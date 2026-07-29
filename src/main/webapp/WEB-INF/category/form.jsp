<%@page import="entities.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Form category</title>

<%
	Category category = (Category) request.getAttribute("oneCategory");
	boolean editing = (boolean) request.getAttribute("editing");
	
	String name = category.getName() == null ? "" : category.getName();
%>
</head>
<body>

	<h1>
		<%=editing ? "Modificar categoria" : "Crear categoria" %>
	</h1>
	<a href="adminHome.jsp">Regresar al menu</a>
	
	<!-- Formulario -->
	<form action="CategoryServlet" method="POST">
		<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
		<p>
			<%if(editing){ %>
			<input type="hidden" name="id" value="<%=category.getId()%>">
			<%}%>
			<label>Nombre:</label> <input type="text" name="name" value="<%=name%>">
		</p>
		
		<button type="submit">Guardar</button>
	</form>
</body>
</html>