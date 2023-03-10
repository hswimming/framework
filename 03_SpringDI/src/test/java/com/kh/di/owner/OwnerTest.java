package com.kh.di.owner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.di.config.RootConfig;
import com.kh.di.pet.Cat;
import com.kh.di.pet.Dog;

/*
 * JUnit에서 스프링을 사용할 수 있도록 SpringExtension.class를 사용하여 확장한다.
 * 해당 설정이 있어야 @ContextConfiguration()을 통해서 설정 파일을 읽고 애플리케이션 컨텍스트를 생성할 수 있다.
 */
@ExtendWith(SpringExtension.class) // @ExtendWith : 테스트 코드를 확장시키기 위한 어노테이션
//@ContextConfiguration(classes = RootConfig.class)
@ContextConfiguration(locations = "classpath:spring/root-context.xml")
class OwnerTest {
	@Autowired
	@Qualifier("susu")
	private Owner owner;

	@Test
	@Disabled
	void nothing() {
	}
	
	@Test
	@Disabled
	void create() {
		// 기존에 자바 애플리케이션에서는 다형성을 통해서 객체간의 결합도를 느슨하게 만들어준다.
		// Dog를 owner 외부에서 생성 후 넘겨준다.
		// 생성자를 통한 의존성 주입
//		Owner owner = new Owner("황수영", 28, new Dog("보리"));
		
		// 위의 Dog에서 Cat으로 바꾸면 에러 발생 vo, service 모두 다 바꿔줘야 한다. (결합도가 높은 코드)
		// 종속 관계에 있을 경우 결합도는 생길 수 밖에 없으나 결합도가 높을 경우 느슨하게 만들어줘야 한다. (결합도가 강할수록 연쇄적으로 수정해야 한다.)
//		Owner owner = new Owner("황수영", 28, new Cat("콜리"));
		
//		Owner owner = new Owner("황수영", 28, new Dog("보리"));
//		Owner owner = new Owner("황수영", 28, new Cat("콜리"));
		Owner owner = new Owner();
		
		owner.setName("황수영");
		owner.setAge(28);
		// 메소드를 통한 의존성 주입 (setter를 통해)
//		owner.setPet(new Dog("보리"));
		owner.setPet(new Cat("콜리"));
		
		System.out.println(owner);
		System.out.println(owner.getPet());
		System.out.println(owner.getPet().getName());
	}
	
	@Test
	void contextTest() {
//		ApplicationContext context = new GenericXmlApplicationContext("spring/root-context.xml"); // 기본적으로 클래스패스를 기준으로 파일을 찾는다.
//		ApplicationContext context = new GenericXmlApplicationContext("classpath:spring/root-context.xml"); // 접두어로 클래스패스 명시적으로 주기
//		ApplicationContext context = new GenericXmlApplicationContext("file:spring/root-context.xml"); // 에러 발생
//		ApplicationContext context = new GenericXmlApplicationContext("file:src/main/resources/spring/root-context.xml"); // 파일로 찾는 경우 
		
		// java 방식
		// 클래스에 대한 정보를 넘기면 빈을 생성하고 의존 관계를 넘겨준다.
		ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class); 
		
		// bean의 아이디
		// 오브젝트 타입으로 받아지기 때문에 원하는 타입으로 받고싶을 경우 형변환을 해줘야 한다.
//		Owner owner =  (Owner) context.getBean("swimming");
		// 형변환 없이 얻어오는 방법
//		Owner owner =  context.getBean("swimming", Owner.class);
		Owner owner =  context.getBean("susu", Owner.class);
		
//		System.out.println(owner);
//		
		// Autowired로 주입받기
		
		assertThat(context).isNotNull();
		assertThat(owner).isNotNull();
		assertThat(owner.getPet()).isNotNull();
	}
	
	@Test
	void autoWiredTest() {
		System.out.println(owner);
		
		assertThat(owner).isNotNull();
		assertThat(owner.getPet()).isNotNull();
	}

}