<%@page import="entities.Exhibition"%>
<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>
    <title>One exhibition</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>
    <% Exhibition ex = (Exhibition) request.getAttribute("oneExhibition"); %>
</head>

<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>


    <!-- Titulo y botones -->
    <div class="container mt-4 mb-5">

		<div class="d-flex justify-content-between align-items-center gap-2 mb-2">
    		<h1><%= ex.getTitle() %></h1>
    		<a href="ExhibitionServlet?operation=userList" class="btn btn-secondary text-nowrap"> Regresar </a>
		</div>
        
        
		<div class="d-flex flex-column flex-md-row align-items-md-center align-items-start gap-4 mb-3">


		</div>
		
        
        <div class="card shadow-sm">
            <div class="card-body">
                <div class="d-flex flex-column flex-md-row align-items-center">
                    <div class="ms-0 ms-md-4 mb-3 mt-md-0">
                        <%= ex.getDescription() %>
                        <br>
                        <% if (ex.getStatus().equals("Creado")) { %>
                            <b>Estado: </b> Aún no ha comenzado.
                        <% } else if (ex.getStatus().equals("Empezado")) { %>
                            <b>Estado: </b> Se encuentra en curso.
                        <% } %>
                        <br>
                        <b>Desde el día</b> <%= ex.getStartDay().format( java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy") ) %> <b>hasta el </b> <%= ex.getEndDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy") ) %>
                        <br>
                        <b>Desde las </b><%= ex.getStartTime() %> <b> hasta </b><%= ex.getEndTime() %>
                    </div>
                </div>
            </div>
        </div>


        <!-- Artículos  -->
        <% LinkedList<Item> items = ex.getItems(); %>
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
                
				<% if(ex.getItems().size() > 1){ %>
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
    </div>


    <%@ include file="/WEB-INF/common/scripts.jsp" %>

</body>

</html>