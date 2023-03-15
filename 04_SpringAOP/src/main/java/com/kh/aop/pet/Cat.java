package com.kh.aop.pet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Component // 빈으로 등록
public class Cat extends Pet {
	
	@Value("콜리")
	private String name;
	
	@Override
	public String bark() {
		return "야옹~!";
	}

}