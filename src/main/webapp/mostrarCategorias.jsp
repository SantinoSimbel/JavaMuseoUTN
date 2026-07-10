<%@page import="entities.Category"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%
	LinkedList<Category> categoriesList = (LinkedList<Category>) request.getAttribute("allCategories");
%>
</head>

<body>
	<h1>Tabla categorias</h1>
	<a href="Index.jsp">Regresar al menu</a>

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