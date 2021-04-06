/**
 * Produce l'HTML corrispondente al tag <option> di un oggetto RappresentazioneViewer.
 * @param rappresentazioneViewer è l'oggetto da cui partire per produrre il corrispondente HTML
 * @returns {string} contenuto HTML
 */
function getSingleOptionTagHtml(rappresentazioneViewer) {

    // Leggo dal sessionStorage
    let rappresentazioneId = window.sessionStorage.getItem('rappresentazione');

    if (rappresentazioneId !== null) {

        let needsToBeSelected = (rappresentazioneId + "").trim() === (rappresentazioneViewer.idRappresentazione + "").trim();
        if (needsToBeSelected) {
            return "<option type=\"int\" value=\""
                + rappresentazioneViewer.idRappresentazione
                + "\" selected>" + rappresentazioneViewer.dsDescrizione
                + "</option>";
        }
    }

    return "<option type=\"int\" value=\""
        + rappresentazioneViewer.idRappresentazione
        + "\">"
        + rappresentazioneViewer.dsDescrizione
        + "</option>";
}

/**
 * Resetta il contenuto della lista delle option dei customization identifier selezionabili.
 */
function resetCustomizationIdList() {
    $("#lista-customizationid").children().remove().end();
}

/**
 * Aggiorna dinamicamente il contenuto del tag "select" responsabile di contenere l'elenco delle
 * rappresentazioni del documento selezionato dall'utente.
 */
function updateOptions(idTipoDocumento) {

    // Reset della selezione precedente
    resetCustomizationIdList();

    if (idTipoDocumento !== -1 && idTipoDocumento !== null) {

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
                if (data !== null && data !== '') {

                    // Seleziono il "select" relativo alla lista di rappresentazioni selezionabili
                    let selectCustomizationId = $('#lista-customizationid');

                    // Effettuo il parsing in oggetto Javascript del Json ricevuto e lo itero
                    let json = JSON.parse(data);

                    for (let index = 0; index < json.length; index++) {

                        /*
                         * La variabile "singleInstance" contiene un oggetto di tipo
                         * com.onlinevalidator.dto.RappresentazioneViewer.
                         */
                        let singleInstance = json[index];
                        selectCustomizationId.append(getSingleOptionTagHtml(singleInstance));
                    }

                    // Abilito il pulsante di invio form
                    selectCustomizationId.prop('disabled', false);
                    $("#button-submit-id").prop('disabled', false);
                }
            },
            error: function () {
            }
        });
    } else {

        /*
         * Se l'utente ha ricliccato su "Seleziona il tipo documento", disabilito e svuoto il contenuto del
         * tag "select" preposto.
         */
        $("#button-submit-id").prop('disabled', true);
    }
}

function esportaXml() {
    esporta('XML');
}

function esportaPdf() {
    esporta('PDF');
}

function esporta(tipoRendering) {
    $('#tipoRenderingId').val(tipoRendering);
    $('#renderingFormId').submit();
}

function selectId(valueToSelect, selectId) {
    $("#" + selectId + " > option").each(function () {
        if (this.value === valueToSelect) {
            this.setAttribute('selected', '');
        } else {
            this.removeAttribute('selected');
        }
    });
}

/**
 * Legge la cache del browser al fine di recuperare la selezione indicata in precedenza (se presente).
 */
function prefillFormAndReadCache() {

    let tipoDocumento = window.sessionStorage.getItem('tipo_documento');
    if (tipoDocumento !== null && tipoDocumento !== undefined) {
        selectId(tipoDocumento, 'lista-documenti');
        updateOptions(tipoDocumento);
    }
}

/**
 * Scrive la cache salvando i le selezioni digitate in form.
 */
function cacheAndSubmit() {

    let tipoDocumento = $('#lista-documenti').val();
    let rappresentazioneDocumento = $('#lista-customizationid').val();

    if (tipoDocumento === null || rappresentazioneDocumento === null) {
        return false;
    }

    window.sessionStorage.setItem('tipo_documento', tipoDocumento);
    window.sessionStorage.setItem('rappresentazione', rappresentazioneDocumento);

    $('#upload-file-form-id').submit();
}