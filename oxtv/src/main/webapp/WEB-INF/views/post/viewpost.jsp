<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>게시글 보기</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
	<p>${post.content}</p>

	<hr>
	<c:if
		test="${not empty sessionScope.loginUser and post.user.userId eq sessionScope.loginUser.userId}">
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
				<p class="comment-content">${comment.content}</p>
				<c:if
					test="${sessionScope.loginUser != null && sessionScope.loginUser.id == comment.user.id}">
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

	<h3>댓글 작성</h3>
	<form id="commentForm">
		<input type="hidden" id="postId" value="${post.id}" />
		<textarea id="commentContent" required></textarea>
		<br>
		<button type="submit">작성</button>
	</form>


	<script>
		// 댓글 등록 버튼
		$("#commentForm").submit(function(e) {
			e.preventDefault(); // 폼 기본 전송 막기

			let postId = $("#postId").val();
			let content = $("#commentContent").val();

			$.post("/comments/create", {
				postId : postId,
				content : content
			}).done(function() {
				location.reload(); // 성공 시 새로고침
			}).fail(function() {
				alert("댓글 작성 실패");
			});
		});

		// 댓글 수정 버튼
		$(".edit-btn").click(function() {
			let commentDiv = $(this).closest(".comment");
			commentDiv.find(".comment-content").hide();
			commentDiv.find(".edit-btn, .delete-btn").hide();
			commentDiv.find(".edit-form").show();
		});

		// 취소 버튼
		$(".cancel-edit").click(function() {
			let commentDiv = $(this).closest(".comment");
			commentDiv.find(".edit-form").hide();
			commentDiv.find(".comment-content").show();
			commentDiv.find(".edit-btn, .delete-btn").show();
		});

		// 저장 버튼
		$(".submit-edit").click(function() {
			let commentDiv = $(this).closest(".comment");
			let id = commentDiv.data("id");
			let content = commentDiv.find("textarea").val();

			$.post("/comments/" + id + "/edit", {
				content : content
			}).done(function() {
				commentDiv.find(".comment-content").text(content).show();
				commentDiv.find(".edit-form").hide();
				commentDiv.find(".edit-btn, .delete-btn").show();
			}).fail(function() {
				alert("수정 실패");
			});
		});

		// 삭제 버튼
		$(".delete-btn").click(function() {
			if (!confirm("댓글을 삭제하시겠습니까?"))
				return;

			let commentDiv = $(this).closest(".comment");
			let id = commentDiv.data("id");

			$.post("/comments/" + id + "/delete").done(function() {
				commentDiv.remove();
			}).fail(function() {
				alert("삭제 실패");
			});
		});
	</script>

</body>
</html>