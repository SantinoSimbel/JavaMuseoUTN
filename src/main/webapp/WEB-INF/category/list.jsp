<%@page import="entities.Category"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>List categories</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		//codigo java para que la pagina funcione correctamente ponerlo en head (segun profe)
		//no los que tengan comportamientos o animaciones 
		LinkedList<Category> categoriesList = (LinkedList<Category>) request.getAttribute("allCategories");
	%>
</head>

<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class="d-flex justify-content-between align-items-center mb-2">

			<h1>Categorias</h1>

			<a href="adminHome.jsp"
			   class = "btn btn-secondary">
			Regresar al menu
			</a>
		</div>
		
		<a href="CategoryServlet?operation=new" 
		   class="btn btn-success mb-3">
			Nueva categoria 
		</a>
		
		<p class="text-muted mb-2">Seleccione una categoria para editarla:</p>
		
		<!-- Listado -->
		<% for (Category cat : categoriesList){ %>
			<div class= "card mb-3 shadow-sm">
				<a href ="CategoryServlet?operation=edit&id=<%=cat.getId()%>"
				class="text-decoration-none text-dark">
					<div class= "card-body">
						<h5 class = "card-title">
							<%= cat.getName()%>
						</h5>
					</div>
				</a>
				<div class= "card-footer">
					<div class= "d-flex justify-content-end">
						<form action="CategoryServlet" method="POST" onsubmit="return confirm('¿Eliminar categoria?');">
							<input type="hidden" name="operation" value= "delete">
							<input type="hidden" name="id" value="<%= cat.getId() %>">
							<button class = "btn btn-danger">Eliminar</button>
						</form>
					</div>
				</div>
			</div>
		<% } %>		
	</div>
<!--Script para que funcione el js de bootstrap-->
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>