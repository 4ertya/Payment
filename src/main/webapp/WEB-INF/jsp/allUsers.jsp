<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 18.10.2020
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <h3><b>Пользователи</b></h3>
        <hr>
        <table cellpadding="5" cellspacing="0" border="1">
            <tr>
                <th>id</th>
                <th>login</th>
                <th>email</th>
                <th>role</th>
            </tr>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
