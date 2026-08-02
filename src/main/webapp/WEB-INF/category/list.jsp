<%@page import="entities.Category"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>List categories</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">


<%
	//codigo java para que la pagina funcione correctamente ponerlo en head (segun profe)
	//no los que tengan comportamientos o animaciones 
	LinkedList<Category> categoriesList = (LinkedList<Category>) request.getAttribute("allCategories");
%>
</head>

<body>
	<div class= "container mt-4">
		<div class="d-flex justify-content-between align-items-center mb-4">

			<h1>Categorias</h1>

			<a href="CategoryServlet?operation=new" 
			   class="btn btn-success">
				Nueva categoria 
			</a>

		</div>
		<a href="adminHome.jsp" 
		   class = "btn btn-secondary">
			Regresar al menu
		</a>
		
		<% for (Category cat : categoriesList){ %>
			<div class= "card">
				<a href ="CategoryServlet?operation=edit&id=<%=cat.getId()%>">
				</a>
				<div class= "card-body">
					<h5 class = "card-title">
						<%= cat.getName()%>
					</h5>
					<p clas = "card-text">
						Id: <%= cat.getId() %>
					</p>
					
				</div>
				
			</div>
		<% } %>		
	</div>
	
	<h1>Tabla categorias</h1>
	<a href="adminHome.jsp">Regresar al menu</a>
	<a href ="CategoryServlet?operation=new">Crear nueva categoria</a>

	<table class="table table-hover table-bordered">
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
				<td>
					<a href ="CategoryServlet?operation=edit&id=<%=cat.getId()%>">
						<%= cat.getName()%>
					</a>
					<button>E</button>
					<form action="CategoryServlet" method="POST">
						<input type="hidden" name="operation" value= "delete">
						<input type="hidden" name="id" value="<%= cat.getId() %>">
						<button type= "submit" class = "btn btn-primary">B</button>
					</form>
				</td>
			</tr>
			<%} %>
		</tbody>
	</table>
<!--Script para que funcione el js de bootstrap-->	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>