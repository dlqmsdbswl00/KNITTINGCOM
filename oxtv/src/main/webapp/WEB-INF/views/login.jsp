<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
</head>
<body>
    <h2>로그인 페이지</h2>
    <form action="<c:url value='/login' />" method="post">
        <input type="text" name="userId" placeholder="아이디" required />
        <input type="password" name="userPassword" placeholder="비밀번호" required />
        <button type="submit">로그인</button>
    </form>
    
      <c:if test="${not empty errorMessage}">
        <p style="color:red">${errorMessage}</p>
    </c:if>
    
    
    <p>계정이 없으면 <a href="<c:url value='/signup' />">회원가입</a> 하세요.</p>
</body>
</html>
