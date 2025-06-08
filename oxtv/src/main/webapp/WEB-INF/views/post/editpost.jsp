<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<title>게시글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/write.css">



</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<div class="main-content">
		<h2>게시글 수정</h2>

		<form method="post" enctype="multipart/form-data" action="/posts/${post.id}/edit" class="write-form">

			<!-- 카테고리 & 제목 -->
			<div class="row-inline">
				<label for="category">카테고리:</label>
				<select name="category" id="category" required>
					<option value="도안" ${post.category == '도안' ? 'selected' : ''}>도안</option>
					<option value="자유" ${post.category == '자유' ? 'selected' : ''}>자유</option>
					<option value="질문" ${post.category == '질문' ? 'selected' : ''}>질문</option>
					<c:if test="${isAdmin}">
						<option value="공지" ${post.category == '공지' ? 'selected' : ''}>공지</option>
					</c:if>
				</select>

				<label for="title">제목:</label>
				<input type="text" name="title" id="title" value="${post.title}" required class="form-input">
			</div>

			<!-- 내용 -->
			<div class="form-group">
				<label for="content">내용:</label>
				<textarea name="content" id="content" rows="10" required class="form-textarea">${post.content}</textarea>
			</div>

			<!-- 기존 첨부파일 -->
			<c:if test="${not empty fileList}">
				<div class="form-group">
					<label>기존 파일:</label>
					<ul style="list-style: none; padding-left: 0;">
						<c:forEach var="file" items="${fileList}">
							<li style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;"><label style="display: flex; align-items: center; gap: 6px; flex-shrink: 0;"> <input type="checkbox" name="deleteFileIds" value="${file.id}"> 삭제
							</label> <c:choose>
									<c:when test="${fn:endsWith(file.originalName, '.png') 
                      || fn:endsWith(file.originalName, '.jpg') 
                      || fn:endsWith(file.originalName, '.jpeg') 
                      || fn:endsWith(file.originalName, '.gif')}">
										<img src="${file.uploadPath}" alt="${file.originalName}" style="max-width: 200px; height: auto; object-fit: contain;">
									</c:when>
									<c:otherwise>
										<a href="${file.uploadPath}" download>${file.originalName}</a>
									</c:otherwise>
								</c:choose></li>
						</c:forEach>

					</ul>
				</div>
			</c:if>

			<!-- 새 파일 업로드 -->
			<div class="form-group row-inline file-submit-group">
				
				
				
				<label for="files">새 파일첨부:</label> <label for="files" class="file-btn btn"> <i class="fi fi-rr-folder"></i> 파일 선택
				</label>
				<input type="file" name="files" id="files" multiple class="file-input">
				<span id="file-names" class="file-names">선택된 파일 없음</span>
			</div>

			<!-- 버튼 그룹 -->
			<div class="submit-group">
				<button type="submit" class="btn">수정</button>

				<form method="post" action="/posts/${post.id}/delete" onsubmit="return confirm('게시글을 삭제할까요?')" style="display: inline;">
					<button type="submit" class="btn">삭제</button>
				</form>

				<button type="button" class="btn" onclick="location.href='/posts/${post.id}'">취소</button>
			</div>


			<script>
    document.getElementById("files").addEventListener("change", function () {
        const names = Array.from(this.files).map(f => f.name).join('\n');
        document.getElementById("file-names").textContent = names || "선택된 파일 없음";
    });
</script>
</body>
</html>
