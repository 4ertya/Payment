<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 12.11.2020
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://localhost:8080/mytag" prefix="mytag"%>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.about.text1" var="text1"/>
<fmt:message bundle="${loc}" key="local.about.text2" var="text2"/>
<fmt:message bundle="${loc}" key="local.about.text3" var="text3"/>
<fmt:message bundle="${loc}" key="local.about.text4" var="text4"/>

<html>
<head>
    <title>About</title>
</head>
<body>
<%@include file="header.jsp"%>
<div class="content">
    <div class="form main">
        <br>
        <br>
        <div class="about">
            <i>${text1}</i><br>
            <i>${text2}</i><br>
            <i>${text3}</i><br>
            <i>${text4}</i><br>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
