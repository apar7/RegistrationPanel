<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Panel rejestracyjny GP Polski BJJ</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" media="all" />
</head>
<body>
<div class="topnav">
    <a href="<c:url value="/" />">Info</a>
    <a href="<c:url value="/register"/>"   class="active">Rejestracja</a>
    <a href="<c:url value="/contact" />">Kontakt</a>
</div>
<iframe src="<c:url value="/form1" />" width=500 height=600 frameborder="no">
    <p>Your browser does not support iframes.</p>
</iframe>
</body>
</html>
