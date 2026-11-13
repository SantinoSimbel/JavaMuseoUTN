<%@page import="entities.Category"%>
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
		LinkedList<Category> categoryList = (LinkedList<Category>) request.getAttribute("allCategories");
		
		String selectedCategory = (String) request.getAttribute("selectedCategory");
	%>
</head>
<body>
	<!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>
    
    <!-- Titulo y botones -->
    <div class="container mt-4 mb-3">
        <div class="d-flex justify-content-between align-items-center ">
            <h1>Museo virtual</h1>
        </div>
        
        <p class="text-secondary">
        	Bienvenido a nuestro museo virtual, donde podrás explorar de manera online toda la colección
        	de articulos que actualmente forma parte de nuestro museo presencial.
         
        </p>
        
        <!-- Filtros -->
        <div class="d-flex flex-column flex-md-row align-items-md-center align-items-start gap-4 mb-3">
            <form action="ItemServlet" method="GET" class="d-flex flex-column flex-sm-row align-items-sm-center gap-2">
            
                <input type="hidden" name="operation" value="userList">
                <label for="category" class="form-label mb-0 text-nowrap">
                    Filtrar por categoria:
                </label>
                
                <!-- categoryId es el nombre del parametro que paso al servlet, id es el  -->
                <div class="d-flex align-items-center gap-2 w-100">
	                <select name="categoryId" id="category" class="form-select">
	                    <option value="" <%= (selectedCategory == null || selectedCategory.isEmpty()) ?
	                    		"selected" : "" %>>Todas </option>
	                    		
	                    <% for (Category cat: categoryList){ %>
	                    	<!-- value es el valor del parametro que se envia ej: categoryId=3 -->
	                    	<option 
	                    		value = "<%= cat.getId() %>" 
	                    		<%= String.valueOf(cat.getId()).equals(selectedCategory) ?
	                    		"selected" : "" %>> <%=cat.getName()%>
	                    	</option>
	                    <%} %>
	                </select>
	                
	                <button type="submit" class="btn btn-primary">
	                    Filtrar
	                </button>
                </div>
            </form>
        </div>
   

        <% if(itemsList.isEmpty()){ %>
            <div class="alert alert-danger" role="alert">
                No hay items disponibles.
            </div>
        <% } %>
        
		<!-- Listado de items -->
		<div class="row g-3">
		
		    <% for (Item ite : itemsList) { %>
		        <div class="col-6 col-md-4 col-lg-3">
		            <div class="card h-100 shadow-sm">
		                <a href="ItemServlet?operation=detail&id=<%=ite.getId()%>"
		                   class="text-decoration-none text-white">
		                   
		                    <img src="<%= ite.getPicture() %>"
		                         class="card-img item-card-img"
		                         alt="Imagen de <%= ite.getName() %>">
		                         
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
