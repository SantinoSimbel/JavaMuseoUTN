<%@page import="entities.Item"%>
<%@page import="entities.Category"%>
<%@page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Form item</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	
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
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class= "d-flex justify-content-between align-items-center mb-3 ">
			<h1>
				<%=editing ? "Editar articulo" : "Crear articulo" %>
			</h1>
		
			<a href="ItemServlet?operation=list" 
			   class = "btn btn-secondary">
				Volver
			</a>
		</div>
				
		<p class="text-muted mb-2"><%=editing ? 
			"Modifique los campos que quiera editar:" : "Complete los campos:" %>
		</p>
		
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
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>