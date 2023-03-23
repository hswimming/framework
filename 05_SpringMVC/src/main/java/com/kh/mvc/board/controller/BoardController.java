package com.kh.mvc.board.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
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
	
	@GetMapping("/fileDown")
	public ResponseEntity<Resource> fileDown(
			@RequestParam String oname, @RequestParam String rname,
			@RequestHeader("user-agent") String userAgent) { // 어노테이션을 통해서 읽어올 수 있다. (매개값으로 헤더명을 주면 자동으로 값을 주입해준다.)
		Resource resource = null;
		String downName = null;
		
		log.info("oname : {}, rname : {}", oname, rname);
		
		try {
			
			// 1. 클라이언트로 전송할 파일을 가져온다.
			resource = resourceLoader.getResource("resources/upload/board/"+ rname); // 원하는 파일을 오브젝트의 형태로 가져온다.
			
			// 2. 브라우저별 인코딩 처리
		    // String userAgent = request.getHeader("user-agent"); // 기존 서블릿에서 사용했던 방식, 현재 @RequestHeader을 사용해서 처리
		    boolean isMSIE = userAgent.indexOf("MSIE") != -1 || userAgent.indexOf("Trident") != -1;
		       
		    if(isMSIE) {
				downName = URLEncoder.encode(oname, "UTF-8").replaceAll("\\+", "%20");
				
		    } else {
		       downName = new String(oname.getBytes("UTF-8"), "ISO-8859-1");         
		    }
	    
		  // 3. 응답 메시지 작성 & 클라이언트로 출력(전송)하기
//		    return "redirect:/board/list";
		    
		    return ResponseEntity.ok()
		    					 .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream") // 모든 종류의 2진 데이터를 의미
		    					 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + downName)
		    					 .body(resource); // 직접 스트림을 얻어오지 않아도 DispatcherServlet이 return 받아준다.
		    
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//			return ResponseEntity.status(500) // 상태 코드를 500으로 직접 줘도 된다.
		}
	}
	
	@GetMapping("/update")
	public ModelAndView update(
			ModelAndView modelAndView, @RequestParam int no,
			@SessionAttribute("loginMember") Member loginMember) {
		Board board = null;
		
		log.info("no : {}", no);
		log.info(loginMember.toString());
		
		board = service.findBoardByNo(no); // 매개값으로 전달되는 파라미터로 조회
		
		if (board != null && board.getWriterId().equals(loginMember.getId())) {
			modelAndView.addObject("board", board);
			modelAndView.setViewName("board/update");
			
		} else {
			modelAndView.addObject("msg", "잘못된 접근입니다.");
			modelAndView.addObject("location", "/board/list");
			modelAndView.setViewName("common/msg");
		}
		
		return modelAndView;
	}
	
	@PostMapping("/update")
	public ModelAndView update(
			ModelAndView modelAndView,
			@RequestParam("upfile") MultipartFile upfile,
			@RequestParam int no, @RequestParam String title, @RequestParam String content,
			@SessionAttribute("loginMember") Member loginMember) { // 로그인 멤버를 세션에 담아서 사용하지 않고 매개값으로 준다.
		
		int result = 0; // update 결과를 정수형으로 반환
		Board board = null;
		
		log.info("{}, {}, {}", new Object[] {no, title, content}); // 오브젝트 배열로 받아온다
		
		board = service.findBoardByNo(no);

		// 게시글 번호로 해당 게시글의 번호가 있는지 조회
		if (board != null && board.getWriterId().equals(loginMember.getId())) {
			
			// 새로운 파일을 저장하는 로직
			if (upfile != null && !upfile.isEmpty()) { // upfile이 null이 아니면서 비어있지 않을 경우
				String location = null;
				String renamedFileName = null;
				
				try {
					location = resourceLoader.getResource("resources/upload/board").getFile().getPath();
					
					// 이전에 업로드된 첨부파일 삭제
					if (board.getRenamedFileName() != null) {
						MultipartFileUtil.delete(location + "/" + board.getRenamedFileName()); // 경로가 포함된 파일명 (물리적인 위치에 저장된 파일 이름)
					}
					
					renamedFileName = MultipartFileUtil.save(upfile, location);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (renamedFileName != null) {
					board.setOriginalFileName(upfile.getOriginalFilename());
					board.setRenamedFileName(renamedFileName);
				}
			} 
			
			board.setTitle(title); // 매개값으로 받은 title을 set (수정된 제목)
			board.setContent(content); // 매개값으로 받은 content를 set (수정된 내용)
			
			result = service.save(board);
			
			if (result > 0) { // return 해주는 결과 값에 따라 실행
				modelAndView.addObject("msg", "게시글이 정상적으로 수정되었습니다.");
//				modelAndView.addObject("location", "/board/update?no=" + board.getNo());
				modelAndView.addObject("location", "/board/view?no=" + board.getNo()); // 수정 성공하면 상세조회 페이지로 이동
				
			} else {
				modelAndView.addObject("msg", "게시글 수정을 실패하였습니다.");
				modelAndView.addObject("location", "/board/update?no=" + board.getNo());
			}
			
		} else {
			modelAndView.addObject("msg", "잘못된 접근입니다.");
			modelAndView.addObject("location", "/board/list");
		}
		
//		modelAndView.addObject("msg", "게시글 수정 테스트");
//		modelAndView.addObject("location", "/board/update?no=" + no);
		modelAndView.setViewName("common/msg"); // 게시글의 목록으로 돌아가게 한 후 포워딩

		return modelAndView;
	}
}