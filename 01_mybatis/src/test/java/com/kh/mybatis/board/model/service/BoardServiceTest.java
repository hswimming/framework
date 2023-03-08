package com.kh.mybatis.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
		
		System.out.println(board);
		System.out.println(board.getReplies());
		
		assertThat(board).isNotNull();
		assertThat(board.getNo()).isEqualTo(122);
	}
	
	public static Stream<Arguments> filterProvider() {
		return Stream.of(
			// arguments 정적 메소드를 통해 인스턴스 생성
			Arguments.arguments((Object) new String[] {"B2", "B3"}),
			Arguments.arguments((Object) new String[] {"B3"})
		);
	}
}