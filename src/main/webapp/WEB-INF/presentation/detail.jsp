<%@page import="entities.Presentation"%>
<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>
    <title>One presentation</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>
    <% Presentation pre = (Presentation) request.getAttribute("onePresentation"); %>
</head>

<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>


    <!-- Titulo y botones -->
    <div class="container mt-4 mb-5">

		<div class="d-flex flex-column flex-sm-row justify-content-between align-items-sm-center gap-3 mb-2">
    		<h1><%= pre.getTitle() %></h1>
    		<div class="d-flex gap-2 ms-sm-auto">
        		<a href="PresentationServlet?operation=userList" class="btn btn-secondary text-nowrap text-center"> Regresar </a>
        		<form action="PurchaseServlet" method="POST" class="m-0">
		            <input type="hidden" name="operation" value="buy">
	    	        <input type="hidden" name="id" value="<%= pre.getId() %>">
        	    	<button class="btn btn-success text-nowrap">Comprar</button>
        		</form>
    		</div>
		</div>

        
        
		<div class="d-flex flex-column flex-md-row align-items-md-center align-items-start gap-4 mb-3"></div>
		
        
        <div class="card shadow-sm">
            <div class="card-body">
                <div class="d-flex flex-column flex-md-row align-items-center">
                    <div class="ms-0 ms-md-4 mb-3 mt-md-0">
                        <%= pre.getDescription() %>
                        <br>
                        <% if (pre.getStatus().equals("Creado")) { %>
                            <b>Estado: </b> Aún no ha comenzado.
                        <% } else if (pre.getStatus().equals("Empezado")) { %>
                            <b>Estado: </b> Se encuentra en curso.
                        <% } %>
                        <br>
                        <b>El día </b><%= pre.getDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %> <br>
                        <br>
                        <b>Desde las </b><%= pre.getStartTime() %> <b> hasta </b><%= pre.getEndTime() %>
                        <br>
                        <b>Precio: </b> $<%= pre.getPrice().intValue() %>
                    </div>
                </div>
            </div>
        </div>


        <!-- Artículos  -->
        <% LinkedList<Item> items = pre.getItems(); %>
        <% if (items != null && !items.isEmpty()) { %>
            <h3 class="mt-4 mb-3 text-center">Artículos</h3>
            
            
            <div class="position-relative px-5 mx-auto" style="max-width: 750px;">
                
                <div id="carouselItems" class="carousel slide shadow-sm border rounded bg-white" data-bs-ride="carousel">
                    <div class="carousel-inner p-4">
                        <% boolean isFirst = true;
                        for (Item item : items) { %>
                            <div class="carousel-item <%= isFirst ? "active" : "" %>">
                                <div class="row align-items-center">
                                    <!-- Columna de la Imagen -->
                                    <div class="col-12 col-sm-4 text-center mb-3 mb-sm-0">
                                        <img src="<%= item.getPicture() %>" class="img-fluid rounded border" alt="<%= item.getName() %>" style="max-height: 150px; object-fit: cover;">
                                    </div>
                                    <!-- Columna de Datos -->
                                    <div class="col-12 col-sm-8 text-start">
                                        <h5 class="fw-bold mb-1"><%= item.getName() %></h5>
                                        <p class="text-muted small mb-2"><%= item.getDescription() %></p>
                                        <% if (item.getCategory() != null) { %>
                                            <span class="badge bg-secondary"><%= item.getCategory().getName() %></span>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% isFirst = false; } %>
                    </div>
                </div>
                
				<% if(pre.getItems().size() > 1){ %>
             	   <!-- Botón Izquierdo Flecha  -->
                	<button class="carousel-control-prev" type="button" data-bs-target="#carouselItems" data-bs-slide="prev" style="width: 40px; left: 0; opacity: 1;">
                    	<svg xmlns="http://w3.org" width="32" height="32" fill="#555" class="bi bi-chevron-left" viewBox="0 0 16 16">
                        	<path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
                    	</svg>
                    	<span class="visually-hidden">Anterior</span>
                	</button>

                	<!-- Botón Derecho Flecha -->
                	<button class="carousel-control-next" type="button" data-bs-target="#carouselItems" data-bs-slide="next" style="width: 40px; right: 0; opacity: 1;">
	                    <svg xmlns="http://w3.org" width="32" height="32" fill="#555" class="bi bi-chevron-right" viewBox="0 0 16 16">
    	                    <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
        	            </svg>
            	        <span class="visually-hidden">Siguiente</span>
                	</button>
                
                <% } %>

            </div>
        <% } %>

    


    <%@ include file="/WEB-INF/common/scripts.jsp" %>

</body>

</html>