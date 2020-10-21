<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 05.10.2020
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://localhost:8080/mytag" prefix="mytag" %>

<html>
<head>
    <title>Cards</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>Ваши карты</b></h3>
        <hr>
        <div class="all">
            <input checked type="radio" name="respond" id="desktop">

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
                                    <img src="../../img/new.jpg" width="250px" height="150px">
                                    <p class="new_card">заказать карту</p>
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