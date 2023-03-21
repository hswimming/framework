package com.kh.mvc.board.model.service;

import java.util.List;

import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.PageInfo;

public interface BoardService {

	// 비즈니스 로직을 처리해주는 빈이 정상적으로 주입 될 경우 nullpointerexception, no search bean 같은 에러 발생 X
	int getBoardCount();

	List<Board> getBoardList(PageInfo pageInfo);

	Board findBoardByNo(int no);

	int delete(int no);

	int save(Board board);

}