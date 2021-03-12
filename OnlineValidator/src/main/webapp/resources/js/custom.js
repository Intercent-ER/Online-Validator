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
    let uploadFileForm = $('#upload-file-form-id');
    uploadFileForm.formPrefill();
    uploadFileForm.data('formPrefill').readAll();
}

/**
 * Scrive la cache salvando i le selezioni digitate in form.
 */
function cacheAndSubmit() {
    let formUpload = $('#upload-file-form-id');
    formUpload.formPrefill();
    formUpload.data('formPrefill').writeAll();
    formUpload.submit();
}