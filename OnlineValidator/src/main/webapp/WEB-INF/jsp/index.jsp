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

                $('select').on('change', function () {
                    let idTipoDocumento = this.value;
                    updateOptions(idTipoDocumento);
                })
            });
        </script>
    </head>
    <body>

        <a class="d-none" href="#main-container">Vai all'area di validazione</a>

        <%@ include file="common/header.jsp" %>

        <main id="main-container" class="container row pt-5">

            <div class="w-100 d-flex justify-content-center">
                <h1>Area di validazione</h1>
            </div>

            <div id="dialog-alert" style="display: none" title="Attenzione">
                <p>
                    <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
                    Si prega di selezionare un tipo documento dalla lista prima di procedere.
                </p>
            </div>

            <section
                class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-sm-10 offset-sm-1 d-flex flex-column justify-content-center pt-2 pt-sm-4">
                <form:form method="POST" action="uploadFile.html" enctype="multipart/form-data" id="upload-file-form-id">
                    <div class="d-flex flex-column container file-container">
                        <label class="subtitle" for="carica-documento">Documento</label>
                        <input id="carica-documento" type="file" name="file" accept=".xml"/>
                    </div>
                    <div class="d-flex flex-column container file-type-container">
                        <label class="subtitle" for="lista-documenti">Tipo di documento</label>
                        <select id="lista-documenti" class="entity-select" type="select" name="idTipoDocumento">
                            <option type="int" value="-1" id="default-selection" selected>Seleziona il tipo di documento
                            </option>
                            <c:forEach items="${tipoDocumento}" var="val">
                                <option type="int" value="${val.idTipoDocumento}">${val.name.readableValue}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="d-flex flex-column container file-type-container">
                        <label class="subtitle" for="lista-customizationid">Formato del documento</label>
                        <select id="lista-customizationid" class="entity-select" type="select" name="idRappresentazione"
                                disabled>
                            <!-- Riempita con l'ausilio di Ajax -->
                        </select>
                    </div>
                    <div class="g-recaptcha-container d-flex flex-column align-items-center">
                        <div class="g-recaptcha" data-sitekey="${ gRecaptchaSiteKey }">
                        </div>
                        <c:if test="${not captchaCompleted}">
                            <span class="captchaNotCompletedAlert mt-2">Per proseguire &egrave necessario completare il captcha</span>
                        </c:if>
                    </div>

                    <div class="d-flex container file-submit-container">
                        <button onclick="cacheAndSubmit()" class="submit-data" id="button-submit-id" disabled>Valida</button>
                    </div>
                </form:form>
                <div class="w-100"></div>
            </section>

            <section>
                <div class="description-container col-lg-8 offset-lg-2 col-md-10 offset-md-1 col-sm-12 d-flex flex-column rounded mt-5">
                    <b>A cosa serve la piattaforma?</b>
                    <span>
                        La piattaforma consente di effettuare la validazione di documenti Peppol (quali ad esempio Ordine, Documento di Trasporto, Fattura, Nota di Credito), al fine di effettuare autonomamente delle prove di verifica della qualit&agrave; dei singoli file xml.
                        Per ulteriori dettagli sui documenti che si possono validare e sugli schematron utilizzati &egrave; possibile consultare le specifiche tecniche al seguente <a
                    href="https://peppol-docs.agid.gov.it/" target="_blank">link</a>.
                    </span>
                </div>
                <div class="w-100"></div>
                <div class="description-container col-lg-8 offset-lg-2 col-md-10 offset-md-1 col-sm-12 d-flex flex-column rounded mt-3">
                    <b>Che dati vengono salvati?</b>
                    <span>La piattaforma non salva nessun dato contenuto all'interno dei documenti processati.</span>
                </div>
            </section>

        </main>

        <%@ include file="common/footer.jsp" %>
