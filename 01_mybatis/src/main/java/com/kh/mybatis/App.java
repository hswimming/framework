package com.kh.mybatis;

import org.apache.ibatis.session.SqlSession;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

import static com.kh.mybatis.common.template.SqlSessionTemplate.*;


/**
 * Hello world!
 *
 */
// 롬복을 사용하면 별도로 선언하지 않아도 된다. 
@Slf4j
public class App {
	
//	private static Logger log = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	
        System.out.println( "Hello World!" );
        // 배포환경일 때 (웹에 공개되지 않아도 될 정보들을 찍어주지 않음, 일반적인 정보만 찍어준다.)
        log.info("Hello SLF4J");
        // 개발환경일 때 (개발하는 환경에서 필요한 정보들을 찍어준다.)
        log.debug("Hello SLF4J - debug");
        
//      SqlSession session = SqlSessionTemplate.getSession();
        
//        int count = 0;
//        SqlSession session = getSession(); // SqlSessionTemplate 클래스명 생략 
//        
//        count = session.selectOne("memberMapper.selectCount");
//        
//        System.out.println("Count : " + count);
    }
}