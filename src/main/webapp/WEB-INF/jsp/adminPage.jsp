<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 03.10.2020
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.my_cards" var="my_cards"/>
<fmt:message bundle="${loc}" key="local.my_accounts" var="my_accounts"/>
<fmt:message bundle="${loc}" key="local.payments" var="payments"/>

<jsp:useBean id="user" class="by.epamtc.payment.entity.User" scope="request"/>

<html>
<head>
    <title>User Page</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="content">
    <div class="form main">
        <h3><b>${user.name} ${user.surname}</b></h3>

        <hr>
        <div class="nav-menu">
            <div class="link"><a href="AdminController?command=to_all_cards_page">Cards</a></div>
            <div class="link"><a href="AdminController?command=to_all_accounts_page">Accounts</a></div>
            <div class="link"><a href="AdminController?command=to_all_users_page">Payments</a></div>
        </div>

    </div>
    <%@include file="footer.jsp" %>
</div>

</body>
</html>
