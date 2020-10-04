<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 21.09.2020
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}../css/style.css">
<script type="text/javascript" src="../../script/script.js"></script>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.home" var="home"/>
<fmt:message bundle="${loc}" key="local.about" var="about"/>
<fmt:message bundle="${loc}" key="local.sign_in" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.sign_up" var="sign_up"/>
<fmt:message bundle="${loc}" key="local.logout" var="logout"/>

<header>
    <div class="navbar">
        <div class="logo">
            <a href="<c:url value=" / " />">ClumsyPay</a>
        </div>
        <div class="navbar-menu">
            <ul>
                <li><a href="<c:url value=" / " />">${home}</a></li>
                <li><a href="<c:url value=" / " />">${about}</a></li>
            </ul>
        </div>
        <div class="language">
            <form action="Controller">
                <input type="hidden" name="command" value="change_language">
                <input type="hidden" name="local" value="ru">
                <input type="hidden" name="page" value="${pageContext.request.getParameter("command")}">
                <input type="image" src="../../img/ru.png">
            </form>
            <form>
                <input type="hidden" name="command" value="change_language">
                <input type="hidden" name="local" value="uk">
                <input type="hidden" name="page" value="${pageContext.request.getParameter("command")}">
                <input type="image" src="../../img/uk.png">
            </form>
        </div>

        <c:if test="${sessionScope.user == null}">
            <div class="authorization">
                <ul>
                    <li><a href="Controller?command=to_login_page">${sign_in}</a>
                    <li><a href="Controller?command=to_registration_page">${sign_up}</a>
                </ul>
            </div>
        </c:if>

        <c:if test="${sessionScope.user != null}">
            <div class="login">
                <ul id="nav">
                    <li>
                        <a href="Controller?command=to_user_page">${sessionScope.user.login}</a>
                        <ul>
                            <li><a href="Controller?command=to_setting_page">Настройки</a></li>
                            <li><a href="Controller?command=logout">${logout}</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </c:if>
    </div>
</header>