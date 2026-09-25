<%@page import="entities.Exhibition"%>
<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

    <title>List exhibition</title>

    <%@ include file="/WEB-INF/common/head.jsp" %>

    <%
        LinkedList<Exhibition> exhibitionList =
            (LinkedList<Exhibition>) request.getAttribute("allExhibitions");
    %>

</head>

<body>

    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>


    <!-- Titulo y botones -->
    <div class="container mt-4 mb-5">

        <div class="d-flex justify-content-between align-items-center mb-2">

            <h1>Exhibiciones</h1>

            <a href="adminHome.jsp"
               class="btn btn-secondary">

                Regresar al menu

            </a>

        </div>


        <a href="ExhibitionServlet?operation=new"
           class="btn btn-success mb-3">

            Nueva Exhibición

        </a>


        <p class="text-muted mb-2">
            Exhibiciones registradas:
        </p>


        <!-- Listado -->
        <% for (Exhibition ex : exhibitionList) { %>

            <div class="card mb-3 shadow-sm">

                <!-- Contenido de la tarjeta -->
                <div class="card-body">

                    <div class="d-flex justify-content-between align-items-center mb-3">

                        <h5 class="card-title mb-0">
                            <%= ex.getTitle() %>
                        </h5>


                        <!-- Estado -->
                        <% if (ex.getStatus().equals("Creado")) { %>

                            <span class="badge text-bg-success">
                                Creado
                            </span>

                        <% } else if (ex.getStatus().equals("Empezado")) { %>

                            <span class="badge text-bg-warning">
                                Empezado
                            </span>

                        <% } else { %>

                            <span class="badge text-bg-secondary">
                                Terminado
                            </span>

                        <% } %>

                    </div>


                    <div class="d-flex flex-column flex-md-row align-items-center">

                        <div class="ms-0 ms-md-4 mb-3 mt-md-0">

                            <b>Artículos:</b>

                            <ul>

                                <% for (Item item : ex.getItems()) { %>

                                    <li>
                                        <%= item.getName() %>
                                    </li>

                                <% } %>

                            </ul>


                            <b>Estado:</b>
                            <%= ex.getStatus() %>


                            <br>

                            <%= ex.getDescription() %>


                            <br>

                            <b>Desde el día</b>
                            <%= ex.getStartDay().format(
                                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                            ) %>

                            <b>hasta el</b>
                            <%= ex.getEndDay().format(
                                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                            ) %>


                            <br>

                            <b>Desde las</b>
                            <%= ex.getStartTime() %>

                            <b>hasta</b>
                            <%= ex.getEndTime() %>

                        </div>

                    </div>

                </div>


                <!-- Acciones -->
                <div class="card-footer">

                    <div class="d-flex justify-content-between align-items-center">


                        <!-- EXHIBICIÓN CREADA -->
                        <% if (ex.getStatus().equals("Creado")) { %>

                            <span class="text-muted small">
                                La exhibición aún no ha comenzado.
                            </span>


                            <div>

                                <!-- Editar -->
                                <a href="ExhibitionServlet?operation=edit&id=<%=ex.getId()%>"
                                   class="btn btn-primary">

                                    Editar

                                </a>


                                <!-- Eliminar -->
                                <form action="ExhibitionServlet"
                                      method="POST"
                                      class="d-inline"
                                      onsubmit="return confirm('¿Eliminar Exhibición?');">

                                    <input type="hidden"
                                           name="operation"
                                           value="delete">

                                    <input type="hidden"
                                           name="id"
                                           value="<%= ex.getId() %>">


                                    <button class="btn btn-danger">

                                        Eliminar

                                    </button>

                                </form>

                            </div>


                        <!-- EXHIBICIÓN EMPEZADA -->
                        <% } else if (ex.getStatus().equals("Empezado")) { %>

                            <span class="text-muted small">

                                🔒 La exhibición está en curso.
                                No se puede editar ni eliminar.

                            </span>


                        <!-- EXHIBICIÓN TERMINADA -->
                        <% } else { %>

                            <span class="text-muted small">

                                🔒 Edición no disponible:
                                la exhibición ya finalizó.

                            </span>


                            <!-- Eliminar -->
                            <form action="ExhibitionServlet"
                                  method="POST"
                                  onsubmit="return confirm('¿Eliminar Exhibición?');">

                                <input type="hidden"
                                       name="operation"
                                       value="delete">

                                <input type="hidden"
                                       name="id"
                                       value="<%= ex.getId() %>">


                                <button class="btn btn-danger">

                                    Eliminar

                                </button>

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