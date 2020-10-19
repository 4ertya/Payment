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
        <table cellpadding="5" cellspacing="0" border="1">
            <caption>Счета</caption>
            <tr>
                <th>id</th>
                <th>number</th>
                <th>exp_date</th>
                <th>owner_name</th>
                <th>owner_surname</th>
                <th>account</th>
                <th>status</th>
                <th>system</th>
            </tr>
            <c:forEach items="${requestScope.cards}" var="card">
                <tr>
                    <td>${card.id}</td>
                    <td>${card.number}</td>
                    <td>${card.expDate}</td>
                    <td>${card.ownerName}</td>
                    <td>${card.ownerSurname}</td>
                    <td>${card.account}</td>
                    <td>${card.status}</td>
                    <td>${card.paymentSystem}</td>
                    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                        <td>
                            <form action="Controller?command=change_card_status" method="post">
                                <input type="hidden" name="card_number" value="${card.number}">
                                <input type="hidden" name="status" value="${card.status}">
                                <input type="hidden" name="page" value="${pageContext.request.getParameter("command")}">
                                <input type="submit" value="Заблокировать">
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
