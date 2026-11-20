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
		
			<a href="${pageContext.request.contextPath}/<%= editing ? "" : "login.jsp" %>"
			   class = "btn btn-secondary">
				Volver
			</a>
		</div>
		<% if (editing){ %>
		
		<!-- Pestañas -->
        <ul class="nav nav-tabs mb-3">
            <li class="nav-item">
                <button class="nav-link active"
                        data-bs-toggle="tab"
                        data-bs-target="#datos"
                        type="button">
                    Mis datos
                </button>
            </li>
            <li class="nav-item">
                <button class="nav-link"
                        data-bs-toggle="tab"
                        data-bs-target="#compras"
                        type="button">
                    Mis compras
                </button>
            </li>
        </ul>
        
		<!-- Contenido de las pestañas -->
		 <div class="tab-content">
		 
		 	<!-- Pestaña Mis datos -->
		 	<div class="tab-pane fade show active" id="datos">
		 		<p class="text-muted mb-2">
                    Modifique los datos que quiera editar:
                </p>
                
				<!-- Mensajes de error -->
				<%if(errorMessage != null){%>
					<div class="alert alert-danger" role="alert">
		 				<%= errorMessage %>
					</div>
				<%}%>     
				<!-- Formularios -->           
                <%@ include file="formFields.jspf" %>
                
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
		 	</div>  <!-- Cierre de Mis datos -->
		 	
            <!-- Pestaña Mis compras -->
            <div class="tab-pane fade" id="compras">
                <h4 class="mb-3">Mis compras</h4>
            </div>
		 </div> <!-- Cierre de tab-content --> 
		 
		<%} else {%>
			<!-- Si no es editing: -->
			<p class="text-muted mb-2">Complete sus datos:</p>
	
	        <% if (errorMessage != null) { %>
	            <div class="alert alert-danger" role="alert">
	                <%= errorMessage %>
	            </div>
	        <% } %>
	        
	        <%@ include file="formFields.jspf" %>
    <% } %>
	</div>
	
<!--Script para que funcione el js de bootstrap-->	
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>