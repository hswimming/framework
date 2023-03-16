package com.kh.mvc.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.mvc.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	// 컨트롤러가 처리할 요청을 정의한다.(URL, Method 등)
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	@GetMapping("/login") // get mapping만 처리
//	@PostMapping("/login")
//	public String login() {
//		log.info("login() - 호출");
//		
//		return "home";
//	}
	
	// 요청 시 사용자의 파라미터를 전송받는 방법
	// 1. HttpServletRequest 통해서 전송받기
	
//	@PostMapping("/login")
//	public String login(HttpServletRequest request) { // ()안의 내용이 없어도 Spring이 알아서 주입해준다.
//		String id = request.getParameter("id");
//		String password = request.getParameter("password");
//		
//		log.info("login() - 호출 : {} {}", id, password);
//		
//		return "home";
//	}
	
	// 2-1. @RequestParam 어노테이션을 통해서 전송받는 방법
//		  - 스프링에서는 조금 더 간편하게 파라미터를 받아올 수 있는 방법을 제공한다.
//		  - 단, 매개변수의 이름과 파라미터의 name 속성의 값이 동일하면 @RequestParam 어노테이션을 생략할 수 있다.
//	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	// 매개변수의 네임과 파라미터로 넘어노는 name 속성의 값이 동일할 경우 생략 가능
//	public String login(@RequestParam("id") String id, @RequestParam("password") String password) {
//	public String login(@RequestParam String id, @RequestParam String password) {
//		public String login(String id, String password) {
//		
//		log.info("login() - 호출 : {} {}", id, password);
//		
//		return "home";
//	}
	// 2-2. @RequestParam 어노테이션에 defaultValue 설정
//		 - defaultValue 속성을 사용하면 파라미터 name 속성에 값이 없을 경우 기본값을 지정할 수 있다.
	// 네임 속성에 파라미터 값이 없을 경우 기본 속성을 지정하고 싶을 때 @RequestParam의 defaultValue를 주면 된다.
//	@PostMapping("/login")
//	public String login(@RequestParam("id") String id,
//						@RequestParam(value = "password", defaultValue = "1111") String password) {
//		
//		log.info("login() - 호출 : {} {}", id, password);
//		
//		return "home";
//	}
	
//	2-3. @RequestParam 어노테이션에 실제 존재하지 않는 파라미터를 받으려고 할 때
//		 - 실제 존재하지 않는 파라미터를 받으려고 하면 에러가 발생한다. (required = true)
//	 	 - @RequestParam(required = false)로 지정하면 에러가 아닌 null 값을 넘겨준다.
//		 - 단, defaultValue를 설정하면 에러없이 defaultValue에 지정된 값을 받아 올 수 있다. 
//	@PostMapping("/login")
//	public String login(@RequestParam String id,
//						@RequestParam String password,
//						@RequestParam(required = false) String address) { // required = false 할 경우 null 값이 들어간다.
//						@RequestParam(required = false, defaultValue = "서울특별시") String address) {
//						@RequestParam(defaultValue = "서울특별시") String address) {
		
		// 두 개 이상일 경우 오브젝트의 배열로 넘겨줘야 한다.
//		log.info("login() - 호출 : {} {} {}", new Object[] {id, password, address});
//		
//		return "home";
//	}
	
//	3. @ModelAttribute 어노테이션을 통해서 전송받는 방법
//		- 요청 파라미터가 많은 경우 객체 타입으로 파라미터를 넘겨받는 방법이다.
//		- 단, 기본 생성자와 Setter 메소드가 존재해야 한다.
//		- @ModelAttribute 어노테이션을 생략해도 객체로 매핑된다.
//	@PostMapping("/login")
	// 기본 생성자를 만든 후 setter를 통해 주입하는 것과 같다. (필드의 이름과 파라미터로 넘어오는 네임 값이 같아야 가능)
//	public String login(@ModelAttribute Member member) {
//	public String login(Member member) { // 어노테이션을 생략해도 알아서 주입해준다. (매핑이 잘 된다.)
//		
//		System.out.println(member);
//		
//		return "home";
//	}
	
//	4. @PathVariable 어노테이션 
//		- URL 패스상에 있는 특정 값을 가져오기 위해 사용하는 방법이다.
//		- REST API를 사용할 때, 요청 URL 상에서 필요한 값을 가져오는 경우에 주로 사용한다.
	@GetMapping("/member/{id}")
	public String findMember(@PathVariable("id") int id) {
	// PathVariabler과 매개변수의 이름이 같을 경우 생략 가능 (어노테이션은 생략할 경우 Request Param이라고 생각하기 때문에 붙어야함)
//	public String findMember(@PathVariable int id) {
		
		log.info("findMember() - 호출 : {}", id);
		
		return "home";
	}
} 