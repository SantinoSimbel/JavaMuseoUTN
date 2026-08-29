<%@page import="entities.Exhibition"%>
<%@ page import="entities.Item" %>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>List exhibition</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		LinkedList<Exhibition> exhibitionList = (LinkedList<Exhibition>) request.getAttribute("allExhibitions");
	%>
</head>

<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4 mb-5">
		<div class="d-flex justify-content-between align-items-center mb-2">

			<h1>Exhibiciones</h1>

			<a href="adminHome.jsp"
			   class = "btn btn-secondary">
			Regresar al menu
			</a>
		</div>
		
		<a href="ExhibitionServlet?operation=new" 
		   class="btn btn-success mb-3">
			Nueva Exhibición 
		</a>
				
		<p class="text-muted mb-2">Seleccione una exhibición para editarla:</p>
		
		<!-- Listado -->
		<% for (Exhibition ex : exhibitionList){ %>
			<div class= "card mb-3 shadow-sm">
				<a href ="ExhibitionServlet?operation=edit&id=<%=ex.getId()%>"
				class="text-decoration-none text-dark">
					<div class= "card-body">
						<h5 class = "card-title">
							<%= ex.getTitle()%>
						</h5>
						<div class="d-flex flex-column flex-md-row align-items-center">
							
							<div class="ms-0 ms-md-4 mt-3 mt-md-0">
								<br> <b>Artículos: </b> 
								
									<ul>
    									<% for (Item item : ex.getItems()) { %>
        									<li><%= item.getName() %></li>
    									<% } %>
									</ul>
							
								<br> <%=ex.getDescription() %>
							<br> <b>Desde el día</b> <%= ex.getStartDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
							<b>hasta el</b> <%= ex.getEndDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>


								<br> <b>Desde las</b> <%=ex.getStartTime() %> <b>hasta</b> <%=ex.getEndTime() %>
						    </div>
						</div>
					</div>
				</a>
				<div class= "card-footer">
					<div class= "d-flex justify-content-end">
						<form action="ExhibitionServlet" method="POST" onsubmit="return confirm('¿Eliminar Exhibición?');">
							<input type="hidden" name="operation" value= "delete">
							<input type="hidden" name="id" value="<%= ex.getId() %>">
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