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
<jsp:useBean id="userDetail" class="by.epamtc.payment.entity.UserDetail" scope="request"/>
<jsp:useBean id="user" class="by.epamtc.payment.entity.User" scope="request"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>${userDetail.name} ${userDetail.surname}</b></h3>
        <i>${user.login}</i><br>
        <i>${user.email}</i><br>
        <i>${userDetail.phone_number}</i>
        <hr>
        <div class="nav-menu">
            <ul>
                <li><a href="#">cards</a></li>
                <li><a href="#">accounts</a></li>
                <li><a href="#">payments</a></li>
            </ul>
        </div>
        <hr>

    </div>
    <%@include file="footer.jsp" %>
</div>

</body>
</html>
