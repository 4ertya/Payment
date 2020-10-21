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


<html>
<head>
    <title>Card Info Page</title>
</head>
<body>
<jsp:useBean id="cardInfo" class="by.epamtc.payment.entity.CardInfo" scope="request"/>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <div class="card_info">

            <c:choose>
                <c:when test="${cardInfo.paymentSystem.name() eq 'VISA'}">
                    <img src="../../img/visaClassic.jpg" width="250px" height="150px" alt="VISA">
                </c:when>
                <c:when test="${cardInfo.paymentSystem.name() eq 'MASTERCARD'}">
                    <img src="../../img/masterWorld.jpg" width="250px" height="150px" alt="MASTERCARD">
                </c:when>
            </c:choose>

            <br>

            <div class="table">
                <table cellpadding="5" cellspacing="0" border="1">
                    <tr>
                        <th>${card_number}</th>
                        <td>${cardInfo.number}</td>
                    </tr>
                    <tr>
                        <td>${status}</td>
                        <td>${cardInfo.status}</td>
                        <td>
                            <form action="UserController?command=block_card" method="post">
                                <input type="hidden" name="card_number" value="${cardInfo.number}">
                                <input type="hidden" name="status" value="${cardInfo.status}">
                                <input type="hidden" name="page" value="${pageContext.request.getParameter("command")}">
                                <input type="submit" value="Заблокировать">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>${cardholder}</td>
                        <td>${cardInfo.ownerName} ${cardInfo.ownerSurname}</td>
                    </tr>
                    <tr>
                        <td>${exp_date}</td>
                        <td>${cardInfo.expDate}</td>
                        <td>
                            <form action="UserController?command=new_card" method="post">
                                <input type="hidden" name="card_number" value="${cardInfo.number}">
                                <input type="hidden" name="status" value="${cardInfo.status}">
                                <input type="submit" value="Перевыпустить">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>${account_number}</td>
                        <td>${cardInfo.accountNumber}</td>
                    </tr>
                    <tr>
                        <td>${balance}</td>
                        <td>${cardInfo.balance} ${cardInfo.currency}</td>
                        <td>
                            <button><a href="UserController?command=to_card_transfer_page">Переводы</a></button>
                        </td>
                    </tr>
                </table>
            </div>

        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
