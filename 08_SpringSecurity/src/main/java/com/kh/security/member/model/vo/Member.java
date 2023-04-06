package com.kh.security.member.model.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {
	
	private static final long serialVersionUID = -908887352091647880L;

	private int no;

	private String id;

	private String password;

	private String role;

	private String name;

	private String phone;

	private String email;

	private String address;

	private String hobby;

	private String status;

	private Date enrollDate;

	private Date modifyDate;

	@Override
	public String getUsername() {
		return this.id;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		
		// 현재 값이 1개라서 배열로 하지 않음
		// 인터페이스는 new로 생성할 수 없어서 클래스로
		authorities.add(new SimpleGrantedAuthority(this.role));
		
		return authorities;
	}

	// 계정이 만료되지 않았는가?
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	// 계정이 잠기지 않았는가?
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	// 패스워드가 만료되지 않았는가?
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	// 계정이 활성화 되어있는가?
	// "N"을 리턴할 경우 인증으로 넘어가지 않고 예외 처리
	@Override
	public boolean isEnabled() {
		
		return this.status.equals("Y");
	}
	
}