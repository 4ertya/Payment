<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 30.09.2020
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.set.personal_data" var="personal_data"/>
<fmt:message bundle="${loc}" key="local.auth.login" var="login"/>
<fmt:message bundle="${loc}" key="local.set.ru_name" var="ru_name"/>
<fmt:message bundle="${loc}" key="local.set.ru_surname" var="ru_surname"/>
<fmt:message bundle="${loc}" key="local.set.latin_name" var="latin_name"/>
<fmt:message bundle="${loc}" key="local.set.latin_surname" var="latin_surname"/>
<fmt:message bundle="${loc}" key="local.set.gender" var="gender"/>
<fmt:message bundle="${loc}" key="local.set.passport_series" var="passport_series"/>
<fmt:message bundle="${loc}" key="local.set.passport_number" var="passport_number"/>
<fmt:message bundle="${loc}" key="local.set.phone_number" var="phone_number"/>
<fmt:message bundle="${loc}" key="local.set.location" var="location"/>
<fmt:message bundle="${loc}" key="local.set.save" var="save"/>
<fmt:message bundle="${loc}" key="local.auth.error" var="error"/>
<fmt:message bundle="${loc}" key="local.auth.incorrect_data" var="incorrect_data"/>
<fmt:message bundle="${loc}" key="local.set.stored_successful" var="stored"/>
<fmt:message bundle="${loc}" key="local.set.home" var="home"/>

<jsp:useBean id="userData" class="by.epamtc.payment.entity.UserData" scope="request"/>
<jsp:useBean id="userDetail" class="by.epamtc.payment.entity.UserDetail" scope="request"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="content">
    <div class="form detail">
        <div class="block-text">Personal data</div>
        <c:if test="${sessionScope.warning_message !=null}">

            <c:choose>
                <c:when test="${sessionScope.warning_message eq 'incorrect_data'}">
                    <div class="error-text">${incorrect_data}</div>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'error'}">
                    <div class="error-text">${error}</div>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'data_stored'}">
                    <div class="good-text">${stored}.<a href="MainController?command=to_main_page">${home}</a></div>
                </c:when>
            </c:choose>

        </c:if>

        <form action="UserController?command=update_user_details" method="post">
            ${login}:
            <label>
                <input class="inputD" type="text" name="login" value="${userData.login}" required disabled>
            </label>
            e-mail:
            <label>
                <input class="inputD" type="email" name="email" value="${userData.email}" title="example@gmail.com"
                       required disabled>
            </label>
            <br>
            ${ru_name}:
            <label>
                <input class="inputD" type="text" pattern="^[а-яА-Я]+$" name="ru_name" value="${userDetail.ruName}"
                       required>
            </label>
            ${ru_surname}:
            <label>
                <input class="inputD" type="text" pattern="^[а-яА-Я]+$" name="ru_surname"
                       value="${userDetail.ruSurname}" required>
            </label>
            ${latin_name}:
            <label>
                <input class="inputD" type="text" pattern="^[a-zA-Z]+$" name="en_name" value="${userDetail.enName}"
                       required>
            </label>
            ${latin_surname}:
            <label>
                <input class="inputD" type="text" pattern="^[a-zA-Z]+$" name="en_surname"
                       value="${userDetail.enSurname}" required>
            </label>
            ${gender}:
            <label>
                <input class="inputD" type="text" name="gender" pattern="^[МЖ]{1}$" value="${userDetail.gender}"
                       title="M | Ж" required>
            </label>
            ${passport_series}:
            <label>
                <input class="inputD" type="text" name="passport_series" pattern="^[А-Я]{2}$"
                       value="${userDetail.passportSeries}" required>
            </label>
            ${passport_number}:
            <label>
                <input class="inputD" type="number" name="passport_number" pattern="^[0-9]{7}$"
                       value="${userDetail.passportNumber}" required>
            </label>
            ${phone_number}:
            <label>
                <input class="inputD" type="tel" pattern="\+375-[0-9]{2}-[0-9]{3}-[0-9]{2}-[0-9]{2}"
                       name="phone_number" value="${userDetail.phoneNumber}" title="+375-xx-xxx-xx-xx" required>
            </label>
            ${location}:
            <label>
                <input class="inputD" type="text" name="location" value="${userDetail.location}" required>
            </label>
            <input type="hidden" name="user_id" value="${userData.id}">

            <input class="button" type="submit" value="${save}">
        </form>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
