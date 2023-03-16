package com.kh.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation
 * 	 - JDK5부터 추가된 기능으로 자바 코드에 추가적인 정보를 제공하는 메타 데이터이다.
 * 	 - 비지니스 로직에 영향을 주지는 않지만 컴파일 과정에서 유효성 체크, 코드를 어떻게 컴파일하고 처리할지 알려주는 정보를 제공한다.
 * 	 - 어노테이션은 클래스, 메소드, 필드, 매개변수 등에 추가할 수 있다.
 */

// 어노테이션을 적용할 위치(대상)을 지정한다.
// @Target(value = {ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.TYPE})
// @Target(value = {ElementType.METHOD, ElementType.TYPE})
@Target({ElementType.METHOD, ElementType.TYPE}) // value 생략 가능 (위와 같은 의미)

// 어노테이션의 유효 범위를 지정할 때 사용한다. (어느 지점까지 어노테이션이 영향을 미치는지 결정)
// RetentionPolicy.SOURCE : 소스 코드상에서만 유효하다.
// RetentionPolicy.CLASS : 컴파일 이후 클래스를 참조할 때까지 유효하다.
// RetentionPolicy.RUNTIME : JVM에 의해서 참조가 가능하다. (실제 실행될 때 값을 주입)
@Retention(value =  RetentionPolicy.RUNTIME)

// 부모 클래스에서 어노테이션을 선언하면 자식 클래스에도 상속된다.
// @Inherited

// 어노테이션을 선언하는 문법
public @interface NoLogging {

}