<%@page import="entities.Category"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List categories</title>

<%
	//codigo java para que la pagina funcione correctamente ponerlo en head (segun profe)
	//no los que tengan comportamientos o animaciones 
	LinkedList<Category> categoriesList = (LinkedList<Category>) request.getAttribute("allCategories");
%>
</head>

<body>
	<h1>Tabla categorias</h1>
	<a href="adminHome.jsp">Regresar al menu</a>
	<a href ="CategoryServlet?operation=new">Crear nueva categoria</a>

	<table border="1">
		<thead>
			<tr>
				<th>Id</th>
				<th>Name</th>
			</tr>
		</thead>
		<tbody>
		<% for (Category cat : categoriesList){ %>
			<tr>
				<td><%= cat.getId()%></td>
				<td>
					<a href ="CategoryServlet?operation=edit&id=<%=cat.getId()%>">
						<%= cat.getName()%>
					</a>
					<button>E</button>
					<button>B</button>
				</td>
			</tr>
			<%} %>
		</tbody>
	</table>

</body>
</html>