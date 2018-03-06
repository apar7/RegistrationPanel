<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" media="all" />

    <script src="<c:url value="/resources/jquery.min.js" />"></script>
    <script>

        $(function() {
            $("#clubTyped").hide();
            $( "#unwindClubTyped").click(function() {
                $( "#clubTyped" ).toggle();
            });
        });

     function validateAndSend() {
            if (contactform.clubChosen.value == '' && contactform.clubTyped.value == '') {
                document.getElementById('noAcademy').innerHTML="Musisz wybrać akademię";
                return false;
            }
            else {
                contactform.submit();
            }
        }
    </script>
</head>
<body>
<div class="data"><p>Witamy w panelu zgłoszeniowym. Prosimy o uzupełnienie danych na temat klubu.</p></div>
<div class="form">
    <form method="POST" id="contactform" onsubmit="return validateAndSend()">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <p class="contact"><label for="city">Miasto</label></p>
        <input id="city" name="city" required tabindex="1" type="text">

        <p class="contact"><label for="clubChosen">Akademia<span id="noAcademy"> </span></label></p>
        <select id="clubChosen" name="clubChosen" class="select-style" tabindex="2" style="width:200px;">
            <option value=""></option>
            <c:forEach items="${clubs}" var="club">
                <option value="${club}">${club}</option></c:forEach>
        </select>

        <a id="unwindClubTyped" href="#"><label id="chooseClub">>>Mojej akademii nie ma na liście</label></a>
        <input id="clubTyped" name="clubTyped" class="clubTyped"
               placeholder="Wpisz, jeśli nie znalazłeś swojej akademii na liście" type="text">

        <p class="contact"> <label for="number"> Ilu zawodników zgłaszasz?</label></p>
        <input id="number" name="number" placeholder="" required="" tabindex="3"
               type="number" min="1" max="60"  style="width:200px;">

        <p class="contact"><label for="email">Email - konieczny do potwierdzenia rejestracji</label></p>
        <input id="email" name="email" placeholder="email@email.com" required type="email">
        <input class="buttom" tabindex="4" value="Dalej" type="submit">


        <br>
    </form>
</div>

</body>
</html>
