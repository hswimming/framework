package com.kh.mybatis.member.model.service;

import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.kh.mybatis.member.model.vo.Member;

// 테스트 메소드 실행 순서 -> 알파벳 순서

@DisplayName("MemberService 테스트")
@TestMethodOrder(OrderAnnotation.class)
//OrderAnnotation -> methodOrder 선택
class MemberServiceTest {
	private MemberService service;
	
//	@AfterAll 모든 test 메소드들이 실행된 후 딱 한번만 실행되는 메소드
//	@AfterEach 각 테스트가 종료될 때 마다 실행, 남아있는 테스트 결과를 정리한다.
//	@BeforeAll // 모든 test 메소드들이 실행되기 전에 딱 한 번만 실행되는 메소드
	@BeforeEach // 각각의 test 메소드들이 실행되기 전에 실행되는 메소드
	void setUp() {
		service = new MemberService();
	}

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
	@Order(1)
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
	@Order(2)
	@DisplayName("모든 회원 조회 테스트")
	void findAllTest() {
		List<Member> members = null;
		
		members = service.findAll();
		
//		System.out.println(members);
		
//		assertThat(members).isNotNull().isNotEmpty();
		assertThat(members).isNotNull().isNotEmpty().extracting("id").contains("swimming");
	}
	
	@ParameterizedTest
	@Order(3)
	@ValueSource(strings = {"admin2", "swimming"}) // 배열 형태로 넣어주기, 개수만큼 테스트 코드 실행
	@DisplayName("회원 조회 테스트 (ID로 검색)")
	void findMemberByIdTest(String id) {
		Member member = null;
		
		// 아이디를 조회 할 때마다 계속 수정하기에는 번거롭기 때문에 라이브러리를 사용한다.
//		member = service.findMemberById("admin2");
		member = service.findMemberById(id);
		
//		System.out.println(member);
		
		assertThat(member).isNotNull();
		assertThat(member.getId()).isNotNull().isEqualTo(id);
	}
	
	@ParameterizedTest
	@CsvSource(value = {"test1, 1234, 황수영", "test2, 4567, 보리"})
	@Order(4)
	@DisplayName("회원 등록 테스트")
	// CsvSource : 데이터들을 콤마(,)로 구분하는 포맷
	void insertMemberTest(String id, String password, String name) {
		int result = 0;
		
		// save 메소드를 테스트 하기 위해 필요한 것만 생성
		Member member = new Member(id, password, name);
		
		// 영향 받은 행의 개수
		result = service.save(member);
		
		assertThat(result).isGreaterThan(0);
		assertThat(member.getNo()).isGreaterThan(0);
		assertThat(service.findMemberById(id)).isNotNull(); // DB에 존재하는지 확인
	}
	
	@ParameterizedTest
	@CsvSource({
		"test1, 0000, 수수수수",
		"test2, 9999, 숫숫숫숫"
	})
	@DisplayName("회원 정보 수정 테스트")
	@Order(5)
	void updateMemberTest(String id, String password, String name) {
		int result = 0;
		Member member = service.findMemberById(id);
		
		member.setPassword(password);
		member.setName(name);
		
		result = service.save(member);
		
		assertThat(result).isGreaterThan(0);
		assertThat(service.findMemberById(id).getName()).isEqualTo(name);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test1", "test2"})
	@DisplayName("회원 삭제 테스트")
	@Order(6)
	void deleteTest(String id) {
		int result = 0;
		
		result = service.delete(id);
		
		assertThat(result).isEqualTo(1); // 유니크 제약조건이 걸려있는 컬럼이므로 결과 값 1
		assertThat(service.findMemberById(id)).isNull(); // 삭제된 후 null 조회되면 테스트 통과
	}
}