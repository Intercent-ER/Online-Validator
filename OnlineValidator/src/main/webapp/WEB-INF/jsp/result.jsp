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
            esporta('XML')
        }

        function esportaPdf() {
            esporta('PDF')
        }

        function esporta(tipoRendering) {
            document.getElementById('tipoRenderingId').value = tipoRendering;
            document.getElementById('renderingFormId').submit()
        }

    </script>
</head>
<body>

<%@ include file="common/header.jsp" %>

<div class="container" id="main-container">
    <div class="row">
        <div class="col-xl-12">
            <h2 class="mt-3 mb-0">Rapporto validazione</h2>
            <c:choose>
                <c:when test="${ not empty assertDiValidazione}">
                    <h3 class="mb-4" style="color:red">Risultato: il file non &egrave; valido</h3>
                    <div class="container pr-0 pl-0">
                        <c:forEach items="${ assertDiValidazione }" var="singoloAssert">
                            <c:if test="${ singoloAssert.warning }">
                                <c:set var="classeCss" value="classe-warning"/>
                                <c:set var="tipoDiv" value="warning-error"/>
                            </c:if>
                            <c:if test="${ singoloAssert.fatal }">
                                <c:set var="classeCss" value="classe-fatal"/>
                                <c:set var="tipoDiv" value="fatal-error"/>
                            </c:if>
                            <div class="${ tipoDiv } mb-3">
                                <div class="row">
                                    <b class="col-lg-1 col-md-2 col-sm-2 col-3">Test</b>
                                    <span class="col-lg-11 col-md-10 col-sm-10 col-9">${ singoloAssert.test }</span>
                                    <div class="w-100"></div>
                                    <b class="col-lg-1 col-md-2 col-sm-2 col-3">Posizione</b>
                                    <span class="col-lg-11 col-md-10 col-sm-10 col-9">${ singoloAssert.location }</span>
                                    <div class="w-100"></div>
                                    <b class="col-lg-1 col-md-2 col-sm-2 col-3">Livello</b>
                                    <span class="col-lg-11 col-md-10 col-sm-10 col-9 ${ classeCss }">${ singoloAssert.fatal ? "FATAL" : "WARNING" }</span>
                                    <div class="w-100"></div>
                                    <b class="col-lg-1 col-md-2 col-sm-2 col-3">Descrizione</b>
                                    <span class="col-lg-11 col-md-10 col-sm-10 col-9 ${ classeCss }">${ singoloAssert.testo }</span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:when test="${ not empty erroreXsd }">
                    <h3 class="mb-4" style="color:red">Risultato: il file non &egrave; valido</h3>
                    <p>Si &egrave; verificato un errore in sede di validazione xsd.</p>
                    <blockquote>${ erroreXsd }</blockquote>
                </c:when>
                <c:otherwise>
                    <h3 class="mb-4" style="color: darkgreen">Risultato: il file &egrave; valido</h3>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="row">
        <span>Report di validazione generato in data <b>${ dataValidazione }</b></span>
    </div>
    <br>

    <div class="d-flex flex-row function-button-container">
        <button type="button" class="btn btn-primary ml-1 mr-1" onclick="esportaXml()">Esporta XML</button>
        &nbsp;
        <button type="button" class="btn btn-primary ml-1 mr-1" onclick="esportaPdf()">Esporta PDF</button>
        &nbsp;
        <button type="button" class="btn btn-outline-primary ml-1 mr-1" onclick="location.href='./'">Effettua una nuova
            validazione
        </button>
    </div>
</div>

<form action="esportaRisultato.html" method="get" id="renderingFormId">
    <input name="tipoRendering" type="hidden" id="tipoRenderingId"/>
</form>

<%@ include file="common/footer.jsp" %>
