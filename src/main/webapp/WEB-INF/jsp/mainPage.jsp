<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 21.09.2020
  Time: 10:16
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
    <title>Main Page</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="main_text">
        <p>Больше чем платежная система.</p>
        <p>Меньше чем банк.</p>
        <p>Рискни.</p>
    </div>
</div>


<%@include file="footer.jsp" %>
</body>
</html>
