package com.kh.mvc.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import com.kh.mvc.member.model.vo.Member;

@Mapper
public interface MemberMapper {

	Member selectMemberById(@Param("id") String id);
	
	// 사용하고자 하는 쿼리문을 동일한 아이디의 추상 메소드로 생성
	int insertMember(Member member);

	// 영향받은 행의 개수를 반환하기 때문에 정수형
	int updateMember(Member member);
	
	// 여러개의 값을 넘겨줄 때 map 형태로 넘겨줬었음, @Param으로 넘겨주면 내부적으로 알아서 map 형태로 넘겨준다.
	int updateMemberStatus(@Param("no") int no, @Param("status") String status);
}