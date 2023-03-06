package com.kh.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kh.mybatis.common.template.SqlSessionTemplate;

/**
 * Unit test for simple App.
 */
// 한 개 이상의 테스트 메소드를 가진 클래스
@DisplayName("첫 번째 테스트 코드 작성")
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
//	  테스트 메소드
//    @Test
//    public void shouldAnswerWithTrue()
//    {
//        assertTrue( true );
//    }
    
    @Test
    @Disabled // 테스트 메소드를 비활성 할 수 있다.
    public void nothing() {
    	// 이 테스트를 통해서 현재 프로젝트가 테스트가 가능한 환경인지 확인한다.
    }
    
    @Test
    @DisplayName("SqlSession 생성 테스트") // 실제 실행시켰을 때 메소드 이름이 아닌 지정한 name 내용으로 출력 (어떤 테스트인지 알 수 있음)
    public void create() {
    	SqlSession session = SqlSessionTemplate.getSession();
    	
//    	System.out.println(session);
    	
    	// assertNotNull 메소드를 통해서 테스트 성공, 실패 여부를 판별 (매개값으로 null 값을 넘겨주는지 판별)
//    	assertNotNull(null); // null 값을 넘겨주기 때문에 테스트 실패
    	
    	assertNotNull(session);
//    	assertEquals(10, 8); // 예상되는 값 10, 실제 값 8 이므로 테스트 실패
    	assertEquals(10, 10);
    	
    	// 위에 다른 import가 있어서 중복될까봐 패키지로 찾아서 지정
    	org.assertj.core.api.Assertions.assertThat(10).isEqualTo(10);
    }
}