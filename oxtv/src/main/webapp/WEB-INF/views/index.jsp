<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OXTV 커뮤니티</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>


	<h1>뜨개 OXTV 커뮤니티에 오신 것을 환영합니다</h1>
	<p>여기가 니가 만들 세상의 시작이다.</p>

	<hr />

	<!-- 게시판 목록 보기 링크 추가 -->
	<form action="/posts" method="get">
		<button type="submit">게시판 가기</button>
	</form>

</body>
</html>