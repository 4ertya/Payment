<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 17.10.2020
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>

<fmt:message bundle="${loc}" key="local.card.block" var="block"/>
<fmt:message bundle="${loc}" key="local.card.unblock" var="unblock"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
    <div class="form main">
        <h3><b>Счета</b></h3>
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
                <th>id</th>
                <th>number</th>
                <th>balance</th>
                <th>currency</th>
                <th>opening_date</th>
                <th>user</th>
                <th>status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.accounts}" var="account">
                <tr>
                    <td>${account.id}</td>
                    <td>${account.accountNumber}</td>
                    <td>${account.balance}</td>
                    <td>${account.currency}</td>
                    <td>${account.openingDate}</td>
                    <td>${account.name} ${account.surname}</td>
                    <td>${account.status}</td>
                    <c:if test="${account.status.name() eq 'ACTIVE'}">
                        <td>
                            <form action="AdminController?command=block_account&account_id=${account.id}" method="post">
                                <input type="submit" value="${block}">
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${account.status.name() eq 'BLOCKED'}">
                        <td>
                            <form action="AdminController?command=unblock_account&account_id=${account.id}" method="post">
                                <input type="submit" value="${unblock}">
                            </form>
                        </td>
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
