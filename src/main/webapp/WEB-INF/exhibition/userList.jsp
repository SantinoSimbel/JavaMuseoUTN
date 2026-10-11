<%@page import="entities.Exhibition"%>
<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>
    <title>User list exhibition</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>
    <% LinkedList<Exhibition> exhibitionList = (LinkedList<Exhibition>) request.getAttribute("allExhibitions"); 
    String currentStatus = request.getParameter("status");%>
</head>

<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>


    <!-- Titulo y botones -->
    <div class="container mt-4 mb-5">

		<div class="d-flex justify-content-between align-items-center gap-2 mb-2">
    		<h1>Exhibiciones</h1>
    		<a href="index.jsp" class="btn btn-secondary text-nowrap"> Regresar al menu </a>
		</div>
        
        
		<div class="d-flex flex-column flex-md-row align-items-md-center align-items-start gap-4 mb-3">

    		<form action="ExhibitionServlet" method="GET" class="d-flex flex-column flex-sm-row align-items-sm-center gap-2">

        		<input type="hidden" name="operation" value="userList">
       			<label for="status" class="form-label mb-0 text-nowrap"> Filtrar por estado: </label>
        		<select name="status" id="status" class="form-select">
    				<option value="" <%= (currentStatus == null || currentStatus.isEmpty()) ? "selected" : "" %>>Todas</option>
    				<option value="Creado" <%= "Creado".equals(currentStatus) ? "selected" : "" %>>No empezadas</option>
    				<option value="Empezado" <%= "Empezado".equals(currentStatus) ? "selected" : "" %>>Empezadas</option>
				</select>

        		<button type="submit" class="btn btn-primary"> Filtrar </button>

    		</form>

		</div>
		
		
		<%if(exhibitionList.isEmpty()){%>
			<div class="alert alert-danger" role="alert">
 				No hay exhibiciones disponibles
			</div>
		<%}%>
        

        <!-- Listado -->
        <% for (Exhibition ex : exhibitionList) { %>
            <div class="card mb-3 shadow-sm">
                <!-- Contenido de la tarjeta -->
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h5 class="card-title mb-0"> <%= ex.getTitle() %> </h5>


                        <!-- Estado -->
                        <% if (ex.getStatus().equals("Creado")) { %>
                            <span class="badge text-bg-success"> No empezada </span>
                        <% } else if (ex.getStatus().equals("Empezado")) { %>
                            <span class="badge text-bg-warning"> Empezada </span>
                        <% } %>

                    </div>


                    <div class="d-flex flex-column flex-md-row align-items-center">

                        <div class="ms-0 ms-md-4 mb-3 mt-md-0">

                            <b>Algunos artículos:</b>
                            	<div>
                                	<% for (int i = 0; i < Math.min(3, ex.getItems().size()); i++) { %>
										<img src="<%= ex.getItems().get(i).getPicture() %>" alt="Imagen de <%= ex.getItems().get(i).getName() %>" style="width: 100px; max-width: 100%; height: 100px; object-fit: contain;">		
                                	<% } %>
                                </div>
                            <br> <b>Desde el día</b>
                            <%= ex.getStartDay().format( java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy") ) %>
                            <b>hasta el </b> <%= ex.getEndDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy") ) %>
                            <br> <b>Desde las </b><%= ex.getStartTime() %>
                            <b> hasta </b><%= ex.getEndTime() %>
                        </div>
                    </div>
                </div>
                <!-- Acciones -->
                <div class="card-footer">
                    <div class="d-flex justify-content-between align-items-center">


                        <!-- EXHIBICIÓN CREADA -->
                        <% if (ex.getStatus().equals("Creado")) { %>
                            <span class="text-muted small"> Aún no ha comenzado. </span>
                         <% } else if (ex.getStatus().equals("Empezado")) { %>
                            <span class="text-muted small"> Se encuentra en curso. </span>
                         <% } %>

                            <div class="d-flex align-items-center gap-2">

                                <!-- Ver mas -->
                                <form action="ExhibitionServlet" method="GET" class="d-inline">
                                    <input type="hidden" name="operation" value="detail">
                                    <input type="hidden" name="id" value="<%= ex.getId() %>">
                                    
                                    <button href="ExhibitionServlet?operation=detail&id=<%=ex.getId()%>" class="btn btn-secondary"> Ver mas </button>
                                </form>
                            </div>


                    </div>

                </div>

            </div>

        <% } %>


    </div>


    <%@ include file="/WEB-INF/common/scripts.jsp" %>

</body>

</html>