<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Risultato Validazione</title>
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

<form action="esportaRisultato.html" method="get" id="renderingFormId">
    <input name="tipoRendering" type="hidden" id="tipoRenderingId"/>
</form>

<div class="container main-container">
    <div class="row">
        <div class="col-xl-12 mt-3 pl-0 pr-0">
            <span class="h2 mb-0">Rapporto validazione <small>(versione schematron ${ risultatoValidazione.versioneSchematron })</small></span>

            <c:choose>
                <c:when test="${ not empty risultatoValidazione and not empty risultatoValidazione.erroriDiValidazione }">
                    <c:choose>
                        <c:when test="${ not risultatoValidazione.valido }"><h3 style="color:red">Risultato: il file
                            non &egrave; valido</h3></c:when>
                        <c:otherwise><h3 style="color:darkgreen">Risultato: il file &egrave;
                            valido</h3></c:otherwise>
                    </c:choose>
                    <%@ include file="common/result_button.jsp" %>
                    <div class="container pr-0 pl-0">
                        <c:forEach items="${ risultatoValidazione.erroriDiValidazione }" var="singoloAssert">
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
                <c:when test="${ not empty risultatoValidazione and not empty risultatoValidazione.descrizioneErroreXsd }">
                    <h3 style="color:red">Risultato: il file non &egrave; valido</h3>
                    <p>Si &egrave; verificato un errore in sede di validazione xsd.</p>
                    <blockquote>${ risultatoValidazione.descrizioneErroreXsd }</blockquote>
                    <%@ include file="common/result_button.jsp" %>
                </c:when>
                <c:otherwise>
                    <h3 style="color: darkgreen">Risultato: il file &egrave; valido</h3>
                    <%@ include file="common/result_button.jsp" %>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <textarea readonly>
        ${ risultatoValidazione.documentoValidato }
    </textarea>

    <div class="row">
        <span class="report-date">Report di validazione generato in data <b>${ dataValidazione }</b></span>
    </div>
    <br>

</div>

<%@ include file="common/footer.jsp" %>