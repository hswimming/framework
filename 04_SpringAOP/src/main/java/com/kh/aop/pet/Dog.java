package com.kh.aop.pet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.kh.aop.annotation.Repeat;

import lombok.Getter;
import lombok.ToString;

@Getter
@Primary // 동일한 타입의 빈이 여러개 조회될 경우 에러가 발생되기 때문에 기본으로 지정한다.
@ToString
@Component // Bean Configuration 파일에 Bean을 따로 등록하지 않아도 사용할 수 있다. (빈 등록을 빈 클래스 자체에다가 할 수 있다는 의미)
public class Dog extends Pet {
	
	@Value("보리")
	private String name;
	
	@Override
	@Repeat(count = 2) // 카운트 횟수 만큼 출력
	public String bark() throws Exception {
		// 선언부 (메소드 시그니처) 어노테이션에 있는 정보까지 가져간다.
		
		
//		if(true) {
//			throw new Exception();
//		}
		
		return "멍멍~!";
	}
}