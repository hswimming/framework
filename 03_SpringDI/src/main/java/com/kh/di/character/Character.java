package com.kh.di.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kh.di.weapon.Weapon;

import lombok.Getter;
import lombok.ToString;

/*
 * properties 파일의 값을 읽어오는 방법
 * 
 * 1. @PropertySource()를 사용하는 방법
 * 	 - Environment 객체를 사용해서 character.properties에 설정된 값을 읽어온다.
 * 	 - 스프링 프로퍼티 플레이스 홀더를 사용해서 character.properties에 설정된 값을 읽어온다. (${키:기본값})
 * 
 * 2. @PropertySource()
 * 	 - xml 설정 파일을 사용하는 경우 <context:property-placeholder/>를 추가한다.
 * 	 - java 설정 파일을 사용하는 경우 PropertySourcesPlaceholderConfigurer 빈을 등록한다.
 */

@Component // 빈 생성 시 별도의 ID를 지정하지 않으면 클래스 이름에서 앞 글자를 소문자로 바꾼 ID를 갖는다.
@Getter
@ToString
//@PropertySource("classpath:character.properties")
public class Character {
	// private 필드에 직접 주입
//	@Value("수수수수")
	@Value("${character.name:수수수수}") // 기본값 (캐릭터 프로퍼티에 값이 없는 경우 콜론 뒤에 있는 값이 세팅되도록 설정)
	private String name;
	
//	@Value("99")
	@Value("${character.level:99}") // 기본값 (캐릭터 프로퍼티에 값이 없는 경우 콜론 뒤에 있는 값이 세팅되도록 설정)
	private int level;
	
	// Weapon 타입의 빈을 자동으로 주입
	@Autowired
	// 특정 빈을 사용
	@Qualifier("windForce")
	private Weapon weapon;

//	@Autowired // settert
//	public void setWwapon(Weapon weapon) {
//		this.weapon = weapon;
//	}
	
//	public Character(@Value("수수수수") String name, @Value("99") int level, /*@Autowired*/ Weapon weapon) {
//		super();
//		this.name = name;
//		this.level = level;
//		this.weapon = weapon;
//	}
	
	
	// 기본 생성자가 있을 경우 기본 생성자로 애플리케이션 컨텍스트를 만들려고 하기 때문에 없애줘야 한다.
	
	// 애플리케이션 컨텍스트가 갖고 있음
//	public Character(/*@Autowired*/ Environment env) {
//		this.name = env.getProperty("character.name");
//		this.level = Integer.parseInt(env.getProperty("character.level"));
//	}
	
}