<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Risultato validazione - Validazione documenti Peppol</title>
        <%@include file="common/css.jsp" %>
        <%@include file="common/script.jsp" %>

        <script type="text/javascript">

            window.onpageshow = function () {
                window.sessionStorage.setItem("validationPassed", "true");
            }
        </script>
    </head>
    <body>

    <a class="d-none" href="#main-container">Vai al risultato della validazione</a>

    <%@ include file="common/header.jsp" %>

    <form action="esportaRisultato.html" method="get" id="renderingFormId">
        <input name="tipoRendering" type="hidden" id="tipoRenderingId"/>
    </form>

    <main id="main-container" class="container row">

        <h1 class="mb-0">Rapporto validazione <small>(versione
            validatore ${ risultatoValidazione.versioneValidatore })</small></h1>

        <section class="d-flex flex-column">
            <span class="report-date">Report di validazione generato in data <b>${ dataValidazione }</b></span>
            <span class="tipo-documento">Tipo documento selezionato: <b>${ risultatoValidazione.tipoDocumentoValidato }</b>; Formato: <b>${ risultatoValidazione.formatoDocumentoValidato }</b></span>
        </section>

        <section class="col-xl-12 mt-3 pl-0 pr-0">
            <c:choose>
                <c:when test="${ not empty risultatoValidazione and not empty risultatoValidazione.erroriDiValidazione }">
                    <c:choose>
                            <c:when test="${ not risultatoValidazione.valido }"><h3 style="color:red">Risultato: il file
                                non &egrave; valido</h3></c:when>
                            <c:otherwise><h2 style="color:darkgreen">Risultato: il file &egrave;
                                valido</h2></c:otherwise>
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
                                <div class="${ tipoDiv } mb-3" role="alert">
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
                        <blockquote style="color:red">${ risultatoValidazione.descrizioneErroreXsd }</blockquote>
                        <%@ include file="common/result_button.jsp" %>
                    </c:when>
                    <c:otherwise>
                        <h3 style="color: darkgreen">Risultato: il file &egrave; valido</h3>
                        <%@ include file="common/result_button.jsp" %>
                    </c:otherwise>
                </c:choose>
            </section>

            <section class="w-100">
                <textarea readonly>
                    ${ risultatoValidazione.documentoValidato }
                </textarea>
            </section>

        </main>

    <%@ include file="common/footer.jsp" %>