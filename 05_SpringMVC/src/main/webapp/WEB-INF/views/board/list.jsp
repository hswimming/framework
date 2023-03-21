<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="path" value="${ pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>게시판</h2>
	
	<section id="content">
		<div id="board-list-container">
			<c:if test="${ not empty loginMember }">
				<button type="button" onclick="location.href='${ path }/board/write'">글쓰기</button>
			</c:if>
	
			<table id="tbl-board">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>첨부파일</th>
					<th>조회수</th>
				</tr>
				<c:if test="${ empty list }">
					<tr>
						<td colspan="6">
							조회된 게시글이 없습니다.
						</td>
					</tr>	
				</c:if>
				<c:if test="${ not empty list }">
					<c:forEach var="board" items="${ list }">
						<tr>
							<td>${ board.no }</td>
							<td>
								<a href="${ path }/board/view?no=${ board.no }">
									${ board.title }
								</a>
							</td>
							<td>${ board.writerId }</td>
							<td>
								<fmt:formatDate type="date" value="${ board.createDate }"/>
							</td>
							<td>
								<c:if test="${ empty board.originalFileName }">
									<span> - </span>
								</c:if>
								<c:if test="${ not empty board.originalFileName }">
									<img width="20px" src="${ path }/images/file.png">
								</c:if>
							</td>
							<td>${ board.readCount }</td>
						</tr>
					</c:forEach>
				</c:if>
	
			</table>
			<div id="pageBar">
				<!-- 맨 처음으로 -->
				<button onclick="location.href='${ path }/board/list?page=1'">&lt;&lt;</button>
	
				<!-- 이전 페이지로 -->
				<button onclick="location.href='${ path }/board/list?page=${ pageInfo.prevPage }'">&lt;</button>
	
				<!--  10개 페이지 목록 -->
				<c:forEach begin="${ pageInfo.startPage }" end="${ pageInfo.endPage }" varStatus="status">
					<c:choose>
						<c:when test="${ status.current == pageInfo.currentPage}">
							<button disabled>${ status.current }</button>
						</c:when>
						<c:otherwise>						
							<button onclick="location.href='${ path }/board/list?page=${ status.current }'">${ status.current }</button>
						</c:otherwise>
					</c:choose>
				</c:forEach>
	
	
				<!-- 다음 페이지로 -->
				<button onclick="location.href='${ path }/board/list?page=${ pageInfo.nextPage }'">&gt;</button>
	
				<!-- 맨 끝으로 -->
				<button onclick="location.href='${ path }/board/list?page=${ pageInfo.maxPage }'">&gt;&gt;</button>
			</div>
		</div>
	</section>
</body>
</html>