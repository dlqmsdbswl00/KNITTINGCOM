<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OXTV 커뮤니티</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">


</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<div class="main-content">

		<section class="welcome">
			<h1>🧶 OXTV 뜨개 커뮤니티에 오신 걸 환영합니다!</h1>
			<p>뜨개를 좋아하는 사람들의 아늑한 공간, 지금 함께 소통하세요.</p>
		</section>

		<hr />

		<section class="intro-split">
			<div class="intro-image">
				<img src="${pageContext.request.contextPath}/img/knitting.png" alt="뜨개 이미지" />
			</div>
			<div class="intro-features">
				<h2>📌 무엇을 할 수 있나요?</h2>
				<ul>
					<li>💬 자유롭게 수다 떠는 <strong>자유 게시판</strong></li>
					<li>🧵 나만의 작품 공유하는 <strong>도안 게시판</strong></li>
					<li>❓ 궁금한 건 다 물어보는 <strong>질문 게시판</strong></li>
					<li>📣 최신 소식을 알려주는 <strong>공지사항</strong></li>
					<li>🎥 유튜브와 함께 배우는 <strong>뜨개 튜토리얼</strong></li>
				</ul>
				<hr />
				<h2>지금 바로 시작해보세요!</h2>
				<form action="/posts" method="get">
					<button type="submit">👉 게시판 구경하러 가기</button>
				</form>
			</div>


		</section>



	</div>
</body>

</html>