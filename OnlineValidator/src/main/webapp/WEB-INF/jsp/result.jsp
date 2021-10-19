<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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

    <section class="d-flex flex-column">
        <h2 class="mt-3 mb-0">Rapporto validazione <small>(versione
            validatore ${ risultatoValidazione.versioneValidatore })</small></h2>

        <span class="mt-2 report-date">Report di validazione generato in data <b>${ dataValidazione }</b></span>
        <span class="tipo-documento">Tipo documento selezionato: <b>${ risultatoValidazione.tipoDocumentoValidato }</b>; Formato: <b>${ risultatoValidazione.formatoDocumentoValidato }</b></span>
    </section>

    <section class="col-xl-12 mt-3 pl-0 pr-0">
        <c:choose>
            <c:when test="${ not empty risultatoValidazione and not empty risultatoValidazione.erroriDiValidazione }">
                <c:choose>
                    <c:when test="${ not risultatoValidazione.valido }"><h3 class="text-danger" role="alert">Risultato:
                        il file
                        non &egrave; valido</h3></c:when>
                    <c:otherwise><h3 class="text-success" role="alert">Risultato: il file &egrave;
                        valido</h3></c:otherwise>
                </c:choose>
                <%@ include file="common/result_button.jsp" %>
                <div class="container pr-0 pl-0">
                    <c:forEach items="${ risultatoValidazione.erroriDiValidazione }" var="singoloAssert"
                               varStatus="currentIteration">
                        <c:if test="${ singoloAssert.warning }">
                            <c:set var="classeCss" value="text-warning"/>
                            <c:set var="tipoDiv" value="warning-error"/>
                        </c:if>
                        <c:if test="${ singoloAssert.fatal }">
                            <c:set var="classeCss" value="text-danger"/>
                            <c:set var="tipoDiv" value="fatal-error"/>
                        </c:if>

                        <table id="tabella-assert-${ currentIteration.index }" class="${ tipoDiv } mb-3">
                            <tr class="row">
                                <th scope="row">Test</th>
                                <td>${ singoloAssert.test }</td>
                            </tr>
                            <tr class="row">
                                <th scope="row">Posizione</th>
                                <td>${ singoloAssert.location }</td>
                            </tr>
                            <tr class="row">
                                <th scope="row">Livello</th>
                                <td class="${ classeCss }">${ singoloAssert.fatal ? "FATAL" : "WARNING" }</td>
                            </tr>
                            <tr class="row">
                                <th scope="row">Descrizione</th>
                                <td class="${ classeCss }">${ singoloAssert.testo }</td>
                            </tr>
                        </table>
                    </c:forEach>
                </div>
            </c:when>
            <c:when test="${ not empty risultatoValidazione and not empty risultatoValidazione.descrizioneErroreXsd }">
                <h3 class="text-danger" role="alert">Risultato: il file non &egrave; valido</h3>
                <p>Si &egrave; verificato un errore in sede di validazione xsd.</p>
                <p class="text-danger">${ risultatoValidazione.descrizioneErroreXsd }</p>
                <%@ include file="common/result_button.jsp" %>
            </c:when>
            <c:otherwise>
                <h3 class="text-success" role="alert">Risultato: il file &egrave; valido</h3>
                <%@ include file="common/result_button.jsp" %>
            </c:otherwise>
        </c:choose>
    </section>

    <section class="w-100 d-flex flex-column">
        <h4>Documento validato</h4>
        <textarea readonly>${ risultatoValidazione.documentoValidato }</textarea>
    </section>

</main>

<%@ include file="common/footer.jsp" %>