package com.kh.security.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	@GetMapping("/login")
	public String login() {
		log.info("/login 페이지 요청");
		
		return "login";
	}
	
	@GetMapping("/admin/view")
	public String admin() {
		log.info("/admin/view 페이지 요청");
		
		return "admin/view";
	}

	@GetMapping("/member/view")
	public String member() {
		log.info("/member/view 페이지 요청");
		
		return "member/view";
	}
	
	@GetMapping("/accessError")
	public String accessError() {
		log.info("accessError 페이지 요청");
		
		return "common/accessError";
	}
}