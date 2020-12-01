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
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript" src="script/script.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.home" var="home"/>
<fmt:message bundle="${loc}" key="local.about" var="about"/>
<fmt:message bundle="${loc}" key="local.sign_in" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.sign_up" var="sign_up"/>
<fmt:message bundle="${loc}" key="local.logout" var="logout"/>
<fmt:message bundle="${loc}" key="local.my_cards" var="my_cards"/>
<fmt:message bundle="${loc}" key="local.my_accounts" var="my_accounts"/>
<fmt:message bundle="${loc}" key="local.transfers" var="transfers"/>
<fmt:message bundle="${loc}" key="local.payments" var="payments"/>
<fmt:message bundle="${loc}" key="local.settings" var="settings"/>
<fmt:message bundle="${loc}" key="local.admin" var="admin"/>
<fmt:message bundle="${loc}" key="local.cards" var="cards"/>
<fmt:message bundle="${loc}" key="local.accounts" var="accounts"/>
<fmt:message bundle="${loc}" key="local.users" var="users"/>
<fmt:message bundle="${loc}" key="local.transactions.transactions_history" var="transactions_history"/>

<header>
    <div class="navbar">
        <div class="logo">
            <a href="<c:url value="MainController?command=to_main_page"/>">ClumsyPay</a>
        </div>
        <div class="language">
            <form action="MainController">
                <input type="hidden" name="local" value="ru">
                <input type="hidden" name="command" value="change_language">
                <input type="image" src="img/ru.png">
            </form>
            <form action="MainController">
                <input type="hidden" name="local" value="uk">
                <input type="hidden" name="command" value="change_language">
                <input type="image" src="img/uk.png">
            </form>
        </div>

        <c:if test="${sessionScope.user == null}">
            <div class="authorization">
                <ul>
                    <li><a href="MainController?command=to_login_page">${sign_in}</a>
                    <li><a href="MainController?command=to_registration_page">${sign_up}</a>
                </ul>
            </div>
        </c:if>

        <c:if test="${sessionScope.user != null}">
            <div class="login">
                <ul class="nav">
                    <li>
                        <a href="UserController?command=to_user_page">${sessionScope.user.name!=null?sessionScope.user.name: "User"}</a>
                        <ul>
                            <li><a href="UserController?command=to_user_cards_page">${my_cards}</a></li>
                            <li><a href="UserController?command=to_user_accounts_page">${my_accounts}</a></li>
                            <li><a href="UserController?command=to_card_transfer_page">${transfers}</a></li>
                            <li><a href="UserController?command=to_payment_categories_page">${payments}</a></li>
                            <li><a href="UserController?command=to_user_transactions">${transactions_history}</a></li>
                            <li><a href="UserController?command=to_settings_page">${settings}</a></li>
                            <li><a href="UserController?command=logout">${logout}</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                <div class="login">
                    <ul class="nav">
                        <li>
                            <a href="AdminController?command=to_admin_page">${admin}</a>
                            <ul>
                                <li><a href="AdminController?command=to_all_cards_page">${cards}</a></li>
                                <li><a href="AdminController?command=to_all_accounts_page">${accounts}</a></li>
                                <li><a href="AdminController?command=to_all_users_page">${users}</a></li>
                                <li><a href="AdminController?command=to_all_transactions">${transactions_history}</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </c:if>
        </c:if>
    </div>
</header>