<%@page import="entities.Presentation"%>
<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>List presentation</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>
    <%LinkedList<Presentation> presentationList = (LinkedList<Presentation>) request.getAttribute("allPresentations");%>

</head>
<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>
    <!-- Titulo y botones -->
    <div class="container mt-4 mb-5">
        <div class="d-flex justify-content-between align-items-center mb-2">
            <h1>Presentaciones</h1>
            <a href="adminHome.jsp"
               class="btn btn-secondary">
                Regresar al menu
            </a>

        </div>
        <a href="PresentationServlet?operation=new" class="btn btn-success mb-3"> Nueva Presentación </a>
        <p class="text-muted mb-2"> Presentaciones registradas: </p>

        <!-- Listado -->
        <% for (Presentation pre : presentationList) { %>
            <div class="card mb-3 shadow-sm">
                <!-- Contenido de la tarjeta -->
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h5 class="card-title mb-0"> <%= pre.getTitle() %> </h5>


                        <!-- Estado -->
                        <% if (pre.getStatus().equals("Creado")) { %>
                            <span class="badge text-bg-success"> Creado </span>
                        <% } else if (pre.getStatus().equals("Empezado")) { %>
                            <span class="badge text-bg-warning"> Empezado </span>
                        <% } else { %>
                            <span class="badge text-bg-secondary"> Terminado </span>
                        <% } %>

                    </div>
                    <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="ms-0 ms-md-4 mb-3 mt-md-0">
                             <b>Artículos:</b>
                            <ul>
                                <% for (Item item : pre.getItems()) { %>
                                    <li> <%= item.getName() %> </li>
                                <% } %>
                            </ul>
                            <b>Estado:</b><%= pre.getStatus() %><br>
                            <b>Capacidad:</b> <%= pre.getCapacity() %>
                            <br><%= pre.getDescription() %> <br>
                            <b>El día</b><%= pre.getDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %> <br>
                            <b>Desde las</b> <%= pre.getStartTime() %>
                            <b>hasta</b> <%= pre.getEndTime() %>
                        </div>
                    </div>
                </div>

                <!-- Acciones -->
                <div class="card-footer">
                    <div class="d-flex justify-content-between align-items-center">
                        <!-- PRESENTACIÓN CREADA -->
                        <% if (pre.getStatus().equals("Creado")) { %>
                            <div></div>
                            <div class="d-flex flex-column flex-sm-row gap-2">
                                <!-- Editar -->
                                <a href="PresentationServlet?operation=edit&id=<%=pre.getId()%>" class="btn btn-primary"> Editar </a>

                                <!-- Eliminar -->
                                <form action="PresentationServlet" method="POST" class="d-inline" onsubmit="return confirm('¿Eliminar Presentación?');">
                                    <input type="hidden" name="operation" value="delete">
                                    <input type="hidden" name="id" value="<%= pre.getId() %>">

                                    <button class="btn btn-danger"> Eliminar </button>
                                </form>
                            </div>

                        <!-- PRESENTACIÓN EMPEZADA -->
                        <% } else if (pre.getStatus().equals("Empezado")) { %>
                            <span class="text-muted small"> 🔒 La presentación está en curso. No se puede editar ni eliminar. </span>
                        <!-- PRESENTACIÓN TERMINADA -->
                        <% } else { %>
                            <span class="text-muted small"> 🔒 Edición no disponible: la presentación ya finalizó. </span>
                            <!-- Eliminar -->
                            <form action="PresentationServlet" method="POST" onsubmit="return confirm('¿Eliminar Presentación?');">
                                <input type="hidden" name="operation" value="delete">
                                <input type="hidden" name="id" value="<%= pre.getId() %>">

                                <button class="btn btn-danger"> Eliminar </button>
                            </form>
                        <% } %>
                    </div>
                </div>
            </div>
        <% } %>
    </div>
    <!-- Script para que funcione el JS de Bootstrap -->
    <%@ include file="/WEB-INF/common/scripts.jsp" %>

</body>

</html>