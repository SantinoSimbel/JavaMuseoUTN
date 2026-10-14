<%@page import="entities.Presentation"%>
<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User list presentation</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>
    <% 
    LinkedList<Presentation> presentationList = (LinkedList<Presentation>) request.getAttribute("allPresentations");
    String currentStatus = request.getParameter("status");
    %>
</head>
<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>

    <!-- Titulo y botones -->
    <div class="container mt-4 mb-5">
        <div class="d-flex justify-content-between align-items-center gap-2 mb-2">
            <h1>Presentaciones</h1>
        </div>

        <div class="d-flex flex-column flex-md-row align-items-md-center align-items-start gap-4 mb-3">
            <form action="PresentationServlet" method="GET" class="d-flex flex-column flex-sm-row align-items-sm-center gap-2">
                <input type="hidden" name="operation" value="userList">
                <label for="status" class="form-label mb-0 text-nowrap">
                    Filtrar por estado:
                </label>
                <select name="status" id="status" class="form-select">
                    <option value="" <%= (currentStatus == null || currentStatus.isEmpty()) ? "selected" : "" %>>Todas</option>
                    <option value="Creado" <%= "Creado".equals(currentStatus) ? "selected" : "" %>>No empezadas</option>
                    <option value="Empezado" <%= "Empezado".equals(currentStatus) ? "selected" : "" %>>Empezadas</option>
                </select>
                <button type="submit" class="btn btn-primary">
                    Filtrar
                </button>
            </form>
        </div>

        <% if(presentationList.isEmpty()){ %>
            <div class="alert alert-danger" role="alert">
                No hay presentaciones disponibles
            </div>
        <% } %>

        <!-- Contenedor Grid (Fila) -->
        <div class="row g-4">
            <% for (Presentation pre : presentationList) { %>
                <!-- Columnas adaptativas: 1 en móvil, 2 en tablets/pantallas medianas, 3 en pantallas grandes -->
                <div class="col-12 col-md-6 col-lg-4 d-flex align-items-stretch">
                    
                    <!-- Tarjeta con h-100 para que todas tengan la misma altura si están en la misma fila -->
                    <div class="card w-100 shadow-sm d-flex flex-column justify-content-between">
                        
                        <!-- Contenido de la tarjeta -->
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start gap-2 mb-3">
                                <h5 class="card-title mb-0">
                                    <%= pre.getTitle() %>
                                </h5>
                                <!-- Estado -->
                                <% if (pre.getStatus().equals("Creado")) { %>
                                    <span class="badge text-bg-success text-nowrap"> No empezada </span>
                                <% } else if (pre.getStatus().equals("Empezado")) { %>
                                    <span class="badge text-bg-warning text-nowrap"> Empezada </span>
                                <% } %>
                            </div>

                            <!-- Distribución interna vertical para diseño cuadrado -->
                            <div class="mb-3">
                                <span class="d-block mb-1"><b>Algunos artículos:</b></span>
                                <div class="d-flex gap-2 flex-wrap mb-3">
                                    <% for (int i = 0; i < Math.min(4, pre.getItems().size()); i++) { %>
                                        <img src="<%= pre.getItems().get(i).getPicture() %>" 
                                             alt="Imagen de <%= pre.getItems().get(i).getName() %>"  class="border rounded p-1" style="width: 69px; height: 70px; object-fit: contain;">
                                    <% } %>
                                </div>
                                <%= pre.getDescription() %><br><br>
                                <div class="small text-muted">
                                    <i class="bi bi-calendar-event"></i> 
                            		<b>El día </b><%= pre.getDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %> <br>
                                </div>
                                <div class="small text-muted mt-1">
                                    <i class="bi bi-clock"></i> 
                                    <b>Horario:</b> <%= pre.getStartTime() %> a <%= pre.getEndTime() %> hs
                                </div>
	                            <div class="small text-muted mt-1">
                                    <i class="bi bi-cash-stack"></i> 
                                    <b>Precio: </b> $<%= pre.getPrice().intValue() %>
                                </div>

                            </div>
                        </div>

                        <!-- Acciones (Pie de tarjeta fijado abajo) -->
                        <div class="card-footer bg-transparent border-top-0 pt-0">
                            <form action="PresentationServlet" method="GET" class="w-100">
                                <input type="hidden" name="operation" value="detail">
                                <input type="hidden" name="id" value="<%= pre.getId() %>">
                                <button type="submit" class="btn btn-secondary w-100"> 
                                    Ver más 
                                </button>
                            </form>
                        </div>

                    </div>
                </div>
            <% } %>
        </div> <!-- Fin de la fila Grid -->

    </div>

    <%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>
