package com.kh.aop.owner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.aop.config.RootConfig;

@ExtendWith(SpringExtension.class) // spring 기능 확장해서 사용
@ContextConfiguration(classes = RootConfig.class)
class OwnerTest {
	@Autowired(required = false) // 주입 할 빈이 없을 경우 null값 주입
	private Owner owner; // 아무것도 없을 경우 jvm이 null로 초기화 해준다.

	@Test
	@Disabled
	void test() {
	}

	@Test
	void create() {
		System.out.println(owner);
		
		assertThat(owner).isNotNull();
		assertThat(owner.getName()).isNotNull();
		assertThat(owner.getAge()).isGreaterThan(0);
		assertThat(owner.getPet()).isNotNull();
	}
	
	@Test
	void barkTest() throws Exception {
//		System.out.println(owner.getPet().bark());
		
		assertThat(owner.getPet().bark()).isNotNull();
	}
}