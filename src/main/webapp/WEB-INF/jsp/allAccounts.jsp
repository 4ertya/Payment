<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 17.10.2020
  Time: 16:54
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
        <h3><b>Счета</b></h3>
        <hr>
        <table cellpadding="5" cellspacing="0" border="1" class="table_sort">
            <caption>Счета</caption>
            <thead>
            <tr>
                <th>id</th>
                <th>number</th>
                <th>balance</th>
                <th>currency</th>
                <th>openning_date</th>
                <th>user</th>
                <th>status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.accounts}" var="account">
                <tr>
                    <td>${account.id}</td>
                    <td>${account.accountNumber}</td>
                    <td>${account.balance}</td>
                    <td>${account.currency}</td>
                    <td>${account.openingDate}</td>
                    <td>${account.user_id}</td>
                    <td>${account.status}</td>
                        <td>
                            <form action="Controller?command=change_card_status" method="post">
                                <input type="hidden" name="card_number" value="${card.number}">
                                <input type="hidden" name="status" value="${card.status}">
                                <input type="hidden" name="page" value="${pageContext.request.getParameter("command")}">
                                <input type="submit" value="Заблокировать">
                            </form>
                        </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
