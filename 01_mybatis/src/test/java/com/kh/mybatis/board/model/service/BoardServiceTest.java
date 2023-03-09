package com.kh.mybatis.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.security.Provider.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("BoardService 테스트")
@TestMethodOrder(OrderAnnotation.class)
class BoardServiceTest {
	private BoardService service;
	
	@BeforeEach
	void setUp() {
		service = new BoardService();
	}

	@Test
	@Disabled
	void nothing() {
	}
	
	@Test
	@Disabled
	void create() {
		assertThat(service).isNotNull();
	}
	
	@Test
	@Order(1)
	@DisplayName("게시글 수 조회 테스트")
	void getBoardCountTest() {
		int count = 0;
		
		count = service.getBoardCount();
		
//		System.out.println(count);
		
		assertThat(count).isGreaterThan(0);
	}

	@Test
	@Order(2)
	@DisplayName("게시글 목록 조회 테스트")
	void findAllTest() {
		List<Board> list = null;
		PageInfo pageInfo = null;
		int listCount = 0;
		
		listCount = service.getBoardCount();
		pageInfo = new PageInfo(2, 10, listCount, 10);
		list = service.findAll(pageInfo);
		
//		System.out.println(list);
//		System.out.println(list.get(0));
//		System.out.println(list.size());
		
		assertThat(list).isNotNull().isNotEmpty();
		assertThat(list.size()).isEqualTo(10);
	}
	
	@ParameterizedTest
	@CsvSource(
			value = {
				"null, null, null",
				"sw, null, null",
				"null, 50, null",
				"null, null, 10"
			},
			nullValues = "null"
		)
	@Order(3)
	@DisplayName("게시글 수 조회 (검색 기능 적용) 테스트")
	void getBoardCountTest(String writer, String title, String content) {
		int count = 0;
		
		count = service.getBoardCount(writer, title, content);
		
//		System.out.println(count);
		
		assertThat(count).isGreaterThan(0);
	}
	
	@ParameterizedTest
	@CsvSource(
		value = {
			"null, null, null",
			"sw, null, null",
			"null, 50, null",
			"null, null, 10"
		},
		nullValues = "null"
		// null이 문자열로 들어가지 않고 진짜 null로 들어갈 수 있게 해준다.
	)
	@Order(4)
	@DisplayName("게시글 목록 조회(검색 기능 적용) 테스트")
	void findAlltest(String writer, String title, String content) {
		
//		assertThat(writer).isNull();
//		assertThat(title).isNull();
//		assertThat(content).isNull();
		
//		System.out.println(writer);
//		System.out.println(title);
//		System.out.println(content);
//		System.out.println();
//		
		List<Board> list = null;
		PageInfo pageInfo = null;
		int listCount = 0;
		
		listCount = service.getBoardCount(writer, title, content);
		pageInfo = new PageInfo(1, 10, listCount, 10);
		list = service.findAll(pageInfo, writer, title, content);
		
//		System.out.println(list);
//		System.out.println(list.size());
		
		assertThat(list).isNotNull().isNotEmpty();
	}
	
	@ParameterizedTest
	@CsvSource(
		value = {
			"null, null",
			"writer, sw",
			"title, 50",
			"content, 10"
		}
	)	
	@Order(5)
	@DisplayName("게시글 수 조회(검색 기능 적용 2) 테스트")
	void getBoardCountTest(String type, String keyWord) {
		// keyWord : 사용자가 입력한 검색어
		
		int count = 0;
		
		count = service.getBoardCount(type, keyWord);
		
//		System.out.println(count);
		
		assertThat(count).isGreaterThan(0);
	}
	
	@ParameterizedTest
	@CsvSource({
			"null, null",
			"writer, sw",
			"title, 50",
			"content, 10"
		}
	)
	@Order(6)
	@DisplayName("게시글 목록 조회(검색 기능 적용 2) 테스트")
	void findAllTest(String type, String keyWord) {
		
//		System.out.println(type);
//		System.out.println(keyWord);
		
		List<Board> list = null;
		PageInfo pageInfo = null;
		int listCount = 0;
		
		listCount = service.getBoardCount(type, keyWord);
		pageInfo = new PageInfo(1, 10, listCount, 10);
		list = service.findAll(pageInfo, type, keyWord);
		
//		System.out.println(list);
//		System.out.println(list.size());
		
		assertThat(list).isNotNull().isNotEmpty();
	}
	
	@ParameterizedTest
	@MethodSource("filterProvider")
	@Order(7)
	@DisplayName("게시글 수 조회(필터 기능 적용) 테스트")
	void getBoardCountTest(String[] filters) {
//		System.out.println(Arrays.toString(filters));
		
		int count = 0;
		
		count = service.getBoardCount(filters);
		
//		System.out.println(count);
		
		assertThat(count).isGreaterThan(0);
	}
	
	@ParameterizedTest
	@MethodSource("filterProvider")
	@Order(8)
	@DisplayName("게시글 목록 조회(필터 기능 적용) 테스트")
	// MethodSource("filterProvider") -> 메소드가 리턴해주는 값을 파라미터에 준다.
	// 문자열을 배열로 받아서 처리 (체크박스 선택 시 배열로 처리하기 때문)
	void findAllTest(String[] filters) {
//		System.out.println(Arrays.toString(filters));
		
		List<Board> list = null;
		PageInfo pageInfo = null;
		int listCount = 0;
		
		listCount = service.getBoardCount(filters);
		pageInfo = new PageInfo(2, 10, listCount, 10);
		// B2 게시글 16개, B3 게시글 11개 라서 페이지를 2페이지로 바꾸면 6, 1 출력
		list = service.findAll(pageInfo, filters);
		
//		System.out.println(list);
//		System.out.println(list.size());
		
		assertThat(list).isNotNull().isNotEmpty();
	}
	
	@Test
	@Order(9)
	@DisplayName("게시글 상세 조회(댓글 포함) 테스트")
	void findBoardByNoTest() {
		Board board = null;
		
		board = service.findBoardByNo(122); // 게시글 번호를 매개값으로 넘겨준다.
		
//		System.out.println(board);
//		System.out.println(board.getReplies());
		
		assertThat(board).isNotNull();
		assertThat(board.getNo()).isEqualTo(122);
		assertThat(board.getReplies().size()).isGreaterThan(0);
	}
	
	@Test
	@Order(10)
	@DisplayName("게시글 등록 테스트")
	public void insertBoard() {
		int result = 0;
		Board board = new Board();
		
		board.setWriterNo(9);
		board.setTitle("mybatis 게시글");
		board.setContent("mybatis로 게시글을 작성하였습니다.");
		board.setOriginalFileName("test.txt");
		board.setRenamedFileName("test.txt");
		
		result = service.save(board);
		
//		System.out.println(board);
		
		assertThat(result).isGreaterThan(0);
		assertThat(board.getNo()).isGreaterThan(0);
		assertThat(service.findBoardByNo(board.getNo())).isNotNull();
	}
	
	@ParameterizedTest
	@MethodSource("boardProvider")
	@Order(11)
	@DisplayName("게시글 수정 테스트")
	void updateBoardTest(Board board) {
		int result = 0;
		
		// 중복되는 메소드를 아래쪽에 따로 빼주었기 때문에 삭제
		// PageInfo를 바로 생성 후 넘겨주기
//		List<Board> list = service.findAll(new PageInfo(1, 10, service.getBoardCount(), 10));
//		Board board = list.get(0); // 가장 마지막에 작성 된 게시글
		
		board.setTitle("mybatis 게시글 수정");
		board.setContent("mybatis 게시글을 수정하였습니다.");
		board.setOriginalFileName(null);
		board.setRenamedFileName(null);

		result = service.save(board);
		
		System.out.println(board);
		
		assertThat(result).isGreaterThan(0);
		// extracting : 특정 필드를 추출하여 테스트를 할 수 있다. 
		assertThat(service.findBoardByNo(board.getNo())).isNotNull().extracting("title").isEqualTo("mybatis 게시글 수정");
		
	}
	
	@ParameterizedTest
	@MethodSource("boardProvider")
	@Order(12)
	@DisplayName("게시글 삭제 테스트")
	void deleteBoardTest(Board board) {
//		System.out.println(board);
		
		int result = 0;
		
		// 최근 게시글의 no값을 넘겨준다.
		result = service.delete(board.getNo());
		
		// no값은 중복되지 않으므로 1개만 조회된다.
		assertThat(result).isEqualTo(1);
		assertThat(service.findBoardByNo(board.getNo())).isNull();
	}
	
	// 정적 메소드는 생성 후 테스트 실행 시 전체적으로 실행해줘야 에러가 나지 않음
	// 중복되는 메소드 따로 생성
	
	public static Stream<Arguments> filterProvider() {
		return Stream.of(
			// arguments 정적 메소드를 통해 인스턴스 생성
			Arguments.arguments((Object) new String[] {"B2", "B3"}),
			Arguments.arguments((Object) new String[] {"B3"})
		);
	}
	
	public static Stream<Arguments> boardProvider() {
		// 정적 메소드에서 정적 필드는 직접 접근할 수 있지만 인스턴스 필드들은 소멸되어서 사용 X, 새로 생성 후 사용
		BoardService service = new BoardService();
		List<Board> list = service.findAll(new PageInfo(1, 10, service.getBoardCount(), 10));
		
		return Stream.of(Arguments.arguments(list.get(0)));
	}
}