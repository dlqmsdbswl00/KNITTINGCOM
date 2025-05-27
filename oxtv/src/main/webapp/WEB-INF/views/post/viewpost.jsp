<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
		let isLoggedIn = "${sessionScope.loginUser != null ? 'true' : 'false'}";
	</script>


	<script>
		let loggedIn = (isLoggedIn === 'true');

		$(document)
				.ready(
						function() {
							// 댓글 등록 버튼
							$("#commentForm")
									.submit(
											function(e) {
												e.preventDefault();

												let postId = $("#postId").val();
												let content = $(
														"#commentContent")
														.val();

												if (!content.trim()) {
													alert("댓글을 입력하세요.");
													return;
												}

												$
														.ajax({
															type : "POST",
															url : "/comments/create",
															data : {
																postId : postId,
																content : content
															},
															success : function() {
																location
																		.reload();
															},
															error : function(
																	xhr) {
																if (xhr.status === 401) {
																	alert("로그인이 필요합니다.");
																	const currentURL = window.location.pathname
																			+ window.location.search;
																	window.location.href = "/login?redirect="
																			+ encodeURIComponent(currentURL);
																} else {
																	alert("댓글 작성 실패: "
																			+ xhr.responseText);
																	console
																			.error(
																					"에러 응답:",
																					xhr);
																}
															}
														});
											});

							// 댓글 수정 버튼
							$(document).on(
									"click",
									".edit-btn",
									function() {
										let commentDiv = $(this).closest(
												".comment");
										commentDiv.find(".comment-content")
												.hide();
										commentDiv.find(
												".edit-btn, .delete-btn")
												.hide();
										commentDiv.find(".edit-form").show();
									});

							// 취소 버튼
							$(document).on(
									"click",
									".cancel-edit",
									function() {
										let commentDiv = $(this).closest(
												".comment");
										commentDiv.find(".edit-form").hide();
										commentDiv.find(".comment-content")
												.show();
										commentDiv.find(
												".edit-btn, .delete-btn")
												.show();
									});

							// 저장 버튼
							$(document)
									.on(
											"click",
											".submit-edit",
											function() {
												let commentDiv = $(this)
														.closest(".comment");
												let id = commentDiv.data("id");
												let content = commentDiv.find(
														"textarea").val();

												$
														.ajax({
															type : "POST",
															url : "/comments/"
																	+ id
																	+ "/edit",
															data : {
																content : content
															},
															success : function() {
																commentDiv
																		.find(
																				".comment-content")
																		.text(
																				content)
																		.show();
																commentDiv
																		.find(
																				".edit-form")
																		.hide();
																commentDiv
																		.find(
																				".edit-btn, .delete-btn")
																		.show();
															},
															error : function() {
																alert("댓글 수정 실패");
															}
														});
											});

							// 삭제 버튼
							$(document).on("click", ".delete-btn", function() {
								if (!confirm("댓글을 삭제하시겠습니까?"))
									return;

								let commentDiv = $(this).closest(".comment");
								let id = commentDiv.data("id");

								$.ajax({
									type : "POST",
									url : "/comments/" + id + "/delete",
									success : function() {
										commentDiv.remove();
									},
									error : function() {
										alert("댓글 삭제 실패");
									}
								});
							});
						});
	</script>

</body>
</html>