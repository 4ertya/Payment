<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 21.09.2020
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<%@include file="header.jsp" %>
<div class="content">
${pageContext.request.requestURI}
</div>



<%@include file="footer.jsp"%>
</body>
</html>
