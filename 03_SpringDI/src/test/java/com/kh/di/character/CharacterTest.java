package com.kh.di.character;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.di.config.RootConfig;

@ExtendWith(SpringExtension.class)
// @ContextConfiguration("classpath:spring/root-context.xml")

// java 파일로 설정
@ContextConfiguration(classes = RootConfig.class)
class CharacterTest {
	/*
	 * required는 빈 주입이 필수로 진행되어야 하는지 설정하는 옵션이다. (기본값 : true)
	 * 	- required가 true일 경우 주입해야 하는 빈이 애플리케이션 컨텍스트에 존재하지 않으면 Exception이 발생한다.
	 * 	- required가 false일 경우 주입해야 하는 빈이 애플리케이션 컨텍스트에 존재하지 않으면 null을 주입한다.
	 */
	@Autowired(required = false) // 해당하는 빈이 없으면 null값을 주입 할 경우
	private Character character;
	
	@Value("${character.name}")
	private String name;
	
	@Value("${character.level}")
	private int level;
	
	@Value("${db.driver}")
	private String driver;
	
	@Value("${db.url}")
	private String url;
	
	@Test
	void nothing() {
	}
	
	@Test
	void create() {
		System.out.println(character);
		
		assertThat(character).isNotNull();
//		assertThat(character.getName()).isEqualTo("보리");
		assertThat(character.getName()).isEqualTo(name);
//		assertThat(character.getLevel()).isEqualTo(85);
		assertThat(character.getLevel()).isEqualTo(level);
		assertThat(character.getWeapon()).isNotNull();
	}
	
	@Test
	void propertiesTest() {
//		System.out.println(driver);
//		System.out.println(url);
		
		assertThat(driver).isNotNull().isEqualTo("oracle.jdbc.driver.OracleDriver");
		assertThat(url).isNotNull().isEqualTo("jdbc:oracle:thin:@localhost:1521:xe");
	}
	
}