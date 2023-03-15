package com.kh.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.kh.di.pet.Cat;
import com.kh.di.pet.Dog;

@Configuration
public class PetConfig {
	// 설정 파일, 어떠한 비즈니스 로직에 영향을 주지 않는다.
	
	// 별도로 빈 ID를 지정해주지 않으면 메소드명으로 ID를 지정한다. (해당 메소드는 dog)
		@Bean
		@Primary // 해결방법 2 : 같은 타입의 빈이 여러개 있을 경우 기본으로 주입 할 빈에 프라이머리 어노테이션을 사용한다.
		public Dog dog() {
			return new Dog("보리");
		}
		
		// setter 메소드를 통해 넘겨주는 방식
		@Bean
		public Cat cat() {
			Cat cat = new Cat();
			
			cat.setName("콜리");
			
			return cat;
		}

}