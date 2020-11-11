<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 17.10.2020
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.card.block" var="block"/>
<fmt:message bundle="${loc}" key="local.card.unblock" var="unblock"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>Карты</b></h3>
        <hr>
        <table cellpadding="5" cellspacing="0" border="1" class="table_sort">
            <caption>Счета</caption>
            <tr>
                <th>id</th>
                <th>number</th>
                <th>exp_date</th>
                <th>card holder</th>
                <th>account</th>
                <th>status</th>
                <th>system</th>
            </tr>
            <c:forEach items="${requestScope.cards}" var="card">
                <tr>
                    <td>${card.id}</td>
                    <td>${card.number}</td>
                    <td>${card.expDate}</td>
                    <td>${card.ownerName} ${card.ownerSurname}</td>
                    <td>${card.accountNumber}</td>
                    <td>${card.status}</td>
                    <td>${card.paymentSystem}</td>

                    <td>
                        <c:choose>
                            <c:when test="${card.status eq 'ACTIVE'}">

                                <form action="UserController?command=block_card&card_id=${card.id}" method="post">
                                    <input type="submit" value="${block}">
                                </form>

                            </c:when>
                            <c:otherwise>
                                    <form action="AdminController?command=unblock_card" method="post">
                                        <input type="hidden" name="card_id" value="${card.id}">
                                        <input type="submit" value="${unblock}">
                                    </form>
                            </c:otherwise>
                        </c:choose>
                    </td>

                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
