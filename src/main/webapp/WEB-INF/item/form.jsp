<%@page import="entities.Item"%>
<%@page import="entities.Category"%>
<%@page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Form item</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">


<%
	Item item = (Item) request.getAttribute("oneItem");
	boolean editing = (boolean) request.getAttribute("editing");
	
	String name = item.getName() == null ? "" : item.getName();
	String description = item.getDescription() == null ? "" : item.getDescription();
	String picture = item.getPicture() == null ? "" : item.getPicture();
	
	 LinkedList<Category> categories = (LinkedList<Category>) request.getAttribute("allCategories");
%>
</head>
<body>
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class= "d-flex justify-content-between align-items-center mb-4 ">
			<h1>
				<%=editing ? "Editar articulo" : "Crear articulo" %>
			</h1>
		
			<a href="ItemServlet?operation=list" 
			   class = "btn btn-secondary">
				Volver
			</a>
		</div>
		
		<!-- Formularios -->
		<div class = "card shadow-sm">	
			<div class = "card-body">
				<form action="ItemServlet" method="POST">
					<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
					<%if(editing){ %>
						<input type="hidden" name="id" value="<%=item.getId()%>">
					<%}%>
					<div class= "mb-3">
						<b><label class="form-label">Nombre:</label></b>
						<input type="text" name="name" class="form-control" required value="<%=name%>">
						<b><label class="form-label">Descripción:</label></b>
						<input type="text" name="description" class="form-control" required value="<%=description%>">
						<b><label class="form-label">Link de imagen:</label></b>
						<input type="text" name="picture" class="form-control" required value="<%=picture%>">
						<b><label class="form-label">Seleccione la categoría:</label></b>
						<select name="category_id" class="form-select" required>
							<% for (Category category : categories) { %>
								<option value="<%=category.getId()%>"
    								<%= item.getCategory() != null &&
							        	item.getCategory().getId() == category.getId()
								        ? "selected"
        								: "" %>>

    								<%=category.getName()%>
								</option>
							<% } %>
						</select>
					</div>
					
					<button type="submit" class="btn btn-success">Guardar</button>
				</form>		
			</div>
		</div>
	</div>
<!--Script para que funcione el js de bootstrap-->	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>