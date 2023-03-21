package com.kh.mvc.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.mvc.board.model.vo.Board;

@Mapper // mapper scanner를 통해서 mapper 인터페이스를 자동으로 구현하게끔 한다.
public interface BoardMapper {
	
	// 실행시키고자 하는 쿼리문의 아이디와 동일한 추상 메소드 생성 (파라미터, 반환 타입)
	int selectBoardCount();
	
	List<Board> selectAll(RowBounds bounds); // 매개값을 받도록
	
	// 게시글 상세 조회 (댓글 포함)
	Board selectBoardByNo(@Param("no") int no);
	
	int updateStatus(@Param("no") int no, @Param("status") String status);
	
	int insertBoard(Board board);
}