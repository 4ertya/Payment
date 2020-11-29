<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 24.09.2020
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.reg.registration" var="registration"/>
<fmt:message bundle="${loc}" key="local.auth.login" var="login"/>
<fmt:message bundle="${loc}" key="local.auth.password" var="password"/>
<fmt:message bundle="${loc}" key="local.reg.button" var="sign_up"/>
<fmt:message bundle="${loc}" key="local.auth.error" var="error"/>
<fmt:message bundle="${loc}" key="local.auth.incorrect_data" var="incorrect_message"/>
<fmt:message bundle="${loc}" key="local.reg.user_exist" var="user_exist"/>

<html>
<head>
    <title>Registration Page</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="form auth">
    <div class="block-text">${registration}</div>

    <form action="MainController" method="post">
        <label>
            <input class="input" type="text" pattern="^[0-9a-zA-Z_-]{3,15}$" name="login" placeholder="${login}" autofocus required>
        </label>
        <label>
            <input class="input" pattern="^[\w-]{8,20}$" type="password" name="password" placeholder="${password}" required>
        </label>
        <label>
            <input class="input" type="email" pattern="^[^\s]+@[^\s]+\.[^\s]+$" name="email" placeholder="example@gmail.com" required>
        </label>
        <input type="hidden" name="command" value="registration">
        <input class="button" type="submit" value="${sign_up}">
    </form>

    <c:if test="${sessionScope.warning_message !=null}">
        <c:choose>
            <c:when test="${sessionScope.warning_message eq 'registration_incorrect_data'}">
                <div class="error-text">${incorrect_message}</div>
            </c:when>
            <c:when test="${sessionScope.warning_message eq 'error'}">
                <div class="error-text">${error}</div>
            </c:when>
            <c:when test="${sessionScope.warning_message eq 'user_exist'}">
                <div class="error-text">${user_exist}</div>
            </c:when>
        </c:choose>

    </c:if>

</div>
</body>
</html>
