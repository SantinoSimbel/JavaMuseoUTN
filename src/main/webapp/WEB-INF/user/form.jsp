<%@page import="entities.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Form user</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		User user = (User) request.getAttribute("oneUser");
		boolean editing = (boolean) request.getAttribute("editing");
		String dni = user.getDni() == null ? "" : user.getDni();
		String name = user.getName() == null ? "" : user.getName();
		String surname = user.getSurname() == null ? "" : user.getSurname();
		String email = user.getEmail() == null ? "" : user.getEmail();
		String password = user.getPassword() == null ? "" : user.getPassword();
		String role = user.getRole() == null ? "" : user.getRole();
	%>
</head>
<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class= "d-flex justify-content-between align-items-center mb-3 ">
			<h1>
				<%=editing ? "Editar usuario" : "Crear usuario" %>
			</h1>
		
			<a href="login" 
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
				<form action="UserServlet" method="POST">
					<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
					<%if(editing){ %>
						<input type="hidden" name="id" value="<%=user.getId()%>">
					<%}%>
					<div class= "mb-3">
						<b><label class="form-label">Dni:</label></b>
						<input type="text" name="dni" class="form-control" required value="<%=dni%>">
						
						<b><label class="form-label">Nombre:</label></b>
						<input type="text" name="name" class="form-control" required value="<%=name%>">
						
						<b><label class="form-label">Apellido:</label></b>
						<input type="text" name="surname" class="form-control" required value="<%=surname%>">
						
						<b><label class="form-label">Email:</label></b>
						<input type="text" name="email" class="form-control" required value="<%=email%>">
						
						<b><label class="form-label">Contraseña:</label></b>
						<input type="password" name="password" class="form-control" required value="<%=password%>">
						
						<b><label class="form-label">Role:</label></b>
						<input type="text" name="role" class="form-control" required value="<%=role%>">
						
					</div>
					
					<button type="submit" class="btn btn-success">Registrarse</button>
				</form>		
			</div>
		</div>
	</div>
	
<!--Script para que funcione el js de bootstrap-->	
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>