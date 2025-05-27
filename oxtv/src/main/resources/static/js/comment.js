
let isLoggedIn = "${sessionScope.loginUser != null ? 'true' : 'false'}";

$(document).ready(function() {
	// 댓글 등록 버튼
	$("#commentForm").submit(function(e) {
		e.preventDefault();
		console.log("댓글 작성 버튼 눌림!");

		let postId = $("#postId").val();
		let content = $("#commentContent").val();

		if (!content.trim()) {
			alert("댓글을 입력하세요.");
			return;
		}

		$.ajax({
			type: "POST",
			url: "/comments/create",
			data: {
				postId: postId,
				content: content
			},
			success: function() {
				location.reload();
			},
			error: function(xhr) {
				if (xhr.status === 401) {
					alert("로그인이 필요합니다.");
					const currentURL = window.location.pathname + window.location.search;
					window.location.href = "/login?redirect=" + encodeURIComponent(currentURL);
				} else {
					alert("댓글 작성 실패: " + xhr.responseText);
					console.error("에러 응답:", xhr);
				}
			}
		});
	});

	// 댓글 수정 버튼
	$(document).on("click", ".edit-btn", function() {
		let commentDiv = $(this).closest(".comment");
		commentDiv.find(".comment-content").hide();
		commentDiv.find(".edit-btn, .delete-btn").hide();
		commentDiv.find(".edit-form").show();
	});

	// 취소 버튼
	$(document).on("click", ".cancel-edit", function() {
		let commentDiv = $(this).closest(".comment");
		commentDiv.find(".edit-form").hide();
		commentDiv.find(".comment-content").show();
		commentDiv.find(".edit-btn, .delete-btn").show();
	});

	// 저장 버튼
	$(document).on("click", ".submit-edit", function() {
		let commentDiv = $(this).closest(".comment");
		let id = commentDiv.data("id");
		let content = commentDiv.find("textarea").val();

		$.ajax({
			type: "POST",
			url: "/comments/" + id + "/edit",
			data: {
				content: content
			},
			success: function() {
				commentDiv.find(".comment-content").text(content).show();
				commentDiv.find(".edit-form").hide();
				commentDiv.find(".edit-btn, .delete-btn").show();
			},
			error: function() {
				alert("댓글 수정 실패");
			}
		});
	});

	// 삭제 버튼
	$(document).on("click", ".delete-btn", function() {
		if (!confirm("댓글을 삭제하시겠습니까?")) return;

		let commentDiv = $(this).closest(".comment");
		let id = commentDiv.data("id");

		$.ajax({
			type: "POST",
			url: "/comments/" + id + "/delete",
			success: function() {
				commentDiv.remove();
			},
			error: function() {
				alert("댓글 삭제 실패");
			}
		});
	});
});
