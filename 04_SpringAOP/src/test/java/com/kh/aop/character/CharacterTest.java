package com.kh.aop.character;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:spring/root-context.xml")
class CharacterTest {
	@Autowired(required = false)
	private Character character;

	@Test
	@Disabled
	void test() {
	}

	@Test
	void create() {
//		System.out.println(character);
		
		assertThat(character).isNotNull();
		assertThat(character.getWeapon()).isNotNull();
	}
	
	@Test
	void questTest() throws Exception {
//		String result = character.quest("도토리 줍기");
		
//		System.out.println(result);
		
		assertThat(character.quest("도토리 줍기")).isNotNull();
	}
	
	@Test
	void attackTest() {
		assertThat(character.getWeapon().attack()).isNotNull();
	}
}