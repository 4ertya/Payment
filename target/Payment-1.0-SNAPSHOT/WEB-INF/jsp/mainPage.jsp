<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 21.09.2020
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Main Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}../css/style.css">
    <script src="https://code.jquery.com/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../script/script.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<div class="authorization-block">
    <div class="authorization-form">
        <label class="tab active" title="Вкладка1">Authorization</label>
        <label class="tab" title="Вкладка2">Registration</label>

        <form action="Controller" method="post" class="tab-form active">
            <input class="input" type="text" name="login" placeholder="Name" autofocus>
            <input class="input" type="password" name="password" placeholder="Password">
            <input type="hidden" name="command" value="login">
            <input class="button" type="submit" value="Sing in">
        </form>
        <form action="Controller" method="post" id="point" class="tab-form ">
            <input class="input" type="text" name="login" placeholder="Name" autofocus>
            <input class="input" type="password" name="password" placeholder="Password">
            <input class="input" type="email" name="email" placeholder="example@gmail.com">
            <input type="hidden" name="command" value="registration">
            <input class="button" type="submit" value="Sing up">
        </form>
    </div>
</div>
</body>
</html>
