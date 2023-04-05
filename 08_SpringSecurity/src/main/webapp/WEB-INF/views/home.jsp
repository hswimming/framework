<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!-- security를 사용하기 위한 태그 라이브러리 지시자 -->

<c:set var="path" value="${ pageContext.request.contextPath }" />

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

	<P>  The time on the server is ${serverTime}. </P>

	<!-- spring 표현식 -->
	<!-- access="isAnonymous()" : 인증이 되지 않은 사용자 -->
	<security:authorize access="isAnonymous()">
		<a href="${ path }/login">로그인</a>
	</security:authorize>
	
	<!-- 일반 사용자와 관리자의 화면을 다르게 렌더링 하고 싶을 경우 -->
	<!-- security-context 파일에서 작성한 authorities="ROLE_ADMIN 부분 ROLE_ 제외하고 사용 -->
	<security:authorize access="hasRole('ADMIN')">
		<h2>관리자님 어서오세요~!</h2>
	</security:authorize>
	
	<!-- access="isAuthenticated() : ROLE에 상관없이 로그인이 되어있으면 참 -->
	<security:authorize access="isAuthenticated()">
		<p>
		<security:authentication property="principal.username"/>님 안녕하세요.</p>
		
		<form action="${ path }/logout" method="POST">
			<input type="submit" value="로그아웃">
			<security:csrfInput/>
		</form>
	</security:authorize>
</body>
</html>