package com.kh.aop.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Repeat {
//	int count(); // 변수 선언이 아닌 추상메소드처럼 선언되어야 사용할 수 있다.
	
	int count() default 1; // Dog 클래스에서 @Repeat(count = 2) 어노테이션이 없을 경우 기본값으로 설정한만큼 출력된다.

}