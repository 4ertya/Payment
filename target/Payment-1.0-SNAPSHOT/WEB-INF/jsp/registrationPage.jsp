<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 24.09.2020
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}../css/style.css">
</head>
<body>
<%@include file="header.jsp" %>
<div class="form auth">
    <div class="block-text">Registration</div>
    <form action="Controller" method="post">
        <input class="input" type="text" name="login" placeholder="Login" autofocus required>
        <input class="input" type="password" name="password" placeholder="Password" required>
        <input class="input" type="email" name="email" placeholder="example@gmail.com" required>
        <input type="hidden" name="command" value="registration">
        <input class="button" type="submit" value="Sing in">
    </form>

    <c:if test="${sessionScope.User_exists !=null}">
        <div class="error-text">Пользователь с такими данными уже существует!</div>
    </c:if>

</div>
</body>
</html>
