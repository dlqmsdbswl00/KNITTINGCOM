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
	<c:if
		test="${post.updatedAt != null && post.updatedAt ne post.createdAt}"
	>
		<small style="color: gray;">(수정됨)</small>
	</c:if>
	<p>${post.content}</p>

	<hr>
	<c:if
		test="${not empty sessionScope.loginUser and post.user.userId eq sessionScope.loginUser.userId}"
	>
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

				<c:if
					test="${comment.updatedAt != null && comment.updatedAt ne comment.createdAt}"
				>
					<small style="color: gray;">(수정됨)</small>
				</c:if>
				<p>
					작성: ${comment.createdAt} <br> 수정: ${comment.updatedAt} <br>
					비교: ${comment.updatedAt ne comment.createdAt}
				</p>

				<p class="comment-content">${comment.content}</p>
				<c:if
					test="${sessionScope.loginUser != null && sessionScope.loginUser.id == comment.user.id}"
				>
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
		// JSP EL에서 true/false를 문자열로 넣고 JS에서 boolean 변환
		let isLoggedIn = $
		{
			sessionScope.loginUser != null ? 'true' : 'false'
		};

		/* 		if (!isLoggedIn) {
		 const currentURL = window.location.pathname
		 + window.location.search;
		 window.location.href = "/login?redirect="
		 + encodeURIComponent(currentURL);
		 return;
		 } */

		// 댓글 등록 버튼
		$("#commentForm").submit(function(e) {
			e.preventDefault(); // 폼 기본 전송 막기

			if (!isLoggedIn) {
				alert("댓글 작성하려면 로그인하세요!");
				window.location.href = "/login";
				return;
			}

			let postId = $("#postId").val();
			let content = $("#commentContent").val();

			$.post("/comments/create", {
				postId : postId,
				content : content
			}).done(function() {
				location.reload();
			}).fail(function(xhr) {
				if (xhr.status === 401) {
					alert("로그인 후 이용 가능합니다.");
					window.location.href = "/login";
				} else {
					alert("댓글 작성 실패");
				}
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