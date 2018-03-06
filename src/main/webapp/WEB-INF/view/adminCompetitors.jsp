<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Panel Rejestracyjny</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" media="all"/>
    <script src="<c:url value="/resources/jquery.min.js" />"></script>
    <script>
        function pageJump(url,nrStrony) {
            if(nrStrony.val()=='' || nrStrony.val()<1) return false;
            $('#jumpToPage').attr('action', url+nrStrony.val())
        }
    </script>
</head>
<body>
<div class="topnav">
    <a href="<c:url value="/admin" />">Kategorie</a>
    <a href="<c:url value="/admin/competitors/1"/>" class="active">Zawodnicy</a>
    <a href="<c:url value="/admin/clubs"/>">Edytuj kluby</a>
</div>
<div class="data">

    <form method="GET" action="<c:url value="/admin/findcompetitor" />">

        <p>Wyszukaj zawodnika:
            <input type="text" name="imie">
            <button type="submit">Szukaj</button>
        </p>
    </form>
    <form method="GET" action="<c:url value="/admin/findregistration" />">

        <p>Wyszukaj zgłoszenia:
            <input type="text" name="registrationNumber">
            <button type="submit">Szukaj</button>
        </p>
    </form>

    <table>
        <tr>
            <td>Nr</td>
            <td>Imię i nazwisko</td>
            <td>Klub</td>
            <td>Numer rejestracji</td>
            <td>Data zgłoszenia</td>
            <td>Potwierdzenie</td>
            <td>Opłata</td>
            <td>Kategoria</td>
            <td>Edytuj</td>
            <td>Usuń</td>
        </tr>
        <c:forEach items="${competitorList}" var="competitor" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td>${competitor.name}</td>
                <td>${competitor.club}</td>
                <td>${competitor.registrationNumber}</td>
                <td>
                    <jsp:useBean id="myDate" class="java.util.Date"/>
                    <c:set target="${myDate}" property="time" value="${competitor.registrationNumber}"/>
                    <fmt:formatDate value="${myDate}" var="myDate2" pattern="yyyy-MM-dd HH:mm:ss"/>
                        ${myDate2}
                </td>
                <td><c:if test="${competitor.enabled}">Tak</c:if></td>
                <td><c:if test="${competitor.paid}">Tak</c:if></td>
                <td>${competitor.category},${competitor.belt},${competitor.weight}</td>
                <td>
                    <form method="get" action="<c:url value="/admin/competitor${competitor.id}" />">
                        <button type="submit">Edytuj</button>
                    </form>
                </td>
                <td>
                    <form method="post" action="<c:url value="/admin/deletefrom${competitor.id}" />">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit"
                                onclick="return confirm('Na pewno usunąć zawodnika?');">
                            Usuń
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <div style="text-align: center">
    <a href="<c:url value="/admin/competitors/${pageNumber-1}"/>"
            <c:if test="${pageNumber==1}"> style="pointer-events:none; color:gray" </c:if>>Poprzednia strona</a>
    ${pageNumber}
    <a href="<c:url value="/admin/competitors/${pageNumber+1}"/>"
            <c:if test="${fn:length(competitorList) lt 100}"> style="pointer-events:none; color:gray" </c:if>>Następna
        strona</a>
    <form method="get" onsubmit="return     pageJump('<c:url value="/admin/competitors/"/>',$('#page'))"
          name="jumpToPage" id="jumpToPage">
        <input type="number" id="page" style="width: 45px">
        <button type="submit">Skocz do strony</button>
    </form></div>
</div>

</body>
</html>
