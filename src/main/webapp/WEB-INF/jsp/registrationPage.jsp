<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 24.09.2020
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.reg.registration" var="registration"/>
<fmt:message bundle="${loc}" key="local.auth.login" var="login"/>
<fmt:message bundle="${loc}" key="local.auth.password" var="password"/>
<fmt:message bundle="${loc}" key="local.reg.button" var="sign_up"/>
<fmt:message bundle="${loc}" key="local.reg.error_message" var="error_message"/>

<html>
<head>
    <title>Registration Page</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="form auth">
    <div class="block-text">${registration}</div>

    <form action="MainController" method="post">
        <input class="input" type="text" name="login" placeholder="${login}" autofocus required>
        <input class="input" type="password" name="password" placeholder="${password}" required>
        <input class="input" type="email" name="email" placeholder="example@gmail.com" required>
        <input type="hidden" name="command" value="registration">
        <input class="button" type="submit" value="${sign_up}">
    </form>

    <c:if test="${sessionScope.warning_message !=null}">
        <div class="error-text">${error_message}</div>
    </c:if>

</div>
</body>
</html>
