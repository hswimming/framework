package com.kh.security;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.security.member.model.mapper.MemberMapper;
import com.kh.security.member.model.vo.Member;

@Controller
public class HomeController {
//	@Autowired
//	private MemberMapper mapper;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
	
	// 2. Authentication을 매개변수로 얻어오는 방법 (핸들러 메소드의 매개변수로 멤버를 얻어오는 방법)
//	public String home(Locale locale, Model model, Authentication authentication) {
	
	// 3. @AuthenticationPrincipal 어노테이션을 사용하는 방법 (그냥 사용하기만 하면 null을 띄우므로 servlet-context에서 추가 작업 필요)
	// 어떤 멤버 객체인지 모르기 때문에 어노테이션을 기반으로 인증된 멤버 객체를 가져온다. (멤버 그대로 얻어오는 방법)
	public String home(Locale locale, Model model, @AuthenticationPrincipal Member loginMember) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		// @Autowired로 자동으로 주입되고 애플리케이션컨텍스트에도 잘 등록이 되면 mapper 실행
//		System.out.println("Member Count : " + mapper.selectCount());

		// 1. SecurityContextHolder 사용하는 방법
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// 2. Authentication을 매개변수로 얻어오는 방법
//		Member loginMember = (Member) authentication.getPrincipal();
		
		System.out.println(loginMember);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}