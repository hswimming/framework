package com.kh.security.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kh.security.member.model.mapper.MemberMapper;
import com.kh.security.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberDetailsService implements UserDetailsService {
	@Autowired
	private MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// UserDetails 타입의 오브젝트를 리턴하도록 생성
		// 사용자가 입력한 아이디 값으로 mapper를 이용하여 DB 조회, 값이 없을 경우 예외 처리 (UsernameNotFoundExceptio을 throws)
		
		Member member = mapper.selectMemberById(username);

//		System.out.println(member);
		
		if(member == null) {
			throw new UsernameNotFoundException(username + " not found"); 
		}

		log.info("Username : {}", member.getId());
		
		return member;
	}

}