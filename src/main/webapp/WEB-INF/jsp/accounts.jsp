<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 15.10.2020
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://localhost:8080/mytag" prefix="mytag" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.account.number" var="account_number"/>
<fmt:message bundle="${loc}" key="local.account.balance" var="balance"/>
<fmt:message bundle="${loc}" key="local.account.currency" var="currency"/>
<fmt:message bundle="${loc}" key="local.account.opening_date" var="opening_date"/>
<fmt:message bundle="${loc}" key="local.account.status" var="status"/>
<fmt:message bundle="${loc}" key="local.account.my_accounts" var="my_accounts"/>
<fmt:message bundle="${loc}" key="local.card.block" var="block"/>
<fmt:message bundle="${loc}" key="local.card.unblock" var="unblock"/>
<fmt:message bundle="${loc}" key="local.account.open_account" var="new_account"/>
<fmt:message bundle="${loc}" key="local.account.account_created" var="account_created"/>
<fmt:message bundle="${loc}" key="local.auth.incorrect_data" var="incorrect_data"/>
<fmt:message bundle="${loc}" key="local.auth.error" var="error"/>
<fmt:message bundle="${loc}" key="local.account.add_data" var="add_data"/>
<fmt:message bundle="${loc}" key="local.account.wait" var="wait"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>${my_accounts}</b></h3>
        <c:if test="${sessionScope.warning_message !=null}">
            <c:choose>
                <c:when test="${sessionScope.warning_message eq 'login_incorrect_data'}">
                    <div class="error-text">${incorrect_data}</div>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'error'}">
                    <div class="error-text">${error}</div>
                </c:when>
                <c:when test="${sessionScope.warning_message eq 'account_created'}">
                    <div class="good-text">${account_created}</div>
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
        <c:if test="${requestScope.accounts!=null}">
        <label>
            <input class="searchKey" type="text" placeholder="Поиск">
        </label>
        <span class="searchCount"></span>
        <hr>
        </c:if>
        <table cellpadding="5" cellspacing="0" border="1" class="table_sort">
            <thead>
            <tr>
                <th>${account_number}</th>
                <th>${balance}</th>
                <th>${currency}</th>
                <th>${opening_date}</th>
                <th>${status}</th>
            </tr>
            <form action="UserController?command=create_new_account" method="post">
                <tr>
                    <td align="center" colspan="4">
                        <input type="submit" value="${new_account}">
                    </td>
                    <td align="center" colspan="3">
                        <label>
                            <select name="currency" required>
                                <option disabled selected value=''>${currency}</option>
                                <option value="USD">USD</option>
                                <option value="EUR">EUR</option>
                                <option value="RUB">RUB</option>
                                <option value="BYN">BYN</option>
                                <option value="GBP">GBP</option>
                            </select>
                        </label>
                    </td>
                </tr>
            </form>
            <tr class="no-result">
                <td>Совпадения не найдены</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.accounts}" var="account">
                <tr>
                    <td>${account.accountNumber}</td>
                    <td>${account.balance}</td>
                    <td>${account.currency}</td>
                    <td>${account.openingDate}</td>
                    <td>${account.status}</td>
                    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                        <c:choose>
                            <c:when test="${account.status eq 'ACTIVE'}">
                                <td>
                                    <form action="AdminController?command=block_account&account_id=${account.id}"
                                          method="post">
                                        <input type="submit" value="${block}">
                                    </form>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <form action="AdminController?command=unblock_account" method="post">
                                        <input type="hidden" name="account_id" value="${account.id}">
                                        <input type="submit" value="${unblock}">
                                    </form>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>


        </table>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
