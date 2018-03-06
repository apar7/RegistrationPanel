<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" media="all"/>
    <script src="<c:url value="/resources/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/loadWeight.js" />"></script>
</head>
<body>
<div class="data"><p>Akademia: ${registration.club}, email: ${registration.email}  </p></div>
<div class="form">
    <form method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <c:forEach items="${registration.competitorList}" var="competitor" varStatus="status">

            <label >Zawodnik nr ${status.index+1}</label>
        <p class="contact"><label>Imię i nazwisko</label></p>
        <input id="name" name="competitorList[${status.index}].name" required="" type="text">

        <fieldset>
            <label> Kategoria</label>
            <select id="category${status.index}" name="competitorList[${status.index}].category" class="select-style" required=""
                    onchange="loadWeight($('#weight${status.index}'),this.value)">
                <option value=""></option>
                <option value="seniorzy">Seniorzy</option>
                <option value="juniorzy">Juniorzy</option>
                <option value="kobiety">Kobiety</option>
            </select>
            <label>Waga</label>
            <select id="weight${status.index}" name="competitorList[${status.index}].weight" class="select-style" required="">
                <option value=""><----</option>
            </select>
            <label>Pas</label>
            <select id="belt" name="competitorList[${status.index}].belt" class="select-style" required="">
                <option value=""></option>
                <option value="białe">Biały</option>
                <option value="niebieskie">Niebieski</option>
                <option value="purpury">Purpurowy</option>
                <option value="brązowe">Brązowy</option>
                <option value="czarne">Czarny</option>
            </select>
        </fieldset>
            <br><hr>
        </c:forEach>
        <input class="buttom" name="submit" id="submit" value="Wyślij" type="submit">
        <br>
    </form>
</div>

</body>
</html>
