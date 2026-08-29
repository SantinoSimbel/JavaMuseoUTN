<%@page import="entities.Presentation"%>
<%@ page import="entities.Item" %>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>List presentation</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		LinkedList<Presentation> presentationList = (LinkedList<Presentation>) request.getAttribute("allPresentations");
	%>
</head>

<body>	
	
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4 mb-5">
		<div class="d-flex justify-content-between align-items-center mb-2">

			<h1>Presentaciones</h1>

			<a href="adminHome.jsp"
			   class = "btn btn-secondary">
			Regresar al menu
			</a>
		</div>
		
		<a href="PresentationServlet?operation=new" 
		   class="btn btn-success mb-3">
			Nueva Presentación 
		</a>
				
		<p class="text-muted mb-2">Seleccione una presentación para editarla:</p>
		
		<!-- Listado -->
		<% for (Presentation pre : presentationList){ %>
			<div class= "card mb-3 shadow-sm">
				<a href ="PresentationServlet?operation=edit&id=<%=pre.getId()%>"
				class="text-decoration-none text-dark">
					<div class= "card-body">
						<h5 class = "card-title">
							<%= pre.getTitle()%>
						</h5>
						<div class="d-flex flex-column flex-md-row align-items-center">
							
							<div class="ms-0 ms-md-4 mt-3 mt-md-0">
								<div class="ms-0 ms-md-4 mt-3 mt-md-0">
									<br> <b>Artículos: </b> 
										<ul>
    										<% for (Item item : pre.getItems()) { %>
        										<li><%= item.getName() %></li>
    										<% } %>
										</ul>
								</div>
								<br> <b>Capacidad: </b><%=pre.getCapacity() %>
								<br> <%=pre.getDescription() %>
								<br> <b>El día</b> <%= pre.getDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
								<br> <b>Desde las</b> <%=pre.getStartTime() %> <b>hasta</b> <%=pre.getEndTime() %>
								
						    </div>
						</div>
					</div>
				</a>
				<div class= "card-footer">
					<div class= "d-flex justify-content-end">
						<form action="PresentationServlet" method="POST" onsubmit="return confirm('¿Eliminar Precentación?');">
							<input type="hidden" name="operation" value= "delete">
							<input type="hidden" name="id" value="<%= pre.getId() %>">
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