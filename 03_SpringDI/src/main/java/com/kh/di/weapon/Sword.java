package com.kh.di.weapon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Primary
@Component // 빈 생성 시 별도의 ID를 지정하지 않으면 클래스 이름에서 앞 글자를 소문자로 바꾼 ID를 갖는다.
public class Sword implements Weapon {
	
	@Value("크리스탈 소드")
	private String name;

	@Override
	public String attack() {
		
		return "검을 휘두른다.";
	}

}