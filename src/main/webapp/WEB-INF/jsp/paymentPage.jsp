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
<%@taglib uri="http://localhost:8080/mytag" prefix="mytag"%>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

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
        <form action="UserController">
            <input type="hidden" name="command" value="payment">
            <input type="hidden" name="type" value="${requestScope.type}">
            <input type="hidden" name="category" value="${requestScope.category}">
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
            <input class="select_temp" type="number" name="destination" placeholder="номер телефона" required>
            <input class="select_temp" type="number" name="amount" placeholder="Сумма" required>
            <input class="select_temp" type="submit" value="Оплатить">
        </form>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
