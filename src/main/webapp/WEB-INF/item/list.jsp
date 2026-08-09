<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>List item</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">


<%
	LinkedList<Item> itemsList = (LinkedList<Item>) request.getAttribute("allItems");
%>
</head>

<body>
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class="d-flex justify-content-between align-items-center mb-4">

			<h1>Articulos</h1>

			<a href="adminHome.jsp"
			   class = "btn btn-secondary">
			Regresar al menu
			</a>
		</div>
		
		<a href="ItemServlet?operation=new" 
		   class="btn btn-success mb-3">
			Nuevo Articulo 
		</a>
		
		<!-- Listado -->
		<% for (Item ite : itemsList){ %>
			<div class= "card mb-3 shadow-sm">
				<a href ="ItemServlet?operation=edit&id=<%=ite.getId()%>"
				class="text-decoration-none text-dark">
					<div class= "card-body">
						<h5 class = "card-title">
							<%= ite.getName()%>
						</h5>
						<div class="d-flex flex-column flex-md-row align-items-center">
							<img src="<%= ite.getPicture() %>" alt="Imagen de <%= ite.getName() %>" style="width: 200px; max-width: 100%; height: 200px; object-fit: contain;">		
							<div class="ms-0 ms-md-4 mt-3 mt-md-0">
								<br> <strong>Id: </strong><%=ite.getId() %> 
								<br> <b>Categoría: </b><%=ite.getCategory().getName() %>
								<br> <%=ite.getDescription() %>
						    </div>
						</div>
					</div>
				</a>
				<div class= "card-footer">
					<div class= "d-flex justify-content-end">
						<form action="ItemServlet" method="POST" onsubmit="return confirm('¿Eliminar articulo?');">
							<input type="hidden" name="operation" value= "delete">
							<input type="hidden" name="id" value="<%= ite.getId() %>">
							<button class = "btn btn-danger">Eliminar</button>
						</form>
					</div>
				</div>
			</div>
		<% } %>		
	</div>
<!--Script para que funcione el js de bootstrap-->	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>