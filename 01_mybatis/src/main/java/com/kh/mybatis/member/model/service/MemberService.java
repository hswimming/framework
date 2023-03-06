package com.kh.mybatis.member.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.dao.MemberDao;
import com.kh.mybatis.member.model.vo.Member;

import static com.kh.mybatis.common.template.SqlSessionTemplate.*;

import java.util.ArrayList;
import java.util.List;

public class MemberService {

	public int getMemberCount() {
		int count = 0;
//		SqlSession session = SqlSessionTemplate.getSession();
		SqlSession session = getSession(); // 클래스명 생략
		
		count = new MemberDao().getMemberCount(session);
		
		// connection에 반환 (connection 대신 SqlSession 사용)
		session.close();
		
		return count;
	}

	public List<Member> findAll() {
//		List<Member> members = new ArrayList<>(); // 실제로 비어있기 때문에 테스트 실패
		List<Member> members = null;
		SqlSession session = getSession();
		
		members = new MemberDao().findAll(session);
		
		session.close();
		
		return members;
	}

	public Member findMemberById(String id) {
		Member member = null;
		SqlSession session = getSession();
		
		member = new MemberDao().findMemberById(session, id);
		
		session.close();
		
		return member;
	}
	
}