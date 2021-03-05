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
        <script type="text/javascript">

            function updateRappresentazioni(idTipoDocumento) {
                if (idTipoDocumento !== -1) {
                    $.ajax({
                        url: "ajax/displayRepresentations.html",
                        data: {
                            idTipoDocumento: idTipoDocumento
                        },
                        success: function (data) {
                            if (data != null && data !== '') {

                                let stringHtmlToBeReplaced = "<select id=\"lista-customizationid\" class=\"entity-select\" type=\"select\" name=\"idRappresentazione\">";

                                console.log(data)
                                var json = JSON.parse(data)
                                console.log(json)

                                for (var index = 0; index < json.length; index++) {
                                    let singleInstance = json[index];
                                    console.log(singleInstance)
                                    stringHtmlToBeReplaced += getHtml(singleInstance)
                                }

                                stringHtmlToBeReplaced += "</select>";

                                let listaCustomizationId = $("#lista-customizationid");
                                listaCustomizationId.replaceWith(stringHtmlToBeReplaced);
                                listaCustomizationId.prop('disabled', false);
                                $("#button-submit-id").prop('disabled', false);
                            }
                        },
                        error: function () {
                        }
                    });
                } else {
                    let listaCustomizationId = $("#lista-customizationid");
                    listaCustomizationId.replaceWith("<select id=\"lista-customizationid\" class=\"entity-select\" type=\"select\" name=\"idRappresentazione\"></select>");
                    listaCustomizationId.prop('disabled', true);
                    $("#button-submit-id").prop('disabled', true);
                }

            }

            /**
             * Produce l'HTML corrispondente al tag <option> di un oggetto RappresentazioneViewer.
             * @param rappresentazioneViewer è l'oggetto da cui p
             * @returns {string}
             */
            function getHtml(rappresentazioneViewer) {
                console.log("rappr.view=" + rappresentazioneViewer)
                return "<option type=\"int\" value=\"" + rappresentazioneViewer.idRappresentazione + "\">" + rappresentazioneViewer.dsDescrizione + "</option>";
            }

        </script>
    </head>
    <body>

    <a class="d-none" href="#main-container">Vai all'area di validazione</a>

    <%@ include file="common/header.jsp" %>

        <main id="main-container" class="container row pt-5">
            
            <div class="offset-lg-3 offset-md-2 offset-sm-1 mt-2">
                <h1>Area di validazione</h1>
            </div>
            
            <section class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-sm-10 offset-sm-1 d-flex flex-column justify-content-center pt-2 pt-sm-4">
                <form:form method="POST" action="uploadFile.html" enctype="multipart/form-data">
                    <div class="d-flex flex-column container file-container">
                        <label class="subtitle" for="carica-documento">Documento</label>
                        <input id="carica-documento" type="file" name="file" accept=".xml"/>
                    </div>
                    <div class="d-flex flex-column container file-type-container">
                        <label class="subtitle" for="lista-documenti">Tipo di documento</label>
                        <select id="lista-documenti" class="entity-select" type="select" name="idTipoDocumento">
                            <option onclick="updateRappresentazioni(-1)" type="int" value="-1" selected>Seleziona il
                                tipo di documento
                            </option>
                            <c:forEach items="${tipoDocumento}" var="val">
                                <option onclick="updateRappresentazioni(${val.idTipoDocumento})" type="int"
                                        value="${val.idTipoDocumento}">${val.name.readableValue}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="d-flex flex-column container file-type-container">
                        <label class="subtitle" for="lista-customizationid">Formato del documento</label>
                        <select id="lista-customizationid" class="entity-select" type="select" name="idRappresentazione"
                                disabled>
                            <!-- Filled with Ajax -->
                        </select>
                    </div>
                    <div class="d-flex container file-submit-container">
                        <input class="submit-data" id="button-submit-id" type="submit" value="Valida" disabled/>
                    </div>
                </form:form>
                <div class="w-100"></div>
            </section>

            <section>
                <div class="description-container col-lg-8 offset-lg-2 col-md-10 offset-md-1 col-sm-12 d-flex flex-column rounded mt-5">
                    <b>A cosa serve la piattaforma?</b>
                    <span>
                        La piattaforma consente di effettuare la validazione di documenti Peppol, quali ad esempio: Ordine, Documento di Trasporto, Fattura, Nota di Credito e ecc..., al fine di effettuare autonomamente delle prove di verifica della qualità dei singoli file xml.
                        Per ulteriori dettagli sui documenti che si possono validare e sugli schematron utilizzati è possibile consultare il seguente <a href="https://notier.regione.emilia-romagna.it/docs/" target="_blank">link</a>.
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