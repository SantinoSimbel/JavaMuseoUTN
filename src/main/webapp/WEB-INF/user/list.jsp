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
		String errorMessage = (String) request.getAttribute("errorMessage");
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
		
		<p class="text-muted mt-3 mb-2">Seleccione un usuario para editar su rol:</p>
		
		<!-- Mensaje de error -->
		<%if(errorMessage != null){%>
			<div class="alert alert-danger" role="alert">
 				<%= errorMessage %>
			</div>
		<%}%>
		
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
								<br> <b>Rol: </b><%= "admin".equals(use.getRole()) ? "Administrador" : "Usuario" %>
							</div>
						</div>
						<div class= "card-footer">
							<div class= "d-flex justify-content-end">
								<form action="UserServlet" method="POST" onsubmit="return confirm('¿Desea cambiar el rol de este usuario?');">
									<input type="hidden" name="operation" value= "changeRole">
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