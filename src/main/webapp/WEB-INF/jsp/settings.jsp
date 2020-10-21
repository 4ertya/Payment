<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 30.09.2020
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="userData" class="by.epamtc.payment.entity.UserData" scope="request"/>
<jsp:useBean id="userDetail" class="by.epamtc.payment.entity.UserDetail" scope="request"/>

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

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="content">
    <div class="form detail">
        <div class="block-text">Personal data</div>
        <form action="UserController?command=update_user_details" method="post">
            ${login}:
            <label>
                <input class="inputD" type="text" name="login" value="${userData.login}" required disabled>
            </label>
            e-mail:
            <label>
                <input class="inputD" type="email" name="email" value="${userData.email}" required>
            </label>
            <br>
            ${ru_name}:
            <label>
                <input class="inputD" type="text" name="name" value="${userDetail.ruName}">
            </label>
            ${ru_surname}:
            <label>
                <input class="inputD" type="text" name="surname" value="${userDetail.ruSurname}">
            </label>
            ${latin_name}:
            <label>
                <input class="inputD" type="text" name="name" value="${userDetail.enName}" required>
            </label>
            ${latin_surname}:
            <label>
                <input class="inputD" type="text" name="surname" value="${userDetail.enSurname}"
                       required>
            </label>
            ${gender}:
            <label>
                <input class="inputD" type="text" name="gender" value="${userDetail.gender}" required>
            </label>
            ${passport_series}:
            <label>
                <input class="inputD" type="text" name="passport_series"
                       value="${userDetail.passportSeries}" required>
            </label>
            ${passport_number}:
            <label>
                <input class="inputD" type="number" name="passport_number"
                       value="${userDetail.passportNumber}" required>
            </label>
            ${phone_number}:
            <label>
                <input class="inputD" type="tel" name="phone_number"
                       value="${userDetail.phoneNumber}" required>
            </label>
            ${location}:
            <label>
                <input class="inputD" type="text" name="location" value="${userDetail.location}" required>
            </label>

            <input class="button" type="submit" value="${save}">
        </form>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
