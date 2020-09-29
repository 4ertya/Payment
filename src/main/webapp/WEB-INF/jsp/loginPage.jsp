<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 21.09.2020
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.auth.message" var="message"/>
<fmt:message bundle="${loc}" key="local.auth.login" var="login"/>
<fmt:message bundle="${loc}" key="local.auth.password" var="password"/>
<fmt:message bundle="${loc}" key="local.auth.button" var="button"/>
<fmt:message bundle="${loc}" key="local.auth.error_message" var="error_message"/>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}../css/style.css">
</head>
<body>
<%@include file="header.jsp" %>
<div class="auth-form">

    <div class="block-text">${message}</div>

    <form action="Controller" method="post">
        <input class="input" type="text" name="login" placeholder=${login} autofocus>
        <input class="input" type="password" name="password" placeholder=${password}>
        <input type="hidden" name="command" value="login">
        <input class="button" type="submit" value=${button}>
    </form>

    <c:if test="${sessionScope.Wrong_Data !=null}">
        <div class="error-text">${error_message}</div>
    </c:if>

</div>
</body>
</html>
