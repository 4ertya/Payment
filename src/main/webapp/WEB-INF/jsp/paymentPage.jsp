<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 26.10.2020
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://localhost:8080/mytag" prefix="mytag" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.auth.error" var="error"/>
<fmt:message bundle="${loc}" key="local.auth.incorrect_data" var="incorrect_data"/>
<fmt:message bundle="${loc}" key="local.transactions.payment_made" var="payment_made"/>
<fmt:message bundle="${loc}" key="local.transactions.account_blocked" var="account_blocked"/>
<fmt:message bundle="${loc}" key="local.transactions.insufficient_funds" var="insufficient_funds"/>
<fmt:message bundle="${loc}" key="local.transactions.phone_number" var="phone_number"/>
<fmt:message bundle="${loc}" key="local.transactions.contract_number" var="contract_number"/>
<fmt:message bundle="${loc}" key="local.transactions.personal_account" var="personal_account"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="content">
    <div class="form main">
        <c:out value="${requestScope.category}"/>
        <c:out value="${requestScope.type}"/>
        <hr>
        <c:if test="${sessionScope.warning_message!=null}">
            <c:choose>
                <c:when test="${sessionScope.warning_message eq 'incorrect_data'}">
                    <mytag:infoMessage message="${incorrect_data}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'error'}">
                    <mytag:infoMessage message="${error}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'payment_made'}">
                    <mytag:infoMessage message="${payment_made}"/>
                </c:when>
            </c:choose>
        </c:if>
        <form action="UserController">
            <input type="hidden" name="command" value="payment">
            <input type="hidden" name="category" value="${requestScope.category}">
            <input type="hidden" name="type" value="${requestScope.type}">
            <label>
                <select name="card_id" class="select">
                    <option selected disabled>с карты...</option>
                    <c:forEach items="${requestScope.cards}" var="card">
                        <c:if test="${card.status eq 'ACTIVE'}">
                            <option value="${card.id}">
                                <mytag:cardNumber cardNumber="${card.number}"/> | ${card.balance} ${card.currency}
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </label>
            <label>
                <input class="select_temp" type="number" name="destination"
                       placeholder="${requestScope.category eq 'mobile'? phone_number:
                       (requestScope.category eq 'internet'? contract_number:personal_account)}" required>
            </label>
            <label>
                <input class="select_temp" type="number" name="amount" placeholder="Сумма" required>
            </label>
            <input class="select_temp" type="submit" value="Оплатить">
        </form>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
