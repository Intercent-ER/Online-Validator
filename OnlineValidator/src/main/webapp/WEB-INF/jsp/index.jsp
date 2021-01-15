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

<div class="container row validation-container">
	<div
			class="col-lg-4 offset-lg-4 col-md-6 offset-md-3 col-sm-8 offset-sm-2 d-flex flex-column justify-content-center">
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
</div>

<%@ include file="common/footer.jsp" %>