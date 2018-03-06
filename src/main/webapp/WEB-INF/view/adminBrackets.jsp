<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Panel Rejestracyjny</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" media="all"/>
    <script src="<c:url value="/resources/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/loadWeight.js" />"></script>
</head>
<body>
<div class="topnav">
    <a href="<c:url value="/admin" />" class="active">Kategorie</a>
    <a href="<c:url value="/admin/competitors/1"/>">Zawodnicy</a>
    <a href="<c:url value="/admin/clubs"/>">Edytuj kluby</a>
</div>
<br>
<form method="POST">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <fieldset>
        <label> Category</label>
        <select id="category0" name="category" class="select-style" required=""
                onchange="loadWeight($('#weight0'),this.value)">
            <option value=""></option>
            <option value="seniorzy">Seniorzy</option>
            <option value="juniorzy">Juniorzy</option>
            <option value="kobiety">Kobiety</option>
        </select>
        <label>Waga</label>
        <select id="weight0" name="weight" class="select-style" required="">
            <option value=""><----</option>
        </select>
        <label>Pas</label>
        <select id="belt" name="belt" class="select-style" required="">
            <option value=""></option>
            <option value="białe">Biały</option>
            <option value="niebieskie">Niebieski</option>
            <option value="purpury">Purpurowy</option>
            <option value="brązowe">Brązowy</option>
            <option value="czarne">Czarny</option>
        </select>
        <input class="buttom" name="submit" id="submit" value="Wybierz" type="submit">
    </fieldset>


</form>

</body>
</html>
