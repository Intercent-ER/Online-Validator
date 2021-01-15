<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Risultato validazione</title>
    <%@include file="common/css.jsp" %>
    <%@include file="common/script.jsp" %>

    <script type="text/javascript">

        function esportaXml() {

        }

        function esportaPdf() {

        }

    </script>
</head>
<body>

<%@ include file="common/header.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-xl-12">
            <h1 class="h1">Rapporto validazione</h1>
            <c:choose>
                <c:when test="${ not empty assertDiValidazione}">
                    <h2 style="color:red">Risultato: il file non &egrave; valido</h2>
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
                </c:when>
                <c:when test="${ not empty erroreXsd }">
                    <h2 style="color:red">Risultato: il file non &egrave; valido</h2>
                    <p>Si &egrave; verificato un errore in sede di validazione xsd.</p>
                    <blockquote>${ erroreXsd }</blockquote>
                </c:when>
                <c:otherwise>
                    <h2 style="color: darkgreen">Risultato: il file &egrave; valido</h2>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="row">
        <button type="button" class="btn btn-primary" onclick="esportaXml()">Esporta XML</button>
        &nbsp;
        <button type="button" class="btn btn-primary" onclick="esportaPdf()">Esporta PDF</button>
    </div>
    <div>
        <a href="./">Torna indietro</a>.
    </div>
</div>

<%@ include file="common/footer.jsp" %>
