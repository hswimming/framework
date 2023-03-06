package com.kh.mybatis.common.template;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionTemplate {
	public static SqlSession getSession() {
		SqlSession session = null;
		SqlSessionFactoryBuilder builder = null;
		InputStream is = null;
		SqlSessionFactory factory = null;
		
		// 1. SqlSessionFactoryBuilder 생성
//		builder = new SqlSessionFactoryBuilder();
		
		// mybatis에서 제공하는 클래스 (클래스 패스 기준으로 경로 지정, 현재 파일은 최상위 위치기 때문에 별도 지정 X)
//		is = Resources.getResourceAsStream("mybatis-config.xml");
		
		try {
			// 1. SqlSessionFactoryBuilder 생성
			builder = new SqlSessionFactoryBuilder();
			
			// 마이바티스에서 자원을 좀 더 쉽게 로드할 수 있는 Resources 클래스를 제공한다.
			is = Resources.getResourceAsStream("mybatis-config.xml");
			
			// 2. SqlSessionFactory 생성
			factory = builder.build(is);
//			factory = builder.build(is, "KH");
			
			// 3. SqlSession 생성 (true : 오토 커밋, false : 수동 커밋)
			session = factory.openSession(false);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		return session;
	}
}