<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Panel Rejestracyjny</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" media="all"/>
    <script src="<c:url value="/resources/jquery.min.js" />"></script>
</head>
<body>
<div class="topnav">
    <a href="<c:url value="/admin" />">Kategorie</a>
    <a href="<c:url value="/admin/competitors/1"/>">Zawodnicy</a>
    <a href="<c:url value="/admin/clubs"/>" class="active">Edytuj kluby</a>
</div>
<div class="data">


    <table style="width: auto">
        <tr>
            <td>Nr</td>
            <td>Nazwa</td>
            <td>Usuń</td>
        </tr>
        <c:forEach items="${clubList}" var="club" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td>
                    <form method="POST">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="text" name="name" value="${club.name}">
                        <input type="hidden" name="id" value="${club.id}">
                        <button type="submit">Zapisz</button>
                    </form>
                </td>
                <td>
                    <form method="post" action="<c:url value="/admin/deleteclub" />">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="id" value="${club.id}">
                        <input type="hidden" name="name" value="${club.name}">
                        <button type="submit"
                                onclick="return confirm('Na pewno usunąć klub?');">
                            Usuń
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td>
                <form method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="text" name="name">
                    <button type="submit">Dodaj</button>
                </form>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
