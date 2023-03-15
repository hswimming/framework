package com.kh.di.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.di.owner.Owner;
import com.kh.di.pet.Pet;

@Configuration
public class OwnerConfig {
	// 설정 파일, 어떠한 비즈니스 로직에 영향을 주지 않는다.
	
//	@Bean("swimming")
//	public Owner owner() {
//		// dog() 메소드는 빈으로 등록되어있기 때문에 호출 시 마다 빈을 생성하는 것이 아닌
//		// 애플리케이션 컨텍스트에서 등록된 빈을 얻어온다. (이미 가지고 있는 빈을 주입한다. 호출 인터셉트 - 요청을 가로챈다)
//	
//		return new Owner("황수영", 28, dog());
	
	
	// 어노테이션 방식
	@Bean("swimming")
	// @Autowired : 타입에 따라 빈을 주입한다. (다른 빈을 생성자에게 넘겨준다. 해당 구문에서는 pet타입을 가진 빈을 주입)
	// 다른 빈들이 아직 만들어지지 않았을 경우 보류상태, 추후 생성되면 주입한다.
	
	
	// 해결방법 1 : 같은 타입의 주입 대상이 한 개여야 하는데 실제로는 두 개 이상의 빈이 존재해 주입할 때 사용할 객체를 선택할 수 없기 때문에 @Qualifier을 사용 
//	public Owner owner(@Autowired @Qualifier("dog") Pet pet) {
	
		public Owner owner(@Autowired Pet pet) {
//		
		return new Owner("황수영", 28, pet);
	}
	
	@Bean
	// @Autowired를 생략해도 pet타입의 빈이 있는지 확인하고 있을 경우 넣어준다.
	// 메소드를 실행시키려면 매개값을 넘겨줘야 하기 때문에 pet이 필수로 필요
	public Owner susu(/*@Autowired*/ Pet pet) {
		Owner owner = new Owner();
		
		owner.setName("수수수수");
		owner.setAge(20);
//		owner.setPet(cat()); // setter를 통해 필요한 값을 주입받는다.
		
		owner.setPet(pet); // setter를 통해 필요한 값을 주입받는다.
		
		return owner;
	}

}