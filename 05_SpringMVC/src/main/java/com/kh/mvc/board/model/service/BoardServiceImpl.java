package com.kh.mvc.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.mvc.board.model.mapper.BoardMapper;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.PageInfo;

@Service // 비즈니스 로직을 처리하는 빈으로 등록 -> 컨트롤러에서 BoardService 빈 주입 가능
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper mapper;
	
	@Override
	public int getBoardCount() {
		
		return mapper.selectBoardCount(); // 추상 메소드를 호출하면 쿼리문 실행 가능
	}
	
	@Override
	public List<Board> getBoardList(PageInfo pageInfo) {
		// 기존에는 Dao에서 rowBounds를 만들었지만 지금은 서비스에서 만든다. (구현체를 자동으로 만들어주기 때문에 여기서 처리)
		int limit = pageInfo.getListLimit(); // 한 페이지에 보이는 개수
		int offset = (pageInfo.getCurrentPage() - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return mapper.selectAll(rowBounds);
	}

	@Override
	public Board findBoardByNo(int no) {
		
		return mapper.selectBoardByNo(no);
	}

	@Override
	@Transactional // 예외가 생길 경우 rollback
	public int delete(int no) {
		
		return mapper.updateStatus(no, "N");
	}

	@Override
	@Transactional
	public int save(Board board) {
		int result = 0;
		
		if (board.getNo() > 0) {
			// update
			
		} else {
			// insert
			result = mapper.insertBoard(board);
		}
		
		return result;
	}

}