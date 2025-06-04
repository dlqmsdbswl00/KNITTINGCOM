<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<title>게시글 보기</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<h2>${post.category}</h2>
	<h2>${post.title}</h2>
	<p>
		<strong>작성자:</strong> ${post.user.nickname}
	</p>
	<p>
		<strong>작성일:</strong> ${formattedCreatedAt}
	</p>
	<c:if test="${post.updatedAt != null && post.updatedAt ne post.createdAt}">
		<small style="color: gray;">(수정됨)</small>
	</c:if>
	<p>${post.content}</p>

	<c:forEach var="file" items="${fileList}">
		<c:choose>
			<c:when test="${fn:endsWith(file.originalName, '.png') 
                        || fn:endsWith(file.originalName, '.jpg') 
                        || fn:endsWith(file.originalName, '.jpeg') 
                        || fn:endsWith(file.originalName, '.gif')}">
				<img src="${file.uploadPath}" style="max-width: 400px;" />
			</c:when>
			<c:otherwise>
				<a href="${file.uploadPath}" download>${file.originalName}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<hr>
	<c:if test="${not empty sessionScope.loginUser and post.user.userId eq sessionScope.loginUser.userId}">
		<a href="/posts/${post.id}/edit">수정</a>
	</c:if>



	<a href="/posts">목록</a>
	<hr>
	<hr>

	<h3>댓글 목록</h3>
	<div id="comments">
		<c:forEach var="comment" items="${post.comments}">
			<div class="comment" data-id="${comment.id}">
				<strong>${comment.user.nickname}</strong>

				<c:if test="${comment.updatedAt != null && comment.updatedAt ne comment.createdAt}">
					<small style="color: gray;">(수정됨)</small>
				</c:if>

				<p class="comment-content">${comment.content}</p>
				<c:if test="${sessionScope.loginUser != null && sessionScope.loginUser.id == comment.user.id}">
					<button class="edit-btn">수정</button>
					<button class="delete-btn">삭제</button>
				</c:if>
				<div class="edit-form" style="display: none;">
					<textarea>${comment.content}</textarea>
					<button class="submit-edit">저장</button>
					<button class="cancel-edit">취소</button>
				</div>
			</div>
			<hr>
		</c:forEach>
	</div>

	<c:forEach var="file" items="${fileList}">
		<p>파일명: ${file.originalName}, URL: ${file.uploadPath}</p>
	</c:forEach>


	<h3>댓글 작성</h3>
	<form id="commentForm">
		<input type="hidden" id="postId" value="${post.id}" />
		<textarea id="commentContent" required></textarea>
		<br>
		<button type="submit">작성</button>
	</form>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="/js/comment.js"></script>



</body>
</html>