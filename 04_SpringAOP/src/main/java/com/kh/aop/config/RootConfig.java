package com.kh.aop.config;
// 베이스 패키지

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// 비즈니스 로직에 영향을 주지 않는 설정 파일

@Configuration // 설정 파일임을 선언
@ComponentScan("com.kh.aop") // 빈을 직접 생성하지 않고 애플리케이션 컨텍스트가 클래스들 중 Component라는 어노테이션이 있으면 빈으로 만들어줌
@EnableAspectJAutoProxy
public class RootConfig {
	// 외부에서 빈을 사용해야 될 경우 선언하여 생성한다.
	
}