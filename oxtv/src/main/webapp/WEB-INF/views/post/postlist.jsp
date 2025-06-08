<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://oxtv.com/functions" prefix="fnx"%>

<html>
<head>
<title>게시글 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/postlist.css">

<style>


</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<div class="main-content">

		<h2>게시글 목록</h2>
		<div class="top-bar">
			<form action="/posts" method="get">
				<select name="searchType">
					<option value="title" ${param.searchType == 'title' ? 'selected' : ''}>제목</option>
					<option value="nickname" ${param.searchType == 'nickname' ? 'selected' : ''}>작성자</option>
					<option value="category" ${param.searchType == 'category' ? 'selected' : ''}>카테고리</option>
				</select>
				<input type="text" name="keyword" placeholder="검색어 입력" value="${param.keyword}" />
				<button type="submit" class="btn">
					검색 <i class="fi fi-rr-search"></i>
				</button>
			</form>


			<a href="/posts/new" class="btn"> <i class="fi fi-rr-edit"></i>새 글 작성
			</a>
		</div>

		<table border="1">
			<thead>
				<tr>
					<th></th>
					<th>카테고리</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="post" items="${postsPage.content}">
					<tr>
						<td>
							<!-- 관리자면 체크박스 보이도록 -->
							<c:if test="${isAdmin}">
								<input type="checkbox" name="postIds" value="${post.id}">
							</c:if>
						</td>

						<td class="category ${post.category}">${post.category}</td>

						<!-- 제목 하이라이트 적용 -->
						<td>
							<a href="/posts/${post.id}"> <c:out value="${fnx:highlight(post.title, param.keyword)}" escapeXml="false" />
							</a>
						</td>

						<!-- 작성자 하이라이트 적용 -->
						<td>
							<c:out value="${fnx:highlight(post.user.nickname, param.keyword)}" escapeXml="false" />
						</td>

						<!-- 작성일은 하이라이트 필요 없음 -->
						<td>${post.formattedCreatedAt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="bottom-bar">
			<div class="left-btns">
				<c:if test="${postsPage.hasPrevious()}">
					<a href="/posts?page=${postsPage.number - 1}&keyword=${param.keyword}" class="btn"> <i class="fi fi-rr-arrow-left"></i> 이전
					</a>
				</c:if>
				<c:if test="${postsPage.hasNext()}">
					<a href="/posts?page=${postsPage.number + 1}&keyword=${param.keyword}" class="btn"> 다음 <i class="fi fi-rr-arrow-right"></i>
					</a>
				</c:if>
			</div>
			<div class="right-btns">
				<c:if test="${isAdmin}">
					<button id="deleteSelectedBtn" class="btn">
						<i class="fi fi-rr-trash-xmark"></i> 선택 삭제
					</button>
				</c:if>
				<c:if test="${loginUser.role == 'ADMIN'}">
					<a href="${pageContext.request.contextPath}/admin/exportPosts" class="btn"> <i class="fi fi-rr-file-spreadsheet"></i> 엑셀 다운로드
					</a>
				</c:if>
			</div>
		</div>

	</div>
</body>
</html>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  $('#deleteSelectedBtn').click(function() {
    let selected = [];
    $('input[name="postIds"]:checked').each(function() {
      selected.push($(this).val());
    });

    if (selected.length === 0) {
      alert("하나 이상 선택하세요");
      return;
    }

    if (!confirm("선택한 게시글을 삭제할까요?")) return;

    $.ajax({
      url: '/posts/delete',
      type: 'POST',
      traditional: true,  // 리스트 파라미터 보낼 때 필요함
      data: { postIds: selected },
      success: function(res) {
        if (res.success) {
          alert(res.message);
          // 선택된 행 삭제
          selected.forEach(id => {
            $('input[name="postIds"][value="' + id + '"]').closest('tr').remove();
          });
        } else {
          alert("삭제 실패: " + res.message);
        }
      },
      error: function() {
        alert("서버 에러");
      }
    });
  });
</script>

