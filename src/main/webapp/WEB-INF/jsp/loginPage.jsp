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
<fmt:message bundle="${loc}" key="local.auth.error_message" var="error_message"/>

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
        <div class="error-text">${error_message}</div>
    </c:if>

</div>
</body>
</html>
