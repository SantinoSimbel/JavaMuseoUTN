<%@page import="entities.Item"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Detail item</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	<%
		Item item = (Item) request.getAttribute("oneItem");
		String from = (String) request.getAttribute("from");
		String eventId = (String) request.getAttribute("eventId");
	%>
</head>
<body>
	<!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>
    
    <div class="container mt-4 mb-3">
    	<div class="mx-auto" style="max-width: 850px;">
    		<!-- Boton -->
    		<%
    			//Para que el boton vuelva a la pagina de donde vino
    			String backUrl = request.getContextPath() + "/";
    		
    			if ("userList".equals(from)){
    				backUrl = "ItemServlet?operation=userList";
    			} else if ("presentation".equals(from) && eventId != null){
    				backUrl = "PresentationServlet?operation=detail&id=" + eventId;
    			} else if("exhibition".equals(from) && eventId != null) {
    				backUrl = "ExhibitionServlet?operation=detail&id=" + eventId;
    			}
    		%>
    		<div class="d-flex justify-content-end mb-3">
    			<a href="<%=backUrl%>" class = "btn btn-secondary">
					Regresar
				</a>
    		</div>

			<div class="card shadow-sm">
	
		        <!-- Título -->
		        <div class="card-header bg-white text-center py-3">
		            <h1 class="mb-0"><%= item.getName() %></h1>
		        </div>
		
		        <div class="card-body">
		            <!-- Imagen -->
		            <div class="text-center mb-4">
		                <img src="<%= item.getPicture() %>"
		                     alt="Imagen de <%= item.getName() %>"
		                     class="img-fluid rounded"
		                     style="max-height: 400px; object-fit: contain;">
		            </div>
		
		            <!-- Categoría -->
		            <div class="text-center mb-3">
		                <span class="badge bg-primary fs-6">
		                    <%= item.getCategory().getName() %>
		                </span>
		            </div>
		
		            <!-- Descripción -->
		            <div class="text-center">
		                <p class="text-muted">
		                    <%= item.getDescription() %>
		                </p>
		            </div>
        		</div>
    		</div>
    	</div>
    </div>
	<!-- Scripts -->
	<%@ include file="/WEB-INF/common/scripts.jsp" %>
</body>
</html>
