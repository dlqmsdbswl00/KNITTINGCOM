<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>게시글 작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/write.css">

</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<div class="main-content">
		<h2>게시글 작성</h2>

		<form method="post" enctype="multipart/form-data" action="/posts/new" class="write-form">

			<div class="row-inline">
				<label for="category">카테고리 :</label>
				<select name="category" id="category" required>
					<c:choose>
						<c:when test="${isAdmin}">
							<option value="공지">공지</option>
						</c:when>
						<c:otherwise>
							<option value="도안">도안</option>
							<option value="자유">자유</option>
							<option value="질문">질문</option>
						</c:otherwise>
					</c:choose>
				</select>

				<label for="title">제목 :</label>
				<input type="text" name="title" id="title" required class="form-input">
			</div>


			<div class="form-group">
				<label for="content">내용 :</label>
				<textarea name="content" id="content" rows="10" required class="form-textarea"></textarea>
			</div>


			<div class="form-group row-inline file-submit-group">
				<label for="files">파일첨부 :</label> <label for="files" class="file-btn btn"> <i class="fi fi-rr-folder"></i> 파일 선택
				</label>
				<input type="file" name="files" id="files" multiple class="file-input">

				<span id="file-names" class="file-names">선택된 파일 없음</span>

				<div class="submit-wrap">
					<button type="submit" class="btn">
						<i class="fi fi-rr-paper-plane"></i> 등록
					</button>
				</div>
			</div>


		</form>
	</div>

	<script>
	document.getElementById("files").addEventListener("change", function () {
	    const names = Array.from(this.files).map(f => f.name).join('\n');
	    document.getElementById("file-names").textContent = names || "선택된 파일 없음";
	});

</script>

</body>


</html>
