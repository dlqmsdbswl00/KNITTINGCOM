<%@ page language="java" pageEncoding="UTF-8"%>

<header>
    <nav id="main-nav">
        <%
            Object loginUser = session.getAttribute("loginUser");
            System.out.println("JSP 렌더링 시 loginUser: " + loginUser);
        %>
        <a href="/">홈</a> <a href="/posts">게시판</a>
        <% if (loginUser != null) { %>
            <span><%= ((com.oxtv.model.User) loginUser).getNickname() %> 님 환영합니다!</span>
            <a href="/logout">로그아웃</a>
        <% } else { %>
            <a href="/signup">회원가입</a> <a href="/login">로그인</a>
        <% } %>
    </nav>
</header>
