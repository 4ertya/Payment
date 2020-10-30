<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 21.09.2020
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.auth.authorization" var="authorization"/>
<fmt:message bundle="${loc}" key="local.auth.login" var="login"/>
<fmt:message bundle="${loc}" key="local.auth.password" var="password"/>
<fmt:message bundle="${loc}" key="local.auth.button" var="button"/>
<fmt:message bundle="${loc}" key="local.auth.error" var="error"/>
<fmt:message bundle="${loc}" key="local.auth.incorrect_data" var="incorrect_message"/>
<fmt:message bundle="${loc}" key="local.auth.wrong_data" var="wrong_data"/>
<fmt:message bundle="${loc}" key="local.reg.successful" var="successful"/>
<fmt:message bundle="${loc}" key="local.auth.must_log_in" var="must_log_in"/>
<fmt:message bundle="${loc}" key="local.auth.must_log_in_as_admin" var="must_log_in_as_admin"/>

<html>
<head>
    <title>Login Page</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="form auth">
    <div class="block-text">${authorization}</div>

    <form action="MainController" method="post">
        <label>
            <input class="input" type="text" name="login" placeholder=${login} autofocus>
        </label>
        <label>
            <input class="input" type="password" name="password" placeholder=${password}>
        </label>
        <input type="hidden" name="command" value="login">
        <input class="button" type="submit" value=${button}>
    </form>

    <c:if test="${sessionScope.warning_message !=null}">
        <c:choose>
            <c:when test="${sessionScope.warning_message eq 'wrong_data'}">
                <div class="error-text">${wrong_data}</div>
            </c:when>
            <c:when test="${sessionScope.warning_message eq 'login_incorrect_data'}">
                <div class="error-text">${incorrect_message}</div>
            </c:when>
            <c:when test="${sessionScope.warning_message eq 'error'}">
                <div class="error-text">${error}</div>
            </c:when>
            <c:when test="${sessionScope.warning_message eq 'registration_successful'}">
                <div class="good-text">${successful}</div>
            </c:when>
            <c:when test="${sessionScope.warning_message eq 'log_in'}">
                <div class="error-text">${must_log_in}</div>
            </c:when>
            <c:when test="${sessionScope.warning_message eq 'log_in_as_admin'}">
                <div class="error-text">${must_log_in_as_admin}</div>
            </c:when>
        </c:choose>

    </c:if>

</div>
</body>
</html>
