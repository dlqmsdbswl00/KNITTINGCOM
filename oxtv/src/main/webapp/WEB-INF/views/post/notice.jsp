<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://oxtv.com/functions" prefix="fnx"%>

<html>
<head>
<title>공지사항</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>공지사항</h2>
	<form action="/posts/notice" method="get">
		<input type="text" name="keyword" placeholder="검색어 입력" value="${param.keyword}" />
		<button type="submit">검색</button>
	</form>

	<c:if test="${isAdmin}">
		<a href="/posts/new">공지작성</a>
	</c:if>


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

					<td>${post.category}</td>

					<!-- 제목 하이라이트 적용 -->
					<td>
						<a href="/posts/${post.id}">
							<c:out value="${fnx:highlight(post.title, param.keyword)}" escapeXml="false" />
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

	<c:if test="${isAdmin}">
		<button id="deleteSelectedBtn">선택 삭제</button>
	</c:if>

	<c:if test="${postsPage.hasPrevious()}">
		<a href="/posts/notice?page=${postsPage.number - 1}&keyword=${param.keyword}">이전</a>
	</c:if>
	<c:if test="${postsPage.hasNext()}">
		<a href="/posts/notice?page=${postsPage.number + 1}&keyword=${param.keyword}">다음</a>
	</c:if>



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

