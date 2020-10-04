<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 30.09.2020
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="header.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:useBean id="user" class="by.epamtc.payment.entity.User" scope="request"/>
<jsp:useBean id="userDetail" class="by.epamtc.payment.entity.UserDetail" scope="request"/>

<div class="content">
<div class="form detail">
    <div class="block-text">Personal data</div>
    <form action="Controller" method="post">
        Имя:    <input class="inputD" type="text" name="login" value="${user.login}" required>
        Фамилия:<input class="inputD" type="password" name="password" value="${user.password}" required>
        e-mail: <input class="inputD" type="email" name="email" value="${user.email}" required>

        <input class="inputD" type="email" name="email" value="${userDetail.name}" required>
        <input class="inputD" type="email" name="email" value="${userDetail.surname}" required>
        <input class="inputD" type="email" name="email" value="${userDetail.gender}" required>
        <input class="inputD" type="email" name="email" value="${userDetail.passportSeries}" required>
        <input class="inputD" type="email" name="email" value="${userDetail.passportNumber}" required>
        <input class="inputD" type="email" name="email" value="${userDetail.phone_number}" required>
        <input class="inputD" type="email" name="email" value="${userDetail.location}" required>
        <input class="button" type="submit" value="Save">
    </form>
    <c:if test="${sessionScope.User_exists !=null}">
        <div class="error-text">Пользователь с такими данными уже существует!</div>
    </c:if>
</div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
