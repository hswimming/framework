package com.kh.di.weapon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Component("windForce")
public class Bow implements Weapon {
	// @Value("윈드포스") // bow가 아닌 윈드포스라는 아이디를 가진다.
	@Value("${character.weapon.name:활}")
	private String name;

	@Override
	public String attack() {
		
		return "활을 쏜다!";
	}

}