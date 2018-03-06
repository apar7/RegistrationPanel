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
    <script src="<c:url value="/resources/loadWeightEdit.js" />"></script>
</head>
<body onload="loadWeightEdit($('#weight0'),$('#category0').val())">
<div class="topnav">
    <a href="<c:url value="/admin" />" class="active">Drabinki</a>
    <a href="<c:url value="/admin/competitors/1"/>">Zawodnicy</a>
    <a href="<c:url value="/admin/clubs"/>">Edytuj kluby</a>
</div>
<div class="data">
    <form method="post" action="<c:url value="/admin" />">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="category" value="${competitor.category}">
        <input type="hidden" name="belt" value="${competitor.belt}">
        <input type="hidden" name="weight" value="${competitor.weight}">
        <button type="submit">
            Wróć do kategorii
        </button>
    </form>
    <br>
    <form method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="registrationNumber" value="${competitor.registrationNumber}">
        <input type="hidden" name="id" value="${competitor.id}">
        <table>
            <tr>
                <td width="100px">Imię i nazwisko</td>
                <td><input name="name" value="${competitor.name}" type="text"></td>

            </tr>

            <tr>
                <td>Akademia</td>
                <td><input name="club" value="${competitor.club}" type="text"></td>

            </tr>

            <tr>
                <td>Kategoria</td>
                <td><select id="category0" name="category" required=""
                            onchange="loadWeight($('#weight0'),this.value)">
                    <option value="${competitor.category}">${competitor.category}</option>
                    <option value="seniorzy">Seniorzy</option>
                    <option value="juniorzy">Juniorzy</option>
                    <option value="kobiety">Kobiety</option>
                </select></td>
            </tr>
            <tr>
                <td>Waga</td>
                <td><select id="weight0" name="weight" class="select-style"
                            required="">
                    <option value=${competitor.weight}>${competitor.weight}</option>
                </select></td>
            </tr>


            <tr>
                <td>Pasy</td>
                <td><select name="belt" class="select-style" required="">
                    <option value="${competitor.belt}">${competitor.belt}</option>
                    <option value="białe">Biały</option>
                    <option value="niebieskie">Niebieski</option>
                    <option value="purpury">Purpurowy</option>
                    <option value="brązowe">Brązowy</option>
                    <option value="czarne">Czarny</option>
                </select></td>
            </tr>

            <tr>
                <td>Potwierdzenie</td>
                <td><select name="enabled" required>
                    <option value="${competitor.enabled}">${competitor.enabled}</option>
                    <option value=true>true</option>
                    <option value=false>false</option>
                </select></td>
            </tr>

            <tr>
                <td>Opłata</td>
                <td><select name="paid" required>
                    <option value="${competitor.paid}">${competitor.paid}</option>
                    <option value=true>true</option>
                    <option value=false>false</option>
                </select></td>
            </tr>

        </table>
        <input class="buttom" value="Wprowadź zmiany" type="submit"
               onclick="return confirm('Na pewno wprowadzić zmiany?');">
    </form>


</div>

</body>
</html>
