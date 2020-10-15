<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 15.10.2020
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>Ваши счета</b></h3>
        <hr>
        <table cellpadding="5" cellspacing="0" border="1">
            <caption>Счета</caption>
            <tr>
                <th>id</th>
                <th>number</th>
                <th>balance</th>
                <th>currency</th>
                <th>opening date</th>
                <th>user id</th>
                <th>status</th>
            </tr>
            <c:forEach items="${requestScope.accounts}" var="account">
            <tr>
                <td>${account.id}</td>
                <td>${account.accountNumber}</td>
                <td>${account.balance}</td>
                <td>${account.currency}</td>
                <td>${account.openingDate}</td>
                <td>${account.user_id}</td>
                <td>${account.status}</td>
            </tr>
            </c:forEach>
            <tr>
                <td align="center" colspan="7"><a href="#">Open new account</a></td>
            </tr>
        </table>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
