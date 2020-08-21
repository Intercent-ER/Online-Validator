<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Online validator</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
	<link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Titillium+Web">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/index.css"/>">
</head>
<body>

	<header class="d-flex flex-row">
		<img class="agid-logo-style" src="<c:url value="/resources/images/agid-logo.png"/>">
	</header>

	<div class="container">
		<div class="row" style="text-align: center">
			<div class="col"></div>
			<div class="col">
				<form method="POST" action="/OnlineValidator/uploadFile"
					enctype="multipart/form-data">
					<div class="d-flex flex-column">
					<h3 style="display: none">Il file è troppo pesante</h3>
						<div class="file-upload-container d-flex flex-row">
							<span class="uploaded-file-name">Nome file caricato</span>
							<label class="label-file-selector">Carica file <input
								class="file-selector" type="file" name="file"
								accept=".txt, text/plain" />
							</label>
						</div>
						<div class="file-submit-container d-flex flex-row">
							<select class="entity-select" type="select" id="name" name="id">
								<c:forEach items="${validatori}" var="val">
									<option type="int" value="${val.id}">${val.name}</option>
								</c:forEach>
							</select> <input class="submit-data" type="submit" value="Valida" />
						</div>
						
					</div>

				</form>
				
			</div>
			<div class="col"></div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/js/index.js"/>"></script>

</body>
</html>