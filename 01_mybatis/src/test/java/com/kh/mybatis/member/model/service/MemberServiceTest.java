package com.kh.mybatis.member.model.service;

import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.kh.mybatis.member.model.vo.Member;

@DisplayName("MemberService 테스트")
class MemberServiceTest {
	private MemberService service;
	
//	@AfterAll 모든 test 메소드들이 실행된 후 딱 한번만 실행되는 메소드
//	@AfterEach 각 테스트가 종료될 때 마다 실행, 남아있는 테스트 결과를 정리한다.
//	@BeforeAll // 모든 test 메소드들이 실행되기 전에 딱 한 번만 실행되는 메소드
	@BeforeEach // 각각의 test 메소드들이 실행되기 전에 실행되는 메소드
	void setUp() {
		service = new MemberService();
	}
	
	// 테스트 메소드 실행 순서 -> 알파벳 순서

	// 테스트 할 수 있는 환경인지 확인
	@Test
	@Disabled
	void nothing() {
	}
	
	// 객체 생성 테스트
	@Test
	@Disabled
	void create() {
		assertThat(service).isNotNull();
	}
	
	@Test
	@DisplayName("회원 수 조회 테스트")
	void getMemberCountTest() {
		int count = 0;
		
		count = service.getMemberCount();
		
//		System.out.println(count);
		
		// count 값이 매개값 보다 크면 전달
//		assertThat(count).isGreaterThan(0);
		assertThat(count).isPositive().isGreaterThanOrEqualTo(2);
	}
	
	@Test
	@DisplayName("모든 회원 조회 테스트")
	void findAllTest() {
		List<Member> members = null;
		
		members = service.findAll();
		
//		System.out.println(members);
		
//		assertThat(members).isNotNull().isNotEmpty();
		assertThat(members).isNotNull().isNotEmpty().extracting("id").contains("swimming");
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"admin2", "swimming"}) // 배열 형태로 넣어주기
	@DisplayName("회원 조회 테스트 (ID로 검색)")
	void findMemberByIdTest(String id) {
		Member member = null;
		
		// 아이디를 조회 할 때마다 계속 수정하기에는 번거롭기 때문에 라이브러리를 사용한다.
//		member = service.findMemberById("admin2");
		member = service.findMemberById(id);
		
//		System.out.println(member);
		
		assertThat(member).isNotNull();
	}
	
}