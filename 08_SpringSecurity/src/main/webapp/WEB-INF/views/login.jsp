<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<c:set var="path" value="${ pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>커스텀 로그인 페이지</h2>
	
	<form action="${ path }/login" method="POST">
		<label for="userId">아이디 : </label>
		<!-- name="username" : 기본값으로 정해져 있기 때문에 속성을 변경해주지 않으면 403 에러
		<input type="text" id="userId" name="username" required>
		-->
		<input type="text" id="userId" name="userId" required>
			
		<br>
			
		<label for="userPwd">비밀번호 : </label>
		<!-- name="password" : 기본값으로 정해져 있기 때문에 속성을 변경해주지 않으면 403 에러
		<input type="password" id="userPwd" name="password" required>
		-->
		<input type="password" id="userPwd" name="userPwd" required>
		
		<br>
		
		<label>로그인 유지 : <input type="checkbox" name="remember-me" /></label>
			
		<br><br>
			
		<security:csrfInput/>
		<input type="submit" value="로그인">
	</form>
</body>
</html>