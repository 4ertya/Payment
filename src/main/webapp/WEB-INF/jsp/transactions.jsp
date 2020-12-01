<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 01.12.2020
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Transactions</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="content">
    <div class="form main">

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
            </c:if>
        </div>

    </div>
</div>
</body>
</html>
