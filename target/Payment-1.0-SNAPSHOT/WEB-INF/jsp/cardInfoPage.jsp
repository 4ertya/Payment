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


<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:useBean id="cardInfo" class="by.epamtc.payment.entity.CardInfo" scope="request"/>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <div class="card_info">
            <c:if test="${cardInfo.paymentSystem.name() eq 'VISA'}">
                <img src="../../img/visaClassic.jpg" width="250px" height="150px">
            </c:if>
            <c:if test="${cardInfo.paymentSystem.name() eq 'MASTERCARD'}">
                <img src="../../img/masterWorld.jpg" width="250px" height="150px">
            </c:if>
<br>
                <table cellpadding="5" >
                    <tr>
                        <td>Номер карты</td>
                        <td>${cardInfo.number}</td>
                    </tr>
                    <tr>
                        <td>Статус</td>
                        <td>${cardInfo.status}</td>
                        <td>
                            <form action="Controller?command=change_card_status" method="post">
                                <input type="hidden" name="card_number" value="${cardInfo.number}">
                                <input type="hidden" name="status" value="${cardInfo.status}">
                                <input type="submit" value="Заблокировать">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>Имя владельца</td>
                        <td>${cardInfo.ownerName} ${cardInfo.ownerSurname}</td>
                    </tr>
                    <tr>
                        <td>Срок окончания действия</td>
                        <td>${cardInfo.expDate}</td>
                        <td>
                            <form action="Controller?command=new_card" method="post">
                                <input type="hidden" name="card_number" value="${cardInfo.number}">
                                <input type="hidden" name="status" value="${cardInfo.status}">
                                <input type="submit" value="Перевыпустить">
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td>Номер счета</td>
                        <td>${cardInfo.account_number}</td>
                    </tr>
                    <tr>
                        <td>Баланс</td>
                        <td>${cardInfo.balance} ${cardInfo.currency}</td>
                        <td>
                            <form action="Controller?command=change_card_status" method="post">
                                <input type="hidden" name="card_number" value="${cardInfo.number}">
                                <input type="hidden" name="status" value="${cardInfo.status}">
                                <input type="submit" value="Пополнить">
                            </form>
                        </td>
                    </tr>
                </table>


        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
