package com.kh.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kh.di.owner.Owner;
import com.kh.di.pet.Cat;
import com.kh.di.pet.Dog;

// 해당 클래스가 자바 설정 파일임을 선언한다.
@Configuration
@Import(
	value = {
		OwnerConfig.class,
		PetConfig.class
	}
)

@ComponentScan("com.kh.di")
// 베이스 패키지로 설정 (현재 패키지와 하위 패키지만 스캔)
// 클래스에 Component라는 어노테이션이 있을 경우 자동으로 빈 생성
public class RootConfig {
	// 설정 파일, 어떠한 비즈니스 로직에 영향을 주지 않는다.
	// 빈에 대한 정보, 빈들의 연결 정보, 기타 정보가 들어간다.
	
	@Bean
	public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer = null;
		Resource[] resources;
		
		configurer = new PropertySourcesPlaceholderConfigurer();
		resources = new ClassPathResource[] {
			new ClassPathResource("character.properties"),	
			new ClassPathResource("driver.properties")
		};
		
		configurer.setLocations(resources);
		
		return configurer;
	}
	
}