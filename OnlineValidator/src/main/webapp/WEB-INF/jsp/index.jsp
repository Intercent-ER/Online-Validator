<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Effettua validazione - Validazione documenti Peppol</title>
    <%@include file="common/css.jsp" %>
    <%@include file="common/script.jsp" %>
    <script>

        window.onpageshow = function () {
            prefillFormAndReadCache();
        };

        $(document).ready(function () {
            $('#lista-documenti').on('change', function () {
                let idTipoDocumento = this.value;
                updateOptions(idTipoDocumento);
            })
        });
    </script>
</head>
<body>

<a class="d-none" href="#main-container">Vai all&apos;area di validazione</a>
<%@ include file="common/header.jsp" %>

<main id="main-container" class="container row pt-5">

    <div class="w-100 d-flex justify-content-center">
        <h2>Area di validazione</h2>
    </div>

    <div id="dialog-alert" style="display: none" title="Attenzione">
        <p>
            <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
            Si prega di selezionare un tipo documento dalla lista prima di procedere.
        </p>
    </div>

    <section
            class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-sm-10 offset-sm-1 d-flex flex-column justify-content-center pt-2 pt-sm-4">
        <form:form method="POST" action="uploadFile.html" enctype="multipart/form-data" id="upload-file-form-id"
                   modelAttribute="uploadFileForm">

            <div class="d-flex flex-column container file-container">
                <form:label id="carica-documento-label" class="subtitle" path="fileDocumento"
                            for="carica-documento">Documento</form:label>
                <form:input id="carica-documento" type="file" name="fileDocumento" path="fileDocumento" accept=".xml"
                            aria-invalid="true" aria-describedby="carica-documento-errors"/>
                <form:errors id="carica-documento-errors" class="text-danger alert alert-danger mt-2 ml-2" role="alert"
                             path="fileDocumento"/>
                <c:if test="${fileSizeError}">
                    <span class="text-danger alert alert-danger mt-2 ml-2" role="alert">Dimensione massima del file superata, il limite è 5MB.</span>
                </c:if>
                <c:if test="${ genericError }">
                    <span class="text-danger alert alert-danger mt-2 ml-2"
                          role="alert">Si è verificato un errore.</span>
                </c:if>
            </div>

            <div class="d-flex flex-column container file-type-container">
                <form:label id="lista-documenti-label" class="subtitle" path="idDocumento"
                            for="lista-documenti">Tipo di documento</form:label>
                <form:select id="lista-documenti" class="entity-select" path="idDocumento" type="select"
                             name="idDocumento" aria-invalid="true" aria-describedby="lista-documento-errors">
                    <option type="int" value="-1" id="default-selection" selected>Seleziona il tipo di documento
                    </option>
                    <c:forEach items="${tipoDocumento}" var="val">
                        <form:option type="int"
                                     value="${val.idTipoDocumento}">${val.name.readableValue}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors id="lista-documenti-errors" class="text-danger alert alert-danger mt-2 ml-2" role="alert" path="idDocumento"/>
            </div>

            <div class="d-flex flex-column container file-type-container">
                <form:label id="lista-customizationid-label" class="subtitle" path="idRappresentazioneDocumento"
                            for="lista-customizationid">Formato del documento</form:label>
                <form:select id="lista-customizationid" class="entity-select" type="select"
                             name="idRappresentazioneDocumento" path="idRappresentazioneDocumento"
                             disabled="true" aria-invalid="true" aria-describedby="lista-customizationid-errors">
                    <!-- Riempita con l'ausilio di Ajax -->
                </form:select>
                <form:errors id="lista-customizationid-errors" path="idRappresentazioneDocumento"
                             cssClass="text-danger alert alert-danger mt-2 ml-2" role="alert"/>
            </div>

            <div class="g-recaptcha-container d-flex flex-column align-items-center">
                <div class="g-recaptcha" data-sitekey="${ gRecaptchaSiteKey }" aria-invalid="true" aria-describedby="captcha-errors"></div>
                <form:errors id="captcha-errors" path="captcha" cssClass="text-danger alert alert-danger mt-2 ml-2" role="alert"/>
            </div>

            <div class="d-flex container file-submit-container">
                <button onclick="cacheAndSubmit()" class="btn submit-data" id="button-submit-id" title="Valida" disabled>Valida
                </button>
            </div>
        </form:form>
        <div class="w-100"></div>
    </section>

    <section class="col-lg-8 offset-lg-2 col-md-10 offset-md-1 col-sm-12 d-flex flex-column">
        <div class="description-container d-flex flex-column rounded mt-5">
            <b class="text-secondary">A cosa serve la piattaforma?</b>
            <span>
                        La piattaforma consente di effettuare la validazione di documenti Peppol (quali ad esempio Ordine, Documento di Trasporto, Fattura, Nota di Credito), al fine di effettuare autonomamente delle prove di verifica della qualit&agrave; dei singoli file xml.
                        Per ulteriori dettagli sui documenti che si possono validare e sugli schematron utilizzati &egrave; possibile consultare le specifiche tecniche al seguente <a
                    class="text-secondary" href="https://peppol-docs.agid.gov.it/" target="_blank">link</a>.
                    </span>
        </div>
        <div class="w-100"></div>
        <div class="description-container d-flex flex-column rounded mt-3">
            <b class="text-secondary">Che dati vengono salvati?</b>
            <span>La piattaforma non salva nessun dato contenuto all'interno dei documenti processati.</span>
        </div>
    </section>
</main>

<%@ include file="common/footer.jsp" %>
