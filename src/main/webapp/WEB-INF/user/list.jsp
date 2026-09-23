<%@page import="entities.User"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>List users</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		LinkedList<User> usersList = (LinkedList<User>) request.getAttribute("allUsers");
	%>
</head>

<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class="d-flex justify-content-between align-items-center mb-2">

			<h1>Usuarios</h1>

			<a href="adminHome.jsp"
			   class = "btn btn-secondary">
			Regresar al menu
			</a>
		</div>
		
		<a href="CategoryServlet?operation=new" 
		   class="btn btn-success mb-3">
			Nueva categoria 
		</a>
		
		<p class="text-muted mb-2">Seleccione un usuario para editar su rol:</p>
		
		<!-- Listado -->
		<div class= "row g-3">
			<% for (User use : usersList){ %>
				<div class = "col-12 col-md-6 col-lg-4">
					<div class= "card mb-3 shadow-sm ">
						<div class= "card-body <%= "admin".equals(use.getRole()) ? "bg-warning-subtle" : "bg-info-subtle" %>">
							<h5 class = "card-title">
								<%= use.getEmail()%>
							</h5>
							<div class="">
									 <b>Dni: </b><%=use.getDni() %>
								<br> <b>Nombre: </b><%=use.getName() %>
								<br> <b>Apellido: </b><%=use.getSurname() %>
								<br> <b>Rol:</b>
									<% if ("admin".equals(use.getRole())) { %>
									    <span class="badge text-bg-warning">Administrador</span>
									<% } else { %>
									    <span class="badge text-bg-info">Usuario</span>
									<% } %>
							</div>
						</div>
						<div class= "card-footer">
							<div class= "d-flex justify-content-end">
								<form action="CategoryServlet" method="POST" onsubmit="return confirm('¿Eliminar categoria?');">
									<input type="hidden" name="operation" value= "delete">
									<input type="hidden" name="id" value="<%= use.getId() %>">
									<button class = "btn btn-dark">Cambiar rol</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			<% } %>	
		</div>	
	</div>
<!--Script para que funcione el js de bootstrap-->
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>