<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online validator</title>
    <%@include file="common/css.jsp" %>
    <%@include file="common/script.jsp" %>
</head>
<body>

<%@ include file="common/header.jsp" %>

<div class="main-container container row pt-5">
    
    <div class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-sm-10 offset-sm-1 d-flex flex-column justify-content-center pt-2 pt-sm-4">
        <form:form method="POST" action="uploadFile.html"
                   enctype="multipart/form-data">

            <div class="d-flex flex-column container file-container">
                <span class="subtitle">File</span> <input type="file" name="file"
                                                          accept=".xml"/>
            </div>

            <div class="d-flex flex-column container file-type-container">
                <span class="subtitle">Tipo di file</span> <select
                    class="entity-select" type="select" name="id">
                <c:forEach items="${tipoDocumento}" var="val">
                    <option type="int" value="${val.idTipoDocumento}">${val.name.readableValue}</option>
                </c:forEach>
            </select>
            </div>
            <div class="d-flex container file-submit-container">
                <input class="submit-data" type="submit" value="Valida"/>
            </div>
        </form:form>
    </div>
    <div class="w-100"></div>
    
    <div class="description-container col-lg-8 offset-lg-2 col-md-10 offset-md-1 col-sm-12 d-flex flex-column rounded mt-5">
        <b>A cosa serve la piattaforma?</b>
        <span>
            La piattaforma consente di effettuare la validazione di documenti Peppol, in particolare: ordine, DDT, Risposta all&apos;ordine, Fattura e Nota di Credito, al fine di effettuare autonomamente delle prove di verifica della qualità dei singoli file xml.
            <!-- TODO: Aggiungere testo disclaimer informazioni -->
        </span>
    </div>
    <div class="w-100"></div>
    
    <div class="description-container col-lg-8 offset-lg-2 col-md-10 offset-md-1 col-sm-12 d-flex flex-column rounded mt-3">
        <b>Che dati vengono salvati?</b>
        <span>
            La piattaforma non salva nessun dato contenuto all'interno dei documenti processati 
            <!-- TODO: Aggiungere testo disclaimer informazioni -->
        </span>
    </div>
    
</div>

<%@ include file="common/footer.jsp" %>