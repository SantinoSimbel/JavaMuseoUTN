<%@page import="entities.PurchasePresentationDTO"%>
<%@page import="entities.Purchase"%>
<%@page import="java.util.LinkedList"%>
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
		LinkedList<PurchasePresentationDTO> purchasesWithPre =
				(LinkedList<PurchasePresentationDTO>) request.getAttribute("allPurchasesWithPre");
		
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
                <button class="nav-link active text-dark"
                        data-bs-toggle="tab"
                        data-bs-target="#datos"
                        type="button">
                    Mis datos
                </button>
            </li>
            <li class="nav-item">
                <button class="nav-link text-dark"
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
            	<p class="text-muted mb-2">
                    Seleccione una compra para ir a su presentacion:
                </p>
            	<% if(purchasesWithPre == null || purchasesWithPre.isEmpty()){ %>
            		<div class="alert alert-warning" role="alert">
                		El usuario todavia no ha hecho ninguna compra.
            		</div>
        		<% } %>

		        <!-- Contenedor Grid (Fila) -->
		        <div class="row g-4 mb-3">
		            <% for (PurchasePresentationDTO purPreDTO : purchasesWithPre) { %>
		                <div class="col-12 col-md-6 col-lg-4 d-flex align-items-stretch">
		                    <div class="card w-100 shadow-sm">
		                        <!-- Contenido de la tarjeta -->
		                        <div class="card-body d-flex flex-column">
			                        <a href="PresentationServlet?operation=detail&id=<%=purPreDTO.getPresentation().getId()%>"
			                        	class="text-decoration-none text-reset d-flex flex-column flex-grow-1">
			                            <div class="d-flex justify-content-between align-items-start gap-2 mb-3">
			                                <h5 class="card-title mb-0">
			                                    <%= purPreDTO.getPresentation().getTitle() %>
			                                </h5>
			                                <!-- Estado -->
			                                <% if (purPreDTO.getPresentation().getStatus().equals("Creado")) { %>
			                                    <span class="badge text-bg-success text-nowrap"> No empezada </span>
			                                <% } else if (purPreDTO.getPresentation().getStatus().equals("Empezado")) { %>
			                                    <span class="badge text-bg-warning text-nowrap"> Empezada </span>
			                                <% } else { %>
			                                	<span class="badge text-bg-secondary"> Terminada </span>
			                                <% } %>
			                            </div>
			
			                            <!-- Distribución interna vertical para diseño cuadrado -->
			                            <div class="mb-3">
			                                <%= purPreDTO.getPresentation().getDescription() %><br><br>
			                                <div class="">
			                                    <i class="bi bi-calendar-event"></i> 
			                            		<b>El día </b><%= purPreDTO.getPresentation().getDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %> <br>
			                                </div>
			                                <div class="mt-1">
			                                    <i class="bi bi-clock"></i> 
			                                    <b>Horario:</b> <%= purPreDTO.getPresentation().getStartTime() %> a <%= purPreDTO.getPresentation().getEndTime() %> hs
			                                </div>
				                            <div class="mt-1">
			                                    <i class="bi bi-cash-stack"></i> 
			                                    <b>Precio unitario actual: </b> $<%= purPreDTO.getPresentation().getPrice().intValue() %>
			                                </div>
			                            </div>
			                                
			                                <!-- Empiezan los cambios -->
			                                
											<!-- Datos compra -->
											<div class="mt-auto pt-3 border-top fs-5">
												<div class="mt-1">
											    	<i class="bi bi-ticket-perforated"></i>
											        <b>Cantidad de entradas:</b> <%= purPreDTO.getPurchase().getQuantity() %>
											    </div>	  
											    <div class="mt-1">
												    <i class="bi bi-receipt"></i>
												    <b>Total a pagar:</b> $<%= purPreDTO.getPurchase().getTotalPrice().intValue() %>
											    </div>
											</div>
										</a>
		                            </div>
		                        </div>
		                    </div>
		            <% } %>
		        </div> <!-- Fin de la fila Grid -->
                
            </div> <!-- Cierre mis compras -->
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