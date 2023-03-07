package com.kh.mybatis.board.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.dao.BoardDao;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

import static com.kh.mybatis.common.template.SqlSessionTemplate.*;
//import static com.kh.mybatis.common.template.SqlSessionTemplate.getSession;;

import java.util.List;

public class BoardService {

	public int getBoardCount() {
		int count = 0;
		SqlSession session = getSession();
		
		count = new BoardDao().getBoardCount(session);
		
		session.close();
		
		return count;
	}

	public List<Board> findAll(PageInfo pageInfo) {
		List<Board> list = null;
		SqlSession session = getSession();
		
		list = new BoardDao().findAll(session, pageInfo);
		
		session.close();
		
		return list;
	}
	
	public int getBoardCount(String writer, String title, String content) {
		int count = 0;
		SqlSession session = getSession();
		
		count = new BoardDao().getBoardCount(session, writer, title, content);
		
		session.close();
		
		return count;
	}

	public List<Board> findAll(String writer, String title, String content) {
		List<Board> list = null;
		SqlSession session = getSession();
		
		list = new BoardDao().findAll(session, writer, title, content);
		
		session.close();
		
		return list;
	}
}