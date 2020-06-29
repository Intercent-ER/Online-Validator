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
	href="<c:url value="/resources/css/index.css" />" />
</head>
<body>

	<div style="text-align: center">
		<form method="POST" action="/OnlineValidator/uploadFile"
			enctype="multipart/form-data">
			Select File: <input type="file" name="file" accept=".txt, text/plain" />
			<input type="submit" value="Upload File" /> <br> <br> 
			<select	type="select" id="name" name="id">
				<c:forEach items="${validatori}" var="val">
					<option type="int" value="${val.id}" id="${val.id}">${val.name}</option>
				</c:forEach>
			</select>
		</form>
	</div>

</body>
</html>