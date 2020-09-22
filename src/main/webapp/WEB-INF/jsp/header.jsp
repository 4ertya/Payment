<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 21.09.2020
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
  <div class="navbar">
    <div class="logo">
      <a href="<c:url value=" /" />">ClumsyPay</a>
    </div>
    <div class="navbar-menu">
      <ul>
        <li><a href="<c:url value=" /" />">Home</a></li>
        <li><a href="<c:url value=" /" />">About</a></li>
      </ul>
    </div>
    <div class="authorization">
      <ul>
        <c:if test="${sessionScope.user != null}">
          <li> ${sessionScope.user.login}</li>

        </c:if>
        <c:if test="${sessionScope.user == null}">
          <li><a href="registrationPage.jsp">Sing in</a>
          <li><a href="loginPage.jsp">Sing up</a>
        </c:if>
      </ul>
    </div>
  </div>
</header>