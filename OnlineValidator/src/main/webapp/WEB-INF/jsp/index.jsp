<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Online validator</title>
	<link rel="stylesheet" type="text/css"
		  href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
	<link rel="stylesheet"
		  href="https://fonts.googleapis.com/css2?family=Titillium+Web">
	<link rel="stylesheet" type="text/css"
		  href="<c:url value="/resources/css/index.css"/>">
</head>
<body>

<header>
	<div class="container row justify-content-start align-items-center header-column-container">
		<div
				class="col-lg-4 col-md-3 d-flex flex-column align-items-center header-peppol-logo">
			<img class="peppol-logo-style"
				 src="<c:url value="/resources/images/peppol-logo-blue.svg"/>">
			<span>Peppol Authority Italia</span>
		</div>
		<div class="col-lg-4 offset-lg-0 col-md-6 offset-md-0 col-sm-8 offset-sm-2 col-xs-12 d-flex justify-content-center">
			<span class="title">Valida file</span>
		</div>
	</div>
</header>

<div class="container row validation-container">
	<div
			class="col-lg-4 offset-lg-4 col-md-6 offset-md-3 col-sm-8 offset-sm-2 d-flex flex-column justify-content-center">
		<form method="POST" action="/OnlineValidator/uploadFile"
			  enctype="multipart/form-data">

			<div class="d-flex flex-column container file-container">
				<span class="subtitle">File</span> <input type="file" name="file"
														  accept=".txt, text/plain"/>
			</div>

			<div class="d-flex flex-column container file-type-container">
				<span class="subtitle">Tipo di file</span> <select
					class="entity-select" type="select" name="id">
				<c:forEach items="${tipoDocumento}" var="val">
					<option type="int" value="${val.id}">${val.name}</option>
				</c:forEach>
			</select>
			</div>
			<div class="d-flex container file-submit-container">
				<input class="submit-data" type="submit" value="Valida"/>
			</div>
		</form>
	</div>
</div>

<footer class="d-flex flex-column">
	<div class="container">

		<div class="row div-logo-container">

			<div class="col-md-4 col-sm-6 d-flex justify-content-start">
				<div class="d-flex flex-column align-items-center peppol-logo-wrapper">
					<img
							src="<c:url value="/resources/images/peppol-logo-blue.svg"/>">
					<span class="span-peppol-logo">Peppol Authority Italia</span>
				</div>
			</div>

			<div class="col-md-4 col-sm-6 d-flex justify-content-center">
				<img
						src="<c:url value="/resources/images/agid-logo-white.svg"/>">
			</div>
			<div class="col-md-4 col-sm-6 d-flex justify-content-end">
				<img
						src="<c:url value="/resources/images/intercenter-logo.png"/>">
			</div>

		</div>
		<ul>
			<li><a
					href="https://www.google.it/maps/place/Via+Liszt,+21,+00144+Roma+RM/@41.8336525,12.4653778,17z/data=!3m1!4b1!4m5!3m4!1s0x13258ae3d27bf449:0x5aa2ce4a30bafdda!8m2!3d41.8336485!4d12.4675665">Via
				Liszt, 21 - 00144 Roma</a></li>
			<li>Telefono: +39 06852641</li>
			<li>Email: <a
					href="mailto:peppol@agid.gov.it?subject=%5Bpeppol.agid.gov.it%5D%20Richiesta%20informazioni">peppol@agid.gov.it</a>
			</li>
			<li>PEC: <a href="mailto:protocollo@pec.agid.gov.it">protocollo@pec.agid.gov.it</a></li>
			<li>Assistenza tecnica: <a
					href="mailto:assistenzaPEPPOL@agid.gov.it">assistenzaPEPPOL@agid.gov.it</a></li>
		</ul>
	</div>
	<div class="footer-bottom-container">
		<div class="container">
			<span>Privacy policy</span>
		</div>
	</div>
</footer>

<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/index.js"/>"></script>

</body>
</html>
