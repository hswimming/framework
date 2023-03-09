package com.kh.mybatis.member.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.dao.MemberDao;
import com.kh.mybatis.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

import static com.kh.mybatis.common.template.SqlSessionTemplate.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MemberService {
	
	public int getMemberCount() {
		int count = 0;
//		SqlSession session = SqlSessionTemplate.getSession();
		SqlSession session = getSession(); // 클래스명 생략
		
		count = new MemberDao().getMemberCount(session);
		
		log.info("getMemberCount() 메소드 호출");
		log.debug("getMemberCount() 메소드 호출 - " + count);
		
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

	public int save(Member member) {
		int result = 0;
		SqlSession session = getSession();
		
		if (member.getNo() > 0) {
			result = new MemberDao().updateMember(session, member);
			
		} else {
			result = new MemberDao().insertMember(session, member);
			
		}
		
		// insert 되기 전 멤버 오브젝트에는 NO 값 X insert 이후에 담아주기 때문에 확인 가능
//		System.out.println(member);
		
		if (result > 0) {
			session.commit();
			
		} else {
			session.rollback();
			
		}
		
		session.close();
		
		return result;
	}

	public int delete(String id) {
		int result = 0;
		SqlSession session = getSession();
		
		result = new MemberDao().delete(session, id);
		
		if (result > 0) {
			session.commit();
			
		} else {
			session.rollback();
			
		}
		
		session.close();
		
		return result;
	}
}