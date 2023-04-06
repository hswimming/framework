package com.kh.security.common.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.kh.security.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	// 인증 완료 후 실행 메소드
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// 인증이 완료 된 멤버 오브젝트 가져오기
		Member loginMember = (Member) authentication.getPrincipal(); // 오브젝트 타입으로 리턴하기 때문에 형변환
		
//		System.out.println(loginMember);
		
		// 필요한 정보를 직접 꺼내서 사용 가능
//		authentication.getDetails(); 
		// 권한 정보
//		authentication.getAuthorities();
		
		log.info("ROLE : {}", loginMember.getRole());
		
		if(loginMember.getRole().equals("ROLE_ADMIN")) {
			response.sendRedirect(request.getContextPath() + "/admin/view");
			
		} else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

}