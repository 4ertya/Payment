<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 07.10.2020
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.card.card_number" var="card_number"/>
<fmt:message bundle="${loc}" key="local.card.cardholder" var="cardholder"/>
<fmt:message bundle="${loc}" key="local.card.exp_date" var="exp_date"/>
<fmt:message bundle="${loc}" key="local.card.account_number" var="account_number"/>
<fmt:message bundle="${loc}" key="local.card.balance" var="balance"/>
<fmt:message bundle="${loc}" key="local.card.status" var="status"/>
<fmt:message bundle="${loc}" key="local.card.block" var="block"/>
<fmt:message bundle="${loc}" key="local.card.unblock" var="unblock"/>


<html>
<head>
    <title>Card Info Page</title>
</head>
<body>
<jsp:useBean id="card" class="by.epamtc.payment.entity.Card" scope="request"/>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <div class="card_info">

            <c:choose>
                <c:when test="${card.paymentSystem.name() eq 'VISA'}">
                    <img src="img/visaClassic.jpg" width="250px" height="150px" alt="VISA">
                </c:when>
                <c:when test="${card.paymentSystem.name() eq 'MASTERCARD'}">
                    <img src="img/masterWorld.jpg" width="250px" height="150px" alt="MASTERCARD">
                </c:when>
            </c:choose>

            <hr>

            <div class="table">
                <table cellpadding="5" cellspacing="0" border="1">
                    <tr>
                        <th>${card_number}</th>
                        <td>${card.number}</td>
                    </tr>
                    <tr>
                        <th>${status}</th>
                        <td>${card.status}</td>

                            <c:choose>
                                <c:when test="${card.status eq 'ACTIVE'}">
                        <td>
                                    <form action="UserController?command=block_card&card_id=${card.id}" method="post">
                                        <input type="submit" value="${block}">
                                    </form>
                        </td>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                        <td>
                                        <form action="AdminController?command=unblock_card" method="post">
                                            <input type="hidden" name="card_id" value="${card.id}">
                                            <input type="submit" value="${unblock}">
                                        </form>
                                        </td>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>

                    </tr>
                    <tr>
                        <td>${cardholder}</td>
                        <td>${card.ownerName} ${card.ownerSurname}</td>
                    </tr>
                    <tr>
                        <td>${exp_date}</td>
                        <td>${card.expDate}</td>

                    </tr>
                    <tr>
                        <td>${account_number}</td>
                        <td>${card.accountNumber}</td>
                    </tr>
                    <tr>
                        <td>${balance}</td>
                        <td>${card.balance} ${card.currency}</td>
                    </tr>
                </table>
            </div>

        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
