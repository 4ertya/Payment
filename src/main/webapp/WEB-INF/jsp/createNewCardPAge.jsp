<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 16.10.2020
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Create new card</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>Выпустить новую карту</b></h3>
        <hr>
        <form action="UserController">
            <input type="hidden" name="command" value="create_new_card">
            <label>
                <select required name="account" class="select">
                    <option selected value=''>выберите счет</option>
                    <c:forEach items="${requestScope.accounts}" var="account">
                        <c:if test="${account.status eq 'ACTIVE'}">
                            <option value="${account.accountNumber}">
                                    ${account.accountNumber}
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </label>
            <label>
                <select name="system" class="select" required>
                    <option selected value=''>платежная система</option>
                    <option value="VISA">Visa</option>
                    <option value="MASTERCARD">Mastercard</option>
                </select>
            </label>
            <label>
                <select required name="term" class="select">
                    <option selected value=''>выберите срок действия</option>
                    <option value="1">1 год</option>
                    <option value="3">3 года</option>
                    <option value="5">5 лет</option>
                </select>
            </label>
            <input class="select_temp" type="submit" value="Создать карту">
        </form>
    </div>
</div>
</body>
</html>
