<%@ page import="entities.Exhibition" %>
<%@ page import="entities.Presentation" %>
<%@ page import="entities.Item" %>
<%@ page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Museum home</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>


    <%
        LinkedList<Exhibition> exhibitionList = (LinkedList<Exhibition>) request.getAttribute("someExhibitions");
        LinkedList<Item> itemList = (LinkedList<Item>) request.getAttribute("someItems");
        LinkedList<Presentation> presentationList = (LinkedList<Presentation>) request.getAttribute("somePresentations");
	 	String errorMessage = (String) request.getAttribute("errorMessage");
    %>
</head>
<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>

    <div class="container mt-4">
        <!-- Título -->
        <div class="mx-auto px-3 px-md-5" style="max-width: 1100px;">
            <div class="px-1">
                <h1 class="fs-2 fs-md-1 mb-2">¡Bienvenido!</h1>

                <p class="mt-2 mb-2 text-start lh-base" style="font-size: 0.95rem;">
                    Bienvenido al <strong>Museo UTN</strong> (Unidad de Teletransportación Neo-dimensional).
                    Gracias a nuestros últimos avances en tecnología de viajes interdimensionales, en la U.T.N.
                    logramos contactar a los dueños originales de los artículos más icónicos de múltiples universos
                    y convencerlos de prestarlos temporalmente para su exhibición pública.
                </p>

                <p class="mb-0 text-start lh-base" style="font-size: 0.95rem;">
                    Recorré nuestra colección completa de manera online en el museo virtual, enterate de las
                    próximas exhibiciones o comprá tus entradas para asistir a nuestras presentaciones. También te
                    invitamos a visitarnos en nuestra sede presencial, donde podrás ver cada objeto cara a cara.
                </p>
            </div>
        </div>
        
        <!-- Mensajes de error -->
		<%if(errorMessage != null){%>
			<div class="alert alert-danger" role="alert">
 				<%= errorMessage %>
			</div>
		<%}%>

        <!-- ==================== ITEMS ==================== -->
        <% if (itemList != null && !itemList.isEmpty()) { %>
           <div class="mx-auto px-4 px-md-5 mt-4 mb-2" style="max-width: 1100px;">
    			<div class="px-1 d-flex justify-content-between align-items-center flex-wrap gap-2">
        			<h3 class="mb-0">Algunos de nuestros artículos</h3>
        			<a href="${pageContext.request.contextPath}/ItemServlet?operation=userList" 
        				class="btn btn-outline-secondary btn-sm rounded-pill px-3 btn-see-all">
            			Ver todos <i class="bi bi-arrow-right ms-1"></i>
        			</a>
    			</div>
			</div> 

            <div class="position-relative px-4 px-md-5 mx-auto" style="max-width: 1100px;">
                <div class="overflow-hidden position-relative px-1">
                    <div id="itemsCarousel" class="carousel-track">
                        <% for (Item item : itemList) { %>
                            <div class="carousel-card d-flex">
                                <div class="card h-100 shadow-sm border rounded w-100 d-flex flex-column position-relative">

                                    <% if (item.getCategory() != null) { %>
                                        <div class="position-absolute top-0 end-0 m-3 z-2">
                                            <span class="badge bg-secondary"><%= item.getCategory().getName() %></span>
                                        </div>
                                    <% } %>

                                    <div class="card-body d-flex flex-column text-start p-3">
                                        <h5 class="fw-bold mb-3 text-dark pe-5"><%= item.getName() %></h5>

                                        <div class="text-center my-2">
                                            <img src="<%= item.getPicture() %>"
                                                 class="img-fluid rounded border p-1"
                                                 alt="<%= item.getName() %>"
                                                 style="max-height: 120px; object-fit: cover;">
                                        </div>

                                        
                                    </div>

                                    <div class="card-footer bg-white border-top-0 p-3 pt-0 mt-auto">
                                        <a href="ItemServlet?operation=detail&id=<%= item.getId() %>"
                                           class="btn btn-secondary w-100 text-white fw-semibold"
                                           style="background-color: #5c636a; border-color: #5c636a;">
                                            Ver más
                                        </a>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                </div>

                <% if (itemList.size() > 1) { %>
                    <button type="button" class="carousel-arrow carousel-arrow-left"
                            aria-label="Ver artículos anteriores"
                            onclick="moveCarousel('itemsCarousel', -1)">
                        <i class="bi bi-chevron-left"></i>
                    </button>
                    <button type="button" class="carousel-arrow carousel-arrow-right"
                            aria-label="Ver más artículos"
                            onclick="moveCarousel('itemsCarousel', 1)">
                        <i class="bi bi-chevron-right"></i>
                    </button>
                <% } %>
            </div>
        <% } %>
        
        
        
        
        
        
        
        
        
        <!-- ==================== PRESENTACIONES ==================== -->
        <% if (presentationList != null && !presentationList.isEmpty()) { %>
           <div class="mx-auto px-4 px-md-5 mt-4 mb-2" style="max-width: 1100px;">
    			<div class="px-1 d-flex justify-content-between align-presentations-center flex-wrap gap-2">
        			<h3 class="mb-0">Algunas de nuestras presentaciones</h3>
        			<a href="${pageContext.request.contextPath}/PresentationServlet?operation=userList" 
        				class="btn btn-outline-secondary btn-sm rounded-pill px-3 btn-see-all">
            			Ver todas <i class="bi bi-arrow-right ms-1"></i>
        			</a>
    			</div>
			</div> 

            <div class="position-relative px-4 px-md-5 mx-auto" style="max-width: 1100px;">
                <div class="overflow-hidden position-relative px-1">
                    <div id="presentationsCarousel" class="carousel-track">
                        <% for (Presentation pre : presentationList) { %>
                 			<div class="carousel-card d-flex">
    							<div class="card h-100 shadow-sm border rounded w-100 d-flex flex-column">
	
    							    <!-- Estado -->
    							    <div class="d-flex justify-content-end px-3 pt-3">
            							<% if (pre.getStatus().equals("Creado")) { %>
                							<span class="badge text-bg-success text-nowrap">No empezada</span>
            							<% } else if (pre.getStatus().equals("Empezado")) { %>
                							<span class="badge text-bg-warning text-nowrap">Empezada</span>
            							<% } else { %>
                							<span class="badge text-bg-secondary">Terminada</span>
            							<% } %>
        							</div>

        							<!-- Contenido principal -->
        							<div class="card-body d-flex flex-column text-start p-3">

            							<!-- Título -->
            							<h5 class="fw-bold mb-3 text-dark title-slot"> <%= pre.getTitle() %> </h5>

							            <!-- Artículos -->
            							<span class="d-block mb-2"><b>Algunos artículos:</b></span>

            							<div class="d-flex gap-2 flex-wrap mb-3 item-images">
                							<% for (int i = 0; i < Math.min(4, pre.getItems().size()); i++) { %>
                    							<img src="<%= pre.getItems().get(i).getPicture() %>" alt="Imagen de <%= pre.getItems().get(i).getName() %>" 
                    								class="border rounded p-1" style="width: 69px; height: 70px; object-fit: contain;">
                    						<% } %>
            							</div>

            							<!-- Descripción -->
            							<p class="text-muted small mb-3"> <%= pre.getDescription() %> </p>

							        </div>

        							<!-- Información y botón -->
        							<div class="card-footer bg-white border-top-0 p-3 pt-0 mt-auto">

            							<div class="small text-muted mb-3">
                							<i class="bi bi-calendar-event"></i> 
                							<b>El día </b> <%= pre.getDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %> <br>
											<i class="bi bi-clock"></i>
                							<b>Horario:</b> <%= pre.getStartTime() %> a <%= pre.getEndTime() %> hs <br>
                							<i class="bi bi-cash-stack"></i>
                							<b>Precio: </b> $<%= pre.getPrice().intValue() %>
            							</div>

            							<a href="PresentationServlet?operation=detail&id=<%= pre.getId() %>" class="btn btn-secondary w-100 text-white fw-semibold" style="background-color: #5c636a; border-color: #5c636a;">
	                						Ver más
    	        						</a>
		
    							    </div>
	
							    </div>
							</div>
                        <% } %>
                    </div>
                </div>

                <% if (presentationList.size() > 1) { %>
                    <button type="button" class="carousel-arrow carousel-arrow-left"
                            aria-label="Ver presentaciones anteriores"
                            onclick="moveCarousel('presentationsCarousel', -1)">
                        <i class="bi bi-chevron-left"></i>
                    </button>
                    <button type="button" class="carousel-arrow carousel-arrow-right"
                            aria-label="Ver más presentaciones"
                            onclick="moveCarousel('presentationsCarousel', 1)">
                        <i class="bi bi-chevron-right"></i>
                    </button>
                <% } %>
            </div>
        <% } %>
        
        
        
        
        
        
        

        <!-- ==================== EXHIBICIONES ==================== -->
        <% if (exhibitionList != null && !exhibitionList.isEmpty()) { %>
           <div class="mx-auto px-4 px-md-5 mt-4 mb-2" style="max-width: 1100px;">
    			<div class="px-1 d-flex justify-content-between align-exhibitions-center flex-wrap gap-2">
        			<h3 class="mb-0">Algunas de nuestras exhibiciones</h3>
        			<a href="${pageContext.request.contextPath}/ExhibitionServlet?operation=userList" 
        				class="btn btn-outline-secondary btn-sm rounded-pill px-3 btn-see-all">
            			Ver todas <i class="bi bi-arrow-right ms-1"></i>
        			</a>
    			</div>
			</div> 

            <div class="position-relative px-4 px-md-5 mx-auto" style="max-width: 1100px;">
                <div class="overflow-hidden position-relative px-1">
                    <div id="exhibitionsCarousel" class="carousel-track">
                        <% for (Exhibition ex : exhibitionList) { %>
                 			<div class="carousel-card d-flex">
    							<div class="card h-100 shadow-sm border rounded w-100 d-flex flex-column">
	
    							    <!-- Estado -->
    							    <div class="d-flex justify-content-end px-3 pt-3">
            							<% if (ex.getStatus().equals("Creado")) { %>
                							<span class="badge text-bg-success text-nowrap">No empezada</span>
            							<% } else if (ex.getStatus().equals("Empezado")) { %>
                							<span class="badge text-bg-warning text-nowrap">Empezada</span>
            							<% } else { %>
                							<span class="badge text-bg-secondary">Terminada</span>
            							<% } %>
        							</div>

        							<!-- Contenido principal -->
        							<div class="card-body d-flex flex-column text-start p-3">

            							<!-- Título -->
            							<h5 class="fw-bold mb-3 text-dark title-slot"> <%= ex.getTitle() %> </h5>

							            <!-- Artículos -->
            							<span class="d-block mb-2"><b>Algunos artículos:</b></span>

            							<div class="d-flex gap-2 flex-wrap mb-3 item-images">
                							<% for (int i = 0; i < Math.min(4, ex.getItems().size()); i++) { %>
                    							<img src="<%= ex.getItems().get(i).getPicture() %>" alt="Imagen de <%= ex.getItems().get(i).getName() %>" 
                    								class="border rounded p-1" style="width: 69px; height: 70px; object-fit: contain;">
                    						<% } %>
            							</div>

            							<!-- Descripción -->
            							<p class="text-muted small mb-3"> <%= ex.getDescription() %> </p>

							        </div>

                                    <div class="card-footer bg-white border-top-0 p-3 pt-0 mt-auto">
                                        <div class="card-body small text-muted">
                                        	<i class="bi bi-calendar-event"></i>
                                        	<b>Desde:</b> <%= ex.getStartDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %><br>
                                    		<i class="bi bi-calendar-event"></i>
                                    		<b>Hasta:</b> <%= ex.getEndDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
                                    	</div>
                                        
                                        <a href="ExhibitionServlet?operation=detail&id=<%= ex.getId() %>"
                                           class="btn btn-secondary w-100 text-white fw-semibold"
                                           style="background-color: #5c636a; border-color: #5c636a;">
                                            Ver más
                                        </a>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                </div>

                <% if (exhibitionList.size() > 1) { %>
                    <button type="button" class="carousel-arrow carousel-arrow-left"
                            aria-label="Ver exhibiciones anteriores"
                            onclick="moveCarousel('exhibitionsCarousel', -1)">
                        <i class="bi bi-chevron-left"></i>
                    </button>
                    <button type="button" class="carousel-arrow carousel-arrow-right"
                            aria-label="Ver más exhibiciones"
                            onclick="moveCarousel('exhibitionsCarousel', 1)">
                        <i class="bi bi-chevron-right"></i>
                    </button>
                <% } %>
            </div>
        <% } %>
    </div>

    <!-- Scripts de Bootstrap -->
    <%@ include file="/WEB-INF/common/scripts.jsp" %>
    <script src="${pageContext.request.contextPath}/js/carousel.js"></script>
</body>
</html>
