<script type="text/javascript">
    function nuovaValidazione() {
        window.sessionStorage.removeItem('tipo_documento');
        window.sessionStorage.removeItem('rappresentazione');
        window.sessionStorage.removeItem('file_selezionato');
        window.location.href = './';
    }
</script>
<div class="d-flex flex-row function-button-container mt-2 mb-4">
    <button type="button" class="btn ml-1 mr-1 display-none" title="Esporta XML" onclick="esportaXml()">Esporta XML</button>
    <button type="button" class="btn mr-1" title="Esporta PDF" onclick="esportaPdf()">Esporta PDF</button>
    <button type="button" class="btn ml-1 mr-1" title="Effettua nuova validazione" onclick="nuovaValidazione()">Effettua una nuova
        validazione
    </button>
</div>
