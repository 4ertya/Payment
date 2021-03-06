<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 13.10.2020
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://localhost:8080/mytag" prefix="mytag" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.auth.error" var="error"/>
<fmt:message bundle="${loc}" key="local.auth.incorrect_data" var="incorrect_data"/>
<fmt:message bundle="${loc}" key="local.transactions.transfer_made" var="transfer_made"/>
<fmt:message bundle="${loc}" key="local.transactions.account_blocked" var="account_blocked"/>
<fmt:message bundle="${loc}" key="local.transactions.insufficient_funds" var="insufficient_funds"/>
<fmt:message bundle="${loc}" key="local.etc.unsupported_operation" var="unsupported_operation"/>
<fmt:message bundle="${loc}" key="local.transfers" var="transfers"/>
<fmt:message bundle="${loc}" key="local.etc.amount" var="amount"/>
<fmt:message bundle="${loc}" key="local.etc.fromCard" var="from_card"/>
<fmt:message bundle="${loc}" key="local.etc.toCard" var="to_card"/>
<fmt:message bundle="${loc}" key="local.transactions.recent_transfers" var="last_transfers"/>
<fmt:message bundle="${loc}" key="local.transactions.transactions_history" var="transactions_history"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>${transfers}</b></h3>
        <hr>
        <c:if test="${sessionScope.warning_message!=null}">
            <c:choose>
                <c:when test="${sessionScope.warning_message eq 'incorrect_data'}">
                    <mytag:infoMessage message="${incorrect_data}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'error'}">
                    <mytag:infoMessage message="${error}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'transfer_made'}">
                    <mytag:infoMessage message="${transfer_made}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'insufficient_funds'}">
                    <mytag:infoMessage message="${insufficient_funds}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'account_blocked'}">
                    <mytag:infoMessage message="${account_blocked}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'unsupported_operation'}">
                    <mytag:infoMessage message="${unsupported_operation}"/>
                </c:when>
            </c:choose>
        </c:if>
        <div>
        <form action="UserController">
            <input type="hidden" name="command" value="transfer">
            <label>
                <select name="from" class="select">
                    <option selected disabled>${from_card}</option>
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
                <select name="to" class="select">
                    <option selected disabled>${to_card}</option>
                    <c:forEach items="${requestScope.cards}" var="card">
                        <option value="${card.id}">
                            <mytag:cardNumber cardNumber="${card.number}"/> | ${card.balance} ${card.currency}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label>
                <input class="select_temp" type="text" name="amount" pattern="^[0-9]+\.?[0-9]*$" title="0.0" placeholder="${amount}" required>
            </label>
            <input class="select_temp" type="submit" value="перевести">
        </form>
        <div>
        <c:if test="${not empty requestScope.transactions}">

    </div>
    <p>${last_transfers}</p>
    <div>
        <table cellpadding="5" cellspacing="0" border="1">
            <tr>
                <th>Date</th>
                <th>Card</th>
                <th>Amount</th>
                <th>Destination</th>
            </tr>
            <c:forEach items="${requestScope.transactions}" var="transaction">
                <tr>
                    <td>${transaction.date}</td>
                    <td><mytag:cardNumber cardNumber="${transaction.cardNumber}"/></td>
                    <td>${transaction.amount} ${transaction.currency}</td>
                    <td>${transaction.destination}</td>
                </tr>
            </c:forEach>
        </table>
        <form action="UserController">
            <input type="hidden" name="command" value="to_user_transactions">
            <input class="select_temp" type="submit" value="${transactions_history}"/>
        </form>
        </c:if>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
