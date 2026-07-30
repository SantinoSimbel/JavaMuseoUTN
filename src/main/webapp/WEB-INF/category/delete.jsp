<%@page import="entities.Category"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delete category</title>
<%
	LinkedList<Category> categoriesList = (LinkedList<Category>) request.getAttribute("allCategories");
%>
</head>
<body>

	<h1>Eliminar categoria</h1>
	<a href="testCrud.jsp">Regresar al menu</a>
	
	<!-- Formulario -->
	<form action="CategoryServlet" method="POST">
		<input type= "hidden" name="operation" value = "delete">
		<p>
			<label>Id:</label> <input type="text" name="id">
		</p>
		
		<button type="submit">Eliminar</button>
	</form>
	
	<!-- Tabla para mostar que se guardo -->
	<h2>Lista categorias</h2>
	<table border="1">
		<thead>
			<tr>
				<th>Id</th>
				<th>Name</th>
			</tr>
		</thead>
		<tbody>
		<% for (Category cat : categoriesList){ %>
			<tr>
				<td><%= cat.getId()%></td>
				<td><%= cat.getName()%></td>
			</tr>
			<%} %>
		</tbody>
	</table>
</body>
</html>