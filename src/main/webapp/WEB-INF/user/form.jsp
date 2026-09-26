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
		
		String errorMessage = (String) request.getAttribute("errorMessage");
		
	%>
</head>
<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class= "d-flex justify-content-between align-items-center mb-3 ">
			<h1>
				<%=editing ? "Mi cuenta" : "Crear cuenta" %>
			</h1>
		
			<a href= <%=editing ? "index.jsp" : "login.jsp" %> 
			   class = "btn btn-secondary">
				Volver
			</a>
		</div>
		
		<p class="text-muted mb-2"><%=editing ? 
			"Modifique los datos que quiera editar:" : "Complete sus datos:" %>
		</p>
		
		<!-- Mensajes de error -->
		<%if(errorMessage != null){%>
			<div class="alert alert-danger" role="alert">
 				<%= errorMessage %>
			</div>
		<%}%>
		
		<!-- Formularios -->
		<div class = "card shadow-sm">	
			<div class = "card-body">
				<form action="UserServlet" method="POST">
					<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
					<%if(editing){ %>
						<input type="hidden" name="dni" value="<%=user.getDni()%>">
					<%}%>
					<div class= "mb-3">
						<b><label class="form-label">Dni:</label></b>
						<input type="text" name="dni" class="form-control" <%= editing ? "disabled" : "" %> required value="<%=dni%>">
						
						<b><label class="form-label">Nombre:</label></b>
						<input type="text" name="name" class="form-control" required value="<%=name%>">
						
						<b><label class="form-label">Apellido:</label></b>
						<input type="text" name="surname" class="form-control" required value="<%=surname%>">
						
						<b><label class="form-label">Email:</label></b>
						<input type="text" name="email" class="form-control" required value="<%=email%>">
						
						<b><label class="form-label">Contraseña:</label></b>
						<input type="password" name="password" class="form-control"
							placeholder="<%= editing ? "••••••" : "" %>" <%= editing ? "" : "required" %>>
						
					</div>
					<div class="text-center">
					<button type="submit" class="btn btn-success">
						<%= editing ? "Guardar datos" : "Registrarse" %>
					</button>
					</div>
				</form>	
			</div>
		</div>
		<!-- Otras acciones -->
		<div class= "row mb-4">
			<%if(editing){ %>
				<!-- Cerrar session -->
				<div class= "col-12 col-md-6 mt-4">	
					<div class="card border-dark h-100 ">
					    <div class="card-body d-flex flex-column justify-content-between">
					        <h5 class="card-title text-dark">Cerrar sesión</h5>
				
					        <p class="card-text">
					            Cierra tu sesión temporalmente.
					        </p>
					
					        <form action="LoginServlet" method="POST"
					              onsubmit="return confirm('¿Está seguro de que desea cerrar sesión?');">
					              
					            <input type="hidden" name="operation" value="logout">
					
					            <button type="submit" class="btn btn-outline-dark">
					                Cerrar mi sesión
					            </button>
					        </form>
					    </div>
					</div>
				</div>
				<!-- Eliminar cuenta -->
				<div class= "col-12 col-md-6 mt-4">
					<div class="card border-danger h-100 ">
					    <div class="card-body d-flex flex-column justify-content-between">
					        <h5 class="card-title text-danger">Eliminar cuenta</h5>
				
					        <p class="card-text">
					            Esta acción eliminará tu cuenta y no podrás recuperar tus datos.
					        </p>
					
					        <form action="UserServlet" method="POST"
					              onsubmit="return confirm('¿Está seguro de que desea eliminar su cuenta?');">
					              
					            <input type="hidden" name="operation" value="delete">
					
					            <button type="submit" class="btn btn-outline-danger">
					                Eliminar mi cuenta
					            </button>
					        </form>
					    </div>
					</div>
				</div>
			<%}%>
		</div>
	</div>
	
<!--Script para que funcione el js de bootstrap-->	
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>