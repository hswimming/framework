package com.kh.security.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.security.member.model.vo.Member;

@Mapper
public interface MemberMapper {
	
	// 사용하고자 하는 쿼리문의 id로 추상 메소드 생성 (받는 파라미터 값이 없으면 비우고 생성)
//	int selectCount();
	
	// memberResultMap의 리턴 타입 -> Member로 동일하게 Member를 리턴
	Member selectMemberById(@Param("id") String id);

}