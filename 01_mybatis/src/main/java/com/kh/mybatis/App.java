package com.kh.mybatis;

import org.apache.ibatis.session.SqlSession;

import static com.kh.mybatis.common.template.SqlSessionTemplate.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
//      SqlSession session = SqlSessionTemplate.getSession();
        
//        int count = 0;
//        SqlSession session = getSession(); // SqlSessionTemplate 클래스명 생략 
//        
//        count = session.selectOne("memberMapper.selectCount");
//        
//        System.out.println("Count : " + count);
    }
}