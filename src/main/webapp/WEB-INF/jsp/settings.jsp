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
        логин:    <input class="inputD" type="text" name="login" value="${user.login}" required disabled>
        пароль:<input class="inputD" type="password" name="password" value="${user.password}" required>
        e-mail: <input class="inputD" type="email" name="email" value="${user.email}" required>
<br>
       имя: <input class="inputD" type="text" name="name" value="${userDetail.name}" required>
       фамилия: <input class="inputD" type="text" name="surname" value="${userDetail.surname}" required>
       пол: <input class="inputD" type="text" name="gender" value="${userDetail.gender}" required>
       серия паспорта: <input class="inputD" type="text" name="passport_series" value="${userDetail.passportSeries}" required>
       номер паспорта: <input class="inputD" type="number" name="passport_number" value="${userDetail.passportNumber}" required>
       телефон: <input class="inputD" type="tel" name="phone_number" value="${userDetail.phone_number}" required>
       адрес: <input class="inputD" type="text" name="location" value="${userDetail.location}" required>
        <input class="button" type="submit" value="Save">
    </form>
</div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
