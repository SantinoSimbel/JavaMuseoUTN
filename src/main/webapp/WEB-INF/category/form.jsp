<%@page import="entities.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Form category</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		Category category = (Category) request.getAttribute("oneCategory");
		boolean editing = (boolean) request.getAttribute("editing");
		String name = category.getName() == null ? "" : category.getName();
	%>
</head>
<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class= "d-flex justify-content-between align-items-center mb-4 ">
			<h1>
				<%=editing ? "Editar categoria" : "Crear categoria" %>
			</h1>
		
			<a href="CategoryServlet?operation=list" 
			   class = "btn btn-secondary">
				Volver
			</a>
		</div>
		
		<!-- Formularios -->
		<div class = "card shadow-sm">	
			<div class = "card-body">
				<form action="CategoryServlet" method="POST">
					<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
					<%if(editing){ %>
						<input type="hidden" name="id" value="<%=category.getId()%>">
					<%}%>
					<div class= "mb-3">
						<label class="form-label">Nombre:</label>
						<input type="text" name="name" class="form-control" required value="<%=name%>">
					</div>
					
					<button type="submit" class="btn btn-success">Guardar</button>
				</form>		
			</div>
		</div>
	</div>
	
<!--Script para que funcione el js de bootstrap-->	
<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>