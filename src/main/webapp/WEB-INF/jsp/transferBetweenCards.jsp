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
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>Переводы</b></h3>
        <hr>
        <form action="Controller">
            <input type="hidden" name="command" value="transfer">
            <select name="from" class="select">
                <option selected disabled>с карты...</option>
                <c:forEach items="${requestScope.cards}" var="card">
                    <c:if test="${card.status eq 'ACTIVE'}">
                        <option value="${card.id}">
                                ${card.number}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
            <select name="to" class="select">
                <option disabled>на карту...</option>
                <c:forEach items="${requestScope.cards}" var="card">
                    <c:if test="${card.status eq 'ACTIVE'}">
                        <option value="${card.id}">${card.number}</option>
                    </c:if>
                </c:forEach>
            </select>
            <input class="select_temp" type="number" name="amount" placeholder="Сумма" required>
            <input class="select_temp" type="submit" value="перевести">
        </form>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
