<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 26.10.2020
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://localhost:8080/mytag" prefix="mytag"%>

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
        <div>
        <nav id="menuVertical">
            <ul>
                <li><a>Мобильная связь</a>
                    <ul>
                        <li><a href="UserController?command=to_payment_page&category=mobile&type=mts">MTS</a></li>
                        <li><a href="UserController?command=to_payment_page&category=mobile&type=a1">A1</a></li>
                        <li><a href="UserController?command=to_payment_page&category=mobile&type=life">Life</a></li>
                    </ul>
                </li>

                <li><a>Интернет</a>
                    <ul>
                        <li><a href="UserController?command=to_payment_page&category=internet&type=beltelecom">BelTelecom</a></li>
                        <li><a href="UserController?command=to_payment_page&category=internet&type=a1">A1</a></li>
                        <li><a href="UserController?command=to_payment_page&category=internet&type=cosmos">CosmosTV</a></li>
                        <li><a href="UserController?command=to_payment_page&category=internet&type=unet">Unet</a></li>
                    </ul>
                </li>
                <li><a href="#m3">Коммунальные платежи</a>
                    <ul>
                        <li><a href="#m3_1">Электроэнергия</a></li>
                        <li><a href="#m3_2">Газоснабжение</a></li>
                        <li><a href="#m3_3">Жилищно-коммунальные услуги</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        </div>
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
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>

</body>
</html>
