<%@page import="entities.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Form category</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">


<%
	Category category = (Category) request.getAttribute("oneCategory");
	boolean editing = (boolean) request.getAttribute("editing");
	
	String name = category.getName() == null ? "" : category.getName();
%>
</head>
<body>
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class= "d-flex justify-content-between align-items-center mb-4 ">
			<h1>
				<%=editing ? "Editar categoria" : "Crear categoria" %>
			</h1>
		
			<a href="CategoryServlet?operation=list" 
			   class = "btn btn-secondary">
				Volver
			</a>
		</div>
		
		<!-- Formularios -->
		<div class = "card shadow-sm">	
			<div class = "card-body">
				<form action="CategoryServlet" method="POST">
					<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
					<%if(editing){ %>
						<input type="hidden" name="id" value="<%=category.getId()%>">
					<%}%>
					<div class= "mb-3">
						<label class="form-label">Nombre:</label>
						<input type="text" name="name" class="form-control" required value="<%=name%>">
					</div>
					
					<button type="submit" class="btn btn-success">Guardar</button>
				</form>		
			</div>
		</div>
	</div>
	
<!--Script para que funcione el js de bootstrap-->	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
crossorigin="anonymous"></script>
</body>
</html>