<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Risultato validazione</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css"/>">
</head>
<body>

<c:if test="${ not empty erroreXsd }">
    <h1>ERRORE XSD</h1>
    <h2>${ erroreXsd }</h2>
</c:if>

<c:if test="${ not empty assertDiValidazione}">

    <table class="table">
        <c:forEach items="${ assertDiValidazione }" var="singoloAssert">
            <c:if test="singoloAssert.isWarning">
                <c:set var="classeCss" value="classe-warning"/>
            </c:if>
            <c:if test="singoloAssert.isFatal">
                <c:set var="classeCss" value="classe-fatal"/>
            </c:if>
            <tr>
                <td>Test</td>
                <td>${ singoloAssert.test }</td>
            </tr>
            <tr>
                <td>Percorso</td>
                <td>${ singoloAssert.location }</td>
            </tr>
            <tr>
                <td>Errore</td>
                <td class="${ classeCss }">${ singoloAssert.testo }</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${ empty assertDiValidazione}">
    <h1>Validazione completata con successo</h1>
</c:if>

<div>
    <a href="../">Torna indietro</a>.
</div>

</body>
</html>