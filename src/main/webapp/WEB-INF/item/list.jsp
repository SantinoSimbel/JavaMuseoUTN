<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>List item</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		LinkedList<Item> itemsList = (LinkedList<Item>) request.getAttribute("allItems");
	%>
</head>

<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class="d-flex justify-content-between align-items-center mb-2">

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
				
		<p class="text-muted mb-2">Seleccione un articulo para editarlo:</p>
		
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
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>