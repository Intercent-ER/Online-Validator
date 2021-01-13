<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Upload result</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css"/>">
</head>
<body>

<c:if test="${!empty assertDiValidazione}">

    <table class="table">


        <c:forEach items="${assertDiValidazione}" var="singoloAssert">

            <c:if test="singoloAssert.isWarning">
                <c:var name="classeCss" value="classe-warning"/>
            </c:if>
            <c:if test="singoloAssert.isFatal">
                <c:var name="classeCss" value="classe-fatal"/>
            </c:if>

            <tr>test:var.test</tr>
            <tr>percorso:var.location</tr>
            <tr class="classeCss">errore:var.testo</tr>

        </c:forEach>
    </table>
</c:if>


dentro al forEach, dopo la valutazione sopra, qualcosa del genere


<tr>(celle...)
    <td class="${classeCss}"></td>
    (celle...)
</tr>


<h2>${message}</h2>
</body>
</html>