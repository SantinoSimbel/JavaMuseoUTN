<%@page import="entities.Item"%>
<%@page import="entities.Event"%>
<%@page import="entities.Presentation"%>
<%@page import="entities.Exhibition"%>
<%@page import="java.util.LinkedList" %>
<%@page import="java.time.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>List event</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		LinkedList<Event> eventsList = (LinkedList<Event>) request.getAttribute("allEvents");
	%>
</head>

<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class="d-flex justify-content-between align-events-center mb-2">

			<h1>Eventos</h1>

			<a href="adminHome.jsp"
			   class = "btn btn-secondary">
			Regresar al menu
			</a>
		</div>
		
		<a href="EventServlet?operation=new" 
		   class="btn btn-success mb-3">
			Nuevo Evento 
		</a>
				
		<p class="text-muted mb-2">Seleccione un articulo para editarlo:</p>
		
		<!-- Listado -->
		<% for (Event eve : eventsList){ %>
			<div class= "card mb-3 shadow-sm">
				<a href ="EventServlet?operation=edit&id=<%=eve.getId()%>"
				class="text-decoration-none text-dark">
					<div class= "card-body">
						<h5 class = "card-title">
							<%= eve.getTitle()%>
						</h5>
						<div class="d-flex flex-column flex-md-row align-events-center">
							<img src="<%= eve.getItem().getPicture() %>" alt="Imagen de <%= eve.getItem().getName() %>" style="width: 200px; max-width: 100%; height: 200px; object-fit: contain;">		
							<div class="ms-0 ms-md-4 mt-3 mt-md-0">
								<br> <b>Categoría: </b><%=eve.getItem().getCategory().getName() %>
								<br> <%=eve.getDescription() %>
								<br> Desde las <%=eve.getStartTime() %> hasta las <%=eve.getEndTime() %>
						    </div>
						</div>
					</div>
				</a>
				<div class= "card-footer">
					<div class= "d-flex justify-content-end">
						<form action="EventServlet" method="POST" onsubmit="return confirm('¿Eliminar Evento?');">
							<input type="hidden" name="operation" value= "delete">
							<input type="hidden" name="id" value="<%= eve.getId() %>">
							<button class = "btn btn-danger">Eliminar</button>
						</form>
					</div>
				</div>
			</div>
		<% } %>		
	</div>

<!--Script para que funcione el js de bootstrap-->
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>