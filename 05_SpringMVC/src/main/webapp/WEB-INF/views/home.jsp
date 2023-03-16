<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="path" value="${ pageContext.request.contextPath }"/>
<!-- <%@ page session="false" %> : session을 사용하지 않는다는 뜻, 로그인 하려면 정보를 담아야 하기 때문에 -->

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<form action="${ path }/login" method="POST">
	<label for="userId">아이디 : </label>
	<input type="text" id="userId" name="id" required>
	
	<br>
	
	<label for="userPwd">비밀번호 : </label>
	<input type="password" id="userPwd" name="password">
	
	<br><br>
	
	<input type="submit" value="로그인">
</form>

</body>
</html>