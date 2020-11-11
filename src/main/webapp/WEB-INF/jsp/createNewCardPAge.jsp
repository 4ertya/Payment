<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 16.10.2020
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://localhost:8080/mytag" prefix="mytag" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.auth.error" var="error"/>
<fmt:message bundle="${loc}" key="local.auth.incorrect_data" var="incorrect_data"/>
<fmt:message bundle="${loc}" key="local.card.card_created" var="card_created"/>
<fmt:message bundle="${loc}" key="local.account.add_data" var="add_data"/>
<fmt:message bundle="${loc}" key="local.account.wait" var="wait"/>

<html>
<head>
    <title>Create new card</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>Выпустить новую карту</b></h3>
        <c:if test="${sessionScope.warning_message!=null}">
            <c:choose>
                <c:when test="${sessionScope.warning_message eq 'incorrect_data'}">
                    <mytag:infoMessage message="${incorrect_data}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'error'}">
                    <mytag:infoMessage message="${error}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'card_created'}">
                    <mytag:infoMessage message="${card_created}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'add_data'}">
                    <mytag:infoMessage message="${add_data}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'wait'}">
                    <mytag:infoMessage message="${wait}"/>
                </c:when>
            </c:choose>
        </c:if>
        <hr>
        <form action="UserController">
            <input type="hidden" name="command" value="create_new_card">
            <label>
                <select required name="account" class="select">
                    <option selected value=''>выберите счет</option>
                    <c:forEach items="${requestScope.accounts}" var="account">
                        <c:if test="${account.status eq 'ACTIVE'}">
                            <option value="${account.accountNumber}">
                                    ${account.accountNumber}
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </label>
            <label>
                <select name="system" class="select" required>
                    <option selected value=''>платежная система</option>
                    <option value="VISA">Visa</option>
                    <option value="MASTERCARD">Mastercard</option>
                </select>
            </label>
            <label>
                <select required name="term" class="select">
                    <option selected value=''>выберите срок действия</option>
                    <option value="1">1 год</option>
                    <option value="3">3 года</option>
                    <option value="5">5 лет</option>
                </select>
            </label>
            <input class="select_temp" type="submit" value="Создать карту">
        </form>
    </div>
</div>
</body>
</html>
