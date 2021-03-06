<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 05.10.2020
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://localhost:8080/mytag" prefix="mytag" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.auth.error" var="error"/>
<fmt:message bundle="${loc}" key="local.auth.incorrect_data" var="incorrect_data"/>
<fmt:message bundle="${loc}" key="local.card.card_created" var="card_created"/>
<fmt:message bundle="${loc}" key="local.account.add_data" var="add_data"/>
<fmt:message bundle="${loc}" key="local.account.wait" var="wait"/>
<fmt:message bundle="${loc}" key="local.card.my_cards" var="my_cards"/>
<fmt:message bundle="${loc}" key="local.card.order_card" var="order_card"/>


<html>
<head>
    <title>Cards</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>${my_cards}</b></h3>
        <c:if test="${sessionScope.warning_message!=null}">
            <c:choose>
                <c:when test="${sessionScope.warning_message eq 'incorrect_data'}">
                    <mytag:infoMessage message="${incorrect_data}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'error'}">
                    <mytag:infoMessage message="${error}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'card_created'}">
                    <mytag:infoMessage message="${card_created}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'add_data'}">
                    <mytag:infoMessage message="${add_data}"/>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'wait'}">
                    <mytag:infoMessage message="${wait}"/>
                </c:when>
            </c:choose>
        </c:if>
        <hr>
        <div class="all">
            <label for="desktop"></label><input checked type="radio" name="respond" id="desktop">

            <article id="slider">
                <input checked type="radio" name="slider" id="switch1">

                <c:forEach items="${requestScope.cards}" var="card" varStatus="status">
                    <input type="radio" name="slider" id="switch${status.count+1}">
                </c:forEach>

                <div id="slides">
                    <div id="overflow">
                        <div class="image">
                            <c:forEach items="${requestScope.cards}" var="card" varStatus="status">
                                <article>
                                    <mytag:card card="${card}"/>
                                </article>
                            </c:forEach>
                            <article>
                                <div class="empty_card">
                                    <form action="UserController?command=to_create_new_card_page" method="post">
                                        <button class="select_card" type="submit"></button>
                                    </form>
                                    <img src="img/new.jpg" width="250px" height="150px" alt="new card">
                                    <p class="new_card">${order_card}</p>
                                </div>
                            </article>
                        </div>
                    </div>
                </div>
                <div id="controls">
                    <label for="switch1"></label>
                    <c:forEach items="${requestScope.cards}" var="card" varStatus="status">
                        <label for="switch${status.count+1}"></label>
                    </c:forEach>
                </div>
                <div id="active">
                    <label for="switch1"></label>
                    <c:forEach items="${requestScope.cards}" var="card" varStatus="status">
                        <label for="switch${status.count+1}"></label>
                    </c:forEach>
                </div>
            </article>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>

</body>
</html>