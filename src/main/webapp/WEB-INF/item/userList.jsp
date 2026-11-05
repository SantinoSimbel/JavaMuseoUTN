<%@page import="entities.Item"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>UserList items</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	
	<%
		LinkedList<Item> itemsList = (LinkedList<Item>) request.getAttribute("allItems");
	%>
</head>
<body>
	<!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>
    
    <!-- Titulo y botones -->
    <div class="container mt-4 mb-3">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1>Museo virtual</h1>
        </div>
        
        <p>Bienvenido a nuestro museo virtual, aqui podras ver toda nuestra coleccion de articulos </p>
        
		<!-- Listado de items -->
		<div class="row g-3">
		
		    <% for (Item ite : itemsList) { %>
		        <div class="col-6 col-md-4 col-lg-3">
		            <div class="card h-100 shadow-sm">
		                <a href="ItemServlet?operation=detail&id=<%=ite.getId()%>"
		                   class="text-decoration-none text-white">
		                   
		                    <img src="<%= ite.getPicture() %>"
		                         class="card-img"
		                         alt="Imagen de <%= ite.getName() %>"
		                         style="width: 100%; height: 220px; object-fit: contain;">
		                         
		                     <div class="card-img-overlay d-flex flex-column  justify-content-end pb-1"
		                     style="background: linear-gradient(to top, rgba(0,0,0,0.90),
		                     rgba(0,0,0,0.2), transparent);">
		                     
			                     <h5 class="card-title">
			                     	<%= ite.getName() %>
			                     </h5>
			                 </div>
		                </a>
		            </div>
		        </div>
		    <% } %>
		</div>	     
    </div>
	<!-- Scripts -->
	<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>
