<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<title>게시글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">


</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>게시글 수정</h2>

	<p>DEBUG: role name = [${roleName}]</p>

	<form method="post" enctype="multipart/form-data" action="/posts/${post.id}/edit">

		<br>카테고리:
		<select name="category" required>
			<option value="도안">도안</option>
			<option value="자유">자유</option>
			<option value="질문">질문</option>
			<c:if test="${isAdmin}">
				<option value="공지">공지</option>
			</c:if>
		</select>
		<br> 제목:
		<input type="text" name="title" value="${post.title}" required>
		<br> 내용:<br>
		<textarea name="content" rows="10" cols="50" required>${post.content}</textarea>

		<!-- 1) 기존 첨부파일 목록 보여주기 -->
		<c:if test="${not empty fileList}">
			<p>기존 첨부파일:</p>
			<ul>
				<c:forEach var="file" items="${fileList}">
					<li style="margin-bottom: 10px;"><c:choose>
							<c:when test="${fn:endsWith(file.originalName, '.png') 
                     || fn:endsWith(file.originalName, '.jpg') 
                     || fn:endsWith(file.originalName, '.jpeg') 
                     || fn:endsWith(file.originalName, '.gif')}">
								<div style="display: flex; align-items: center; gap: 10px;">
									<img src="${file.uploadPath}" style="max-width: 200px; height: auto;" /> <label> <input type="checkbox" name="deleteFileIds" value="${file.id}"> 삭제 (${file.originalName})
									</label>
								</div>
							</c:when>
							<c:otherwise>
								<div style="display: flex; align-items: center; gap: 10px;">
									<a href="${file.uploadPath}" download>${file.originalName}</a> <label> <input type="checkbox" name="deleteFileIds" value="${file.id}"> 삭제
									</label>
								</div>
							</c:otherwise>
						</c:choose></li>
				</c:forEach>
			</ul>

			<hr>
		</c:if>

		<!-- 2) 수정 시 새로 업로드할 파일 input -->
		<br>새 파일첨부:
		<input type="file" name="files" multiple />
		<br> <br>

		<button type="submit">수정</button>

	</form>
	<form method="post" action="/posts/${post.id}/delete" style="display: inline;">
		<button type="submit" onclick="return confirm('게시글을 삭제할까요?')">삭제</button>
	</form>


	<a href="/posts/${post.id}">취소</a>
</body>
</html>
