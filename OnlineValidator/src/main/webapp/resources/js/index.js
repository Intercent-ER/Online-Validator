/**
 * Aggiorna dinamicamente il contenuto del tag "select" responsabile di contenere l'elenco delle
 * rappresentazioni del documento selezionato dall'utente.
 */
$(document).ready(function () {

    $('select').on('change', function (e) {
        var optionSelected = $("option:selected", this);
        var idTipoDocumento = this.value;

        if (idTipoDocumento !== -1) {

            console.log("UPDATE")

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
                        var json = JSON.parse(data)

                        // Itero il Json
                        for (var index = 0; index < json.length; index++) {
                            let singleInstance = json[index];
                            stringHtmlToBeReplaced += getHtml(singleInstance)
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

    })

});

/**
 * Produce l'HTML corrispondente al tag <option> di un oggetto RappresentazioneViewer.
 * @param rappresentazioneViewer è l'oggetto da cui partire per produrre il corrispondente HTML
 * @returns {string} contenuto HTML
 */
function getHtml(rappresentazioneViewer) {
    return "<option type=\"int\" value=\"" + rappresentazioneViewer.idRappresentazione + "\">" + rappresentazioneViewer.dsDescrizione + "</option>";
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

