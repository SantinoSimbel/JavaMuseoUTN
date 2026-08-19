<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Admin Home</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
</head>
<body>
	<!-- navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<h1>Admin home</h1>
	<p>Seleccione que desea administrar:</p>
	<div style= "display: flex; flex-direction: column;">
		<a href="ItemServlet?operation=list">Articulos</a>
		<a href="">Exhibiciones</a>
		<a href="">Presentaciones</a>
		<a href="CategoryServlet?operation=list">Categorias</a>
		<a href="">Usuarios</a>
	</div>
	
<!--Script para que funcione el js de bootstrap-->	
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>