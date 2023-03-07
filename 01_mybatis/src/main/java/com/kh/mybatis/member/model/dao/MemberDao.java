package com.kh.mybatis.member.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.vo.Member;

public class MemberDao {

	public int getMemberCount(SqlSession session) {
		
		return session.selectOne("memberMapper.selectCount");
	}

	public List<Member> findAll(SqlSession session) {
		
		return session.selectList("memberMapper.selectAll");
	}

	public Member findMemberById(SqlSession session, String id) {

		// 쿼리문 수행 전에 원하는 곳에 id를 set 해줄 수 있다.
		return session.selectOne("memberMapper.selectMemberById", id);
		
	}

	public int insertMember(SqlSession session, Member member) {
		
		return session.insert("memberMapper.insertMember", member);
	}

	public int updateMember(SqlSession session, Member member) {
		
		return session.update("memberMapper.updateMember", member);
	}

	public int delete(SqlSession session, String id) {
		
		return session.delete("memberMapper.deleteMember", id);
	}
}