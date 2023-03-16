package com.kh.aop.pet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Repeat;

import com.kh.aop.annotation.NoLogging;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Component // 빈으로 등록
public class Cat extends Pet {
	
	@Value("콜리")
	private String name;
	
	@Override
	@NoLogging // AOP에서 해당 어노테이션이 붙어있으면 실행시키지 않는다. (어드바이스 적용을 하지 않는다.)
	public String bark() {
		return "야옹~!";
	}

}