package com.kh.mvc.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.mvc.member.model.mapper.MemberMapper;
import com.kh.mvc.member.model.vo.Member;

@Service
// @Transactional 클래스에 붙일 경우 메소드들이 어드바이스 된다. (서비스에서 다 트랜잭션 처리가 필요할 경우 사용, 일부만 처리하려면 메소드에만 붙이기)
public class MemberServiceImpl implements MemberService {
	
//	@Autowired
//	private MemberMapper dao; // 직접 new 해서 만드는 게 아니라 애플리케이션 컨텍스트를 통해서 생성
//
//	@Autowired
//	private SqlSession session;
	
	@Autowired
	private MemberMapper mapper; // mapper 인터페이스 안에서 자동으로 구현, connection 연결 필요 X
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Member findMemberById(String id) {
		
		return mapper.selectMemberById(id);
	}
	
	@Override
	public Member login(String id, String password) {
		Member member = null;
		
//		member = mapper.selectMemberById(id);
		member = this.findMemberById(id); // 중복되는 코드 줄이기
		
		// 매번 랜덤한 솔트값을 가지고 암호화 하기 때문에 매번 다른 값으로 암호화 된다.
		System.out.println(passwordEncoder.encode(password));
		
		// matches() 메소드를 사용하면 내부적으로 복호화해서 나온 결과 값에 솔트값을 땐 나머지 값과 원문을 비교한다.
		// 암호화가 되지 않은 원문과 암호화 된 문자열을 받아서 비교 (원문이 동일하면 true, 멤버가 없으면 널포인트이셉션)
		// 사용자가 입력하는 원문 패스워드, DB에 저장된 암호화된 패스워드
//		System.out.println(passwordEncoder.matches(password, member.getPassword()));
		
//		if (member != null && member.getPassword().equals(password)) {
		if (member != null && passwordEncoder.matches(password, member.getPassword())) {
			return member;
			
		} else {
			return null;
		}
		
	}

	@Override
	@Transactional
	public int save(Member member) {
		int result = 0;
		
		if (member.getNo() > 0) {
			// update
			result = mapper.updateMember(member);
			
		} else {
			// insert
			// 애플리케이션 컨텍스트로부터 주입받아서 바로 사용 가능
			member.setPassword(passwordEncoder.encode(member.getPassword()));
			
			result = mapper.insertMember(member);
		}
		
//		if (true) {
//			throw new RuntimeException(); // 예외가 발생하는 순간 rollback
//		}
		
		return result;
	}

	@Override
	public Boolean isDuplicateId(String id) {
		
//		return mapper.selectMemberById(id) != null;
		return this.findMemberById(id) != null;	
	}

	@Override
	@Transactional // 예외가 생길 경우 rollback 하도록
	public int delete(int no) {
		
		return mapper.updateMemberStatus(no, "N");
	}
	
}