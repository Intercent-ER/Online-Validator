/**
 * Produce l'HTML corrispondente al tag <option> di un oggetto RappresentazioneViewer.
 * @param rappresentazioneViewer è l'oggetto da cui partire per produrre il corrispondente HTML
 * @returns {string} contenuto HTML
 */
function getSingleOptionTagHtml(rappresentazioneViewer) {
    return "<option type=\"int\" value=\"" + rappresentazioneViewer.idRappresentazione + "\">" + rappresentazioneViewer.dsDescrizione + "</option>";
}

/**
 * Legge la cache del browser al fine di recuperare la selezione indicata in precedenza (se presente).
 */
function prefillFormAndReadCache() {


    // Esecuzione chiamata Ajax
    $.ajax({
        url: "ajax/prefillFilters.html",
        async: true,
        datatype: "json",
        success: function (data) {
            if (data != null && data !== '' && data !== '{}') {

                // Effettuo il parsing in oggetto Javascript del Json ricevuto
                let json = JSON.parse(data)

                if (json !== null && json.filtroTipoDocumento !== null && json.filtroRappresentazione !== null) {

                    updateOptions(json.filtroTipoDocumento);
                    document.getElementById('lista-customizationid').value = (json.filtroRappresentazione + "");
                }
            }
        },
        error: function () {
        }
    });

}

/**
 * Aggiorna dinamicamente il contenuto del tag "select" responsabile di contenere l'elenco delle
 * rappresentazioni del documento selezionato dall'utente.
 */
function updateOptions(idTipoDocumento) {
    if (idTipoDocumento !== -1) {

        // Esecuzione chiamata Ajax
        $.ajax({
            url: "ajax/displayRepresentations.html",
            async: true,
            datatype: "json",
            data: {

                // Fornisco in ingresso il tipo del documento
                idTipoDocumento: idTipoDocumento
            },
            success: function (data) {
                if (data != null && data !== '') {

                    // Dichiaro la stringa che rappresenta il contenuto HTML da rimpiazzare
                    let stringHtmlToBeReplaced = "<select id=\"lista-customizationid\" class=\"entity-select\" type=\"select\" name=\"idRappresentazione\">";

                    // Effettuo il parsing in oggetto Javascript del Json ricevuto
                    let json = JSON.parse(data)

                    // Itero il Json
                    for (let index = 0; index < json.length; index++) {
                        let singleInstance = json[index];
                        stringHtmlToBeReplaced += getSingleOptionTagHtml(singleInstance)
                    }

                    // Chiudo il tag "select"
                    stringHtmlToBeReplaced += "</select>";

                    // Abilito la selezione sul customization id
                    let listaCustomizationId = $("#lista-customizationid");
                    listaCustomizationId.replaceWith(stringHtmlToBeReplaced);
                    listaCustomizationId.prop('disabled', false);

                    $("#button-submit-id").prop('disabled', false);
                }
            },
            error: function () {
                // TODO gestire errore mostrando un modal
            }
        });
    } else {

        /*
         * Se l'utente ha ricliccato su "Seleziona il tipo documento", disabilito e svuoto il contenuto del
         * tag "select" preposto.
         */
        $("#lista-customizationid").replaceWith("<select id=\"lista-customizationid\" class=\"entity-select\" type=\"select\" name=\"idRappresentazione\" disabled=\"true\"></select>");
        $("#button-submit-id").prop('disabled', true);
    }
}

/**
 * Scrive la cache salvando i le selezioni digitate in form.
 */
function cacheAndSubmit() {
    let formUpload = $('#upload-file-form-id');
    formUpload.formPrefill();
    let inputTipoDocumento = formUpload.find
    formUpload.data('formPrefill').writeAll();
    formUpload.submit();
}

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