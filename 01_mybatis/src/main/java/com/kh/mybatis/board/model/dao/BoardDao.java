package com.kh.mybatis.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

public class BoardDao {

	public int getBoardCount(SqlSession session) {
		
		return session.selectOne("boardMapper.selectBoardCount");
	}

	public List<Board> findAll(SqlSession session, PageInfo pageInfo) {
		/*
		 * mybatis에서 페이징 처리
		 * 
		 * mybatis에서는 페이징 처리를 위해 RowBounds라는 클래스를 제공해 준다.
		 * RowBounds의 인스턴스를 생성할 때 offset과 limit 값을 전달해서 페이징 처리를 구현한다.
		 * (offset 만큼 건너뛰고 limit 만큼 가져온다.)
		 * 
		 * offset = 0, limit = 10
		 *  - 0개를 건너뛰고 10개를 가져온다. (1 ~ 10)
		 *  
		 * offset = 10, limit = 10
		 *  - 10개를 건너뛰고 10개를 가져온다. (11 ~ 20)
		 *  
		 * offset = 20, limit = 10
		 *  - 20개를 건너뛰고 10개를 가져온다. (21 ~ 20)
		 */
		
		int limit = pageInfo.getListLimit(); // 한 페이지에 보이는 개수
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// 파라미터 값이 없을 경우 null
		return session.selectList("boardMapper.selectAll", null, rowBounds);
	}
	
	public int getBoardCount(SqlSession session, String writer, String title, String content) {
		Map<String, String> map = new HashMap<>();
		
		map.put("writer", writer);
		map.put("title", title);
		map.put("content", content);	
	
		return session.selectOne("boardMapper.selectBoardCountByKeyWord", map);
	}

	public List<Board> findAll(SqlSession session, String writer, String title, String content) {
		Map<String, String> map = new HashMap<>();
		
		map.put("writer", writer);
		map.put("title", title);
		map.put("content", content);
		
		// 한 개의 파라미터 값만 넘겨줄 수 있기 때문에 map에 담아주고 하나의 오브젝트로(map) 3개의 값을 넘겨준다.
		return session.selectList("boardMapper.selectAllByKeyWord", map);
	}
}