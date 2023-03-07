package com.kh.mybatis.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
import org.junit.jupiter.params.provider.CsvSource;

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
		
		System.out.println(count);
		
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
	@DisplayName("게시글 목록 조회 (검색 기능 적용) 테스트")
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
		
		list = service.findAll(writer, title, content);
		
//		System.out.println(list);
//		System.out.println(list.size());
		
		assertThat(list).isNotNull().isNotEmpty();
		
	}
}