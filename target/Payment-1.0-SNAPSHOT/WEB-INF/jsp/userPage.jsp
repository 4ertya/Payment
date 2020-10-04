<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 03.10.2020
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <p>${requestScope.userDetail.name} ${requestScope.userDetail.surname}</p>
        <hr>
        <div class="nav-menu">
            <ul>
                <a href="#">cards</a>
                <hr>
                <li>Переводы</li>
                <li>Платежи</li>
                <li>Последние операции</li>
            </ul>
        </div>
        <div class="nav-menu">
            <ul>
                <a href="#">accounts</a>
                <hr>
                <li>Переводы</li>
                <li>Настройки</li>
                <li>Последние операции</li>
            </ul>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</div>

</body>
</html>
