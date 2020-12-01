<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 01.12.2020
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="mytag" uri="http://localhost:8080/mytag" %>

<html>
<head>
    <title>Transactions</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="content">
    <div class="form main">

        <div>
            <c:if test="${requestScope.transactions!=null}">
                <label>
                    <input class="searchKey" type="text" placeholder="Поиск">
                </label>
                <span class="searchCount"></span>
                <hr>
            </c:if>
            <table cellpadding="5" cellspacing="0" border="1" class="table_sort">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Card</th>
                    <th>Amount</th>
                    <th>Destination</th>
                    <th>Type</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.transactions}" var="transaction">
                    <tr>
                    <td>${transaction.date}</td>
                    <td><mytag:cardNumber cardNumber="${transaction.cardNumber}"/></td>
                    <td>${transaction.amount} ${transaction.currency}</td>
                    <c:choose>
                        <c:when test="${(transaction.transactionType eq 'DEPOSIT') or (transaction.transactionType eq 'TRANSFER')}">
                            <td><mytag:cardNumber cardNumber="${transaction.cardNumber}"/></td>
                        </c:when>
                        <c:otherwise>
                            <td>${transaction.destination}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${transaction.transactionType}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
