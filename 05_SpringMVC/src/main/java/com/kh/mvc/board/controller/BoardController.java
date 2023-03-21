package com.kh.mvc.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.MultipartFileUtil;
import com.kh.mvc.common.util.PageInfo;
import com.kh.mvc.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그를 자동으로 찍을 수 있는 변수
@Controller // 요청을 처리하는 Model 객체를 만들어 데이터를 담고 View를 반환
@RequestMapping("/board") // url이 공통적으로 들어가는 경우 앞 부분에 해당 url이 다 들어가게 된다. (중복되는 코드 줄이기)
public class BoardController {
	@Autowired // 직접 new 하는것이 아닌 애플리케이션 컨텍스트에서 얻어온다.
	private BoardService service; // 참조변수
	
	@Autowired
	private ResourceLoader resourceLoader; // 파일을 기준으로 하거나 웹앱을 기준으로 필요한 리소스를 얻어올 수 있다.
	
//	메소드의 리턴 타입이 void일 경우 Mapping URL을 유추해서 View를 찾는다. (해당하는 파일을 찾아서 포워드)
//	@GetMapping("/board/list")
//	public void list() {
//	}
	
	@GetMapping("/list")
	public ModelAndView list(ModelAndView modelAndView, @RequestParam(defaultValue = "1") int page) {
		// 파라미터가 name 속성인데 값이 없을 경우 빈 문자열이 들어오기 때문에 default 값을 지정해준다.
		// 파라미터 네임 속성과 매개변수 네임 속성이 같으면 생략 가능
		
		int listCount = service.getBoardCount();
		
//		System.out.println(page);
//		System.out.println(service.getBoardCount());
		log.info("Page : {}", page);
		log.info("Page : {}", listCount);

		PageInfo pageInfo = new PageInfo(page, 10, listCount, 10);
		List<Board> list = service.getBoardList(pageInfo); // service는 애플리케이션 컨텍스트로부터 빈으로 주입
		
		modelAndView.addObject("pageInfo", pageInfo);
		modelAndView.addObject("list", list);
		modelAndView.setViewName("board/list");
		
		return modelAndView;
	}
	
	@GetMapping("/view")
	public ModelAndView view(ModelAndView modelAndView, @RequestParam int no) {
		// 파라미터 네임 속성과 매개변수 네임 속성이 같으면 생략
		
		Board board = null;
		
		log.info("no : {}", no);
		
		board = service.findBoardByNo(no);
		
		modelAndView.addObject("board", board);
		modelAndView.setViewName("board/view"); // 원하는 JSP 파일로 포워딩
		
		return modelAndView;
	}
	
	@GetMapping("/delete")
	public ModelAndView delete(ModelAndView modelAndView, @RequestParam int no,
				@SessionAttribute("loginMember") Member loginMember) { // 세션 영역에 저장되어있는 attribute, 매개변수로 전달
		
		int result = 0;
		Board board = null;
		
		board = service.findBoardByNo(no);
		
//		System.out.println(no);
//		System.out.println(loginMember);
//		System.out.println(board);
		
		if (board != null && board.getWriterId().equals(loginMember.getId())) {
			result = service.delete(no);
			
			if(result > 0) {
			modelAndView.addObject("msg", "게시글이 정상적으로 삭제되었습니다.");
			modelAndView.addObject("location", "/board/list");
				
			} else {
				modelAndView.addObject("msg", "게시글 삭제를 실패하였습니다.");
				modelAndView.addObject("location", "/board/view?no=" + no);
			}
			
		} else {
			modelAndView.addObject("msg", "잘못된 접근입니다.");
			modelAndView.addObject("location", "/board/list");
		}
		
		modelAndView.setViewName("common/msg");
		
		return modelAndView;
	}
	
	// 단순 페이지 요청
	@GetMapping("/write")
	public String write() {
		log.info("게시글 작성 페이지 요청");
		
		return "board/write";
	}
	
	// 작성 후 서브밋
	@PostMapping("/write")
	public ModelAndView write(
			ModelAndView modelAndView,
			@ModelAttribute Board board, // MultipartResolver만 등록하면 빈으로 사용하여 객체 생성 가능
			@RequestParam("upfile") MultipartFile upfile,
//			@RequestParam("upfile") MultipartFile[] upfile, 사진을 여러개로 받을 경우 배열로 받는다.
			@SessionAttribute("loginMember") Member loginMember) { 
		
		int result = 0;
		
		// 파일을 업로드 하지 않으면 true  , 파일을 업로드 하면 false 
		log.info("upfile.isEmpty() : {}", upfile.isEmpty());
		// 파일을 업로드 하지 않으면 "" (빈 문자열), 파일을 업로드 하면 "파일명"
		log.info("upfile.getOriginalFilename() : {}", upfile.getOriginalFilename());
		
		// 1. 파일을 업로드 했는지 확인 후 파일을 저장
		if (upfile != null && !upfile.isEmpty()) {
			// 최상위 경로이기 때문에 따로 작성하지 않는다.
			String location = null;
			String renamedFileName = null;
			
			// 업로드 된 파일이 있을때만 물리적인 경로에 저장 후 board에 값을 set 한다.
			try {
				// 파일 경로 기준 : file: 접두어
				// 클래스 패스 기준 : classpath: 접두어
				// 따로 주지 않을 경우(웹 컨텐츠 기준) : WEB-INF 경로
				location = resourceLoader.getResource("resources/upload/board").getFile().getPath();
				
//				System.out.println(location);

				// MultipartFileUtil : save, delete 메소드 다 있음, 중복되는 코드를 줄이기 위해 사용
				renamedFileName = MultipartFileUtil.save(upfile, location);
				
				System.out.println(renamedFileName);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (renamedFileName != null) {
				board.setOriginalFileName(upfile.getOriginalFilename());
				board.setRenamedFileName(renamedFileName);
			}
		}
		
		// 2. 작성한 게시글 데이터를 데이터 베이스에 저장
		board.setWriterNo(loginMember.getNo());
		
		result = service.save(board); // result : save의 결과값을 받는 변수
		
		if (result > 0) {
			modelAndView.addObject("msg", "게시글이 정상적으로 등록되었습니다.");
			modelAndView.addObject("location", "/board/view?no=" + board.getNo());
			
		} else {
			modelAndView.addObject("msg", "게시글 등록을 실패하였습니다.");
			modelAndView.addObject("location", "/board/write");
			
		}
		
		modelAndView.setViewName("common/msg");
		
		return modelAndView;
	}
	
}