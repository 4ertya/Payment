<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 18.10.2020
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <th>name</th>
                <th>surname</th>
                <th>passport</th>
                <th>phoneNumber</th>
                <th>role</th>
                <th>status</th>
            </tr>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.ruName}</td>
                    <td>${user.ruSurname}</td>
                    <td>${user.passportSeries} ${user.passportNumber}</td>
                    <td>${user.phoneNumber}</td>
                    <td>${user.role}</td>
                    <td>${user.status}</td>
                    <td>
                        <form action="UserController?command=to_settings_page&user_id=${user.id}" method="post">
                        <input type="submit" value="Редактировать">
                    </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
