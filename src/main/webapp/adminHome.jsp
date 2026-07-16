<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Home</title>
</head>
<body>
	<h1>Admin home</h1>
	<p>Seleccione que desea administrar:</p>
	<div style= "display: flex; flex-direction: column;">
		<a href="">Articulos</a>
		<a href="">Exhibiciones</a>
		<a href="">Presentaciones</a>
		<a href="CategoryServlet?operation=list">Categorias</a>
		<a href="">Usuarios</a>
	</div>
	
</body>
</html>