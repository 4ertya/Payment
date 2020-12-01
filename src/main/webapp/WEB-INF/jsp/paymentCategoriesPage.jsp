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
<%@ taglib uri="http://localhost:8080/mytag" prefix="mytag" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.transactions.last_payments" var="last_payments"/>
<fmt:message bundle="${loc}" key="local.payment.electricity" var="electricity"/>
<fmt:message bundle="${loc}" key="local.payment.gas" var="gas"/>
<fmt:message bundle="${loc}" key="local.payment.internet" var="internet"/>
<fmt:message bundle="${loc}" key="local.payment.mobile" var="mobile"/>
<fmt:message bundle="${loc}" key="local.payment.utility" var="utility"/>
<fmt:message bundle="${loc}" key="local.payment.utilityBills" var="utilityBills"/>


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
                    <li><a>${mobile}</a>
                        <ul>
                            <li><a href="UserController?command=to_payment_page&category=mobile&type=mts">MTS</a></li>
                            <li><a href="UserController?command=to_payment_page&category=mobile&type=a1">A1</a></li>
                            <li><a href="UserController?command=to_payment_page&category=mobile&type=life">Life</a></li>
                        </ul>
                    </li>

                    <li><a>${internet}</a>
                        <ul>
                            <li><a href="UserController?command=to_payment_page&category=internet&type=beltelecom">BelTelecom</a>
                            </li>
                            <li><a href="UserController?command=to_payment_page&category=internet&type=a1">A1</a></li>
                            <li>
                                <a href="UserController?command=to_payment_page&category=internet&type=cosmos">CosmosTV</a>
                            </li>
                            <li><a href="UserController?command=to_payment_page&category=internet&type=unet">Unet</a>
                            </li>
                        </ul>
                    </li>
                    <li><a>${utilityBills}</a>
                        <ul>
                            <li>
                                <a href="UserController?command=to_payment_page&category=utility&type=electricity">${electricity}</a>
                            </li>
                            <li><a href="UserController?command=to_payment_page&category=utility&type=gas">${gas}</a>
                            </li>
                            <li>
                                <a href="UserController?command=to_payment_page&category=utility&type=communal">${utility}</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <c:if test="${not empty requestScope.transactions}">
        </div>
        <p>${last_payments}</p>
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
<%@include file="footer.jsp" %>

</body>
</html>
