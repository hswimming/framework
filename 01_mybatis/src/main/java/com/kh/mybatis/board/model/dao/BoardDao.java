package com.kh.mybatis.board.model.dao;

import java.util.Arrays;
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
		
		// 키 값이 중복되지 않게 하기 위해 map 사용, 반복적으로 처리 할 때는 list 사용
		// 예시) Member로 담아주면 내부적으로 map에 담아서 처리한다.
		map.put("writer", writer);
		map.put("title", title);
		map.put("content", content);	
	
		return session.selectOne("boardMapper.selectBoardCountByKeyWord", map);
	}

	public List<Board> findAll(SqlSession session, PageInfo pageInfo, String writer, String title, String content) {
		int limit = pageInfo.getListLimit(); // 한 페이지에 보이는 개수
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, String> map = new HashMap<>();
		
		map.put("writer", writer);
		map.put("title", title);
		map.put("content", content);
		
		// 한 개의 파라미터 값만 넘겨줄 수 있기 때문에 map에 담아주고 하나의 오브젝트로(map) 3개의 값을 넘겨준다.
		// 위에서는 파라미터 값이 없었기 때문에 null을 넣었지만 현재는 rowBounds라는 파라미터 값이 있기 때문에 넘겨준다.
		return session.selectList("boardMapper.selectAllByKeyWord", map, rowBounds);
	}

	public int getBoardCount(SqlSession session, String type, String keyWord) {
		Map<String, String> map = new HashMap<>();
		
		map.put("type", type);
		map.put("keyWord", keyWord);
		
		return session.selectOne("boardMapper.selectBoardCountByKeyWord2", map);
	}
	
	public List<Board> findAll(SqlSession session, PageInfo pageInfo, String type, String keyWord) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, String> map = new HashMap<>();
		
		map.put("type", type);
		map.put("keyWord", keyWord);
		
		return session.selectList("boardMapper.selectAllByKeyWord2", map, rowBounds);
	}

	public int getBoardCount(SqlSession session, String[] filters) {
//		Map<String, String[]> map = new HashMap<>();
//		
//		map.put("filters", filters);
		
		return session.selectOne("boardMapper.selectBoardCountByFilters", filters);
	}
	
	public List<Board> findAll(SqlSession session, PageInfo pageInfo, String[] filters) {
		int limit = pageInfo.getListLimit();
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		/*
		 * List나 Array 타입의 데이터를 파라미터로 전달하면 Mapper.xml에서는 list, array라는 이름으로 파라미터에 접근해야 한다.
		 * 
		 * 만약에 Mapper.xml에서 filters라는 이름으로 사용하고 싶으면 Map에 담아서 파라미터로 전달한다.
		 * (키값으로 접근 가능)
		 */
		Map<String, Object> map = new HashMap<>();
		
		map.put("filters", filters);
//		map.put("pageInfo", pageInfo);
		
//		return session.selectList("boardMapper.selectAllByFilters", filters);
//		return session.selectList("boardMapper.selectAllByFilters", Arrays.asList(filters));
		return session.selectList("boardMapper.selectAllByFilters", map, rowBounds);
	}

	public Board findBoardByNo(SqlSession session, int no) {
		
		return session.selectOne("boardMapper.selectBoardByNo", no);
	}
}