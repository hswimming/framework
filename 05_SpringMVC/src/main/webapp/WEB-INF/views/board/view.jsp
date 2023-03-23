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
	<script src="${ path }/js/jquery-3.6.3.js"></script>
</head>
<body>
	<h2>게시판</h2>
	
	<section id="content">   
		<div id="board-write-container">
			<table id="tbl-board">
				<tr>
					<th>글번호</th>
					<td>${ board.no }</td>
				</tr>
				<tr>
					<th>제 목</th>
					<td>${ board.title }</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${ board.writerId }</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${ board.readCount }</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<c:if test="${ empty board.originalFileName }">
							<span> - </span>
						</c:if>
						<c:if test="${ not empty board.originalFileName }">
							<a href="javascript:" id="fileDown">
								<span> ${ board.originalFileName } </span>
							</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<th>내 용</th>
					<td>${ board.content }</td>
				</tr>
				<%--글작성자/관리자인경우 수정삭제 가능 --%>
				<tr>
					<th colspan="2">
						<c:if test="${ not empty loginMember && loginMember.id == board.writerId }">
							<button type="button" onclick="location.href='${ path }/board/update?no=${ board.no }'">수정</button>
							<button type="button" id="btnDelete">삭제</button>
						</c:if>
						<button type="button" onclick="location.href='${ path }/board/list'">목록으로</button>
					</th>
				</tr>
			</table>
			<div id="comment-container">
		    	<div class="comment-editor">
		    		<form action="${ path }/board/reply" method="POST">
		    			<input type="hidden" name="boardNo" value="${ board.no }">
						<textarea name="content" id="replyContent" cols="55" rows="3"></textarea>
						<button type="submit" id="btn-insert">등록</button>	    			
		    		</form>
		    	</div>
		    </div>
		    <table id="tbl-comment">
		    	<c:forEach var="reply" items="${ board.replies }">
		    	   	<tr class="level1">
			    		<td>
			    			<sub class="comment-writer">${ reply.writerId }</sub>
			    			<sub class="comment-date">${ reply.createDate }</sub>
			    			<br>
			    			<span>${ reply.content }</span>
			    		</td>
			    		<td>
			    			<c:if test="${ not empty loginMember && loginMember.id == reply.writerId }">
			    				<button>삭제</button>
			    			</c:if>
			    		</td>
			    	</tr>
		    	</c:forEach>
		    </table>
	    </div>
	</section>
	<script>
		$(document).ready(() => {
			$('#btnDelete').on('click', () => {
				if(confirm('정말로 게시글을 삭제 하시겠습니까?')) {
					location.replace('${ path }/board/delete?no=${ board.no }');
				}
			});
			
			$('#fileDown').on('click', () => {
				let oname = encodeURIComponent('${ board.originalFileName }');
				let rname = encodeURIComponent('${ board.renamedFileName }');
				
				location.assign('${ path }/board/fileDown?oname=' + oname + '&rname=' + rname);
			});
			
			$('#replyContent').on('focus', () => {
				if(${ empty loginMember}) {
					alert('로그인 후 이용해 주세요.')	;
					
					$('#userId').focus();
				}
			});
		});
	</script>
</body>
</html>