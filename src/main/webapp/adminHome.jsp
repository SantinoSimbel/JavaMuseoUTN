<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin Home</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="style/style.css">
</head>
<body>
	<!-- navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<h1>Admin home</h1>
	<p>Seleccione que desea administrar:</p>
	<div style= "display: flex; flex-direction: column;">
		<a href="">Articulos</a>
		<a href="">Exhibiciones</a>
		<a href="">Presentaciones</a>
		<a href="CategoryServlet?operation=list">Categorias</a>
		<a href="">Usuarios</a>
	</div>
	
<!--Script para que funcione el js de bootstrap-->	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" 
crossorigin="anonymous"></script>
</body>
</html>