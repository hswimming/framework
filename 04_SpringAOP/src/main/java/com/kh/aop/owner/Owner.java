package com.kh.aop.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kh.aop.pet.Pet;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Component // 별도의 아이디를 주지 않으면 앞글자가 소문자인 문자열을 아이디로 사용한다.
public class Owner {
	
	@Value("황수영") // 일반적인 값을 주입, spring framework에서 제공해주는 value를 사용
	private String name;
	
	@Value("28")
	private int age;
	
	@Autowired
//	@Qualifier("cat") // 얻어오고자 하는 빈의 아이디를 준다. (해당 아이디의 내용으로 출력됨)
	private Pet pet;

}