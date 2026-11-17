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

    <style>
        /* Carrusel horizontal compartido por las tres secciones */
        .carousel-track {
            display: flex;
            flex-direction: row;
            gap: 1rem;
            padding: 1rem 0;
            overflow-x: auto;
            scroll-behavior: smooth;
            scroll-snap-type: x mandatory;
            scrollbar-width: none;
            -ms-overflow-style: none;
        }

        .carousel-track::-webkit-scrollbar {
            display: none;
        }

        /* Una tarjeta en móviles */
        .carousel-track > .carousel-card {
            flex: 0 0 100%;
            min-width: 0;
            scroll-snap-align: start;
        }

        /* Dos tarjetas en tablets */
        @media (min-width: 768px) {
            .carousel-track > .carousel-card {
                flex: 0 0 calc((100% - 1rem) / 2);
            }
        }

        /* Tres tarjetas en escritorio */
        @media (min-width: 992px) {
            .carousel-track > .carousel-card {
                flex: 0 0 calc((100% - 2rem) / 3);
            }
        }

        .carousel-card .card {
            width: 100%;
            min-width: 0;
        }

        /* Flechas ubicadas respecto del contenedor de SU carrusel */
        .carousel-arrow {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            z-index: 5;
            width: 40px;
            height: 40px;
            padding: 0;
            background-color: white;
            border: 1px solid #dee2e6;
            border-radius: 50%;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .carousel-arrow-left {
            left: 0;
        }

        .carousel-arrow-right {
            right: 0;
        }
        
        .title-slot {
    		min-height: 4.5rem;
		}
    </style>

    <script>
        function moveCarousel(carouselId, direction) {
            const carousel = document.getElementById(carouselId);
            if (!carousel) return;

            const firstCard = carousel.querySelector('.carousel-card');
            if (!firstCard) return;

            const cardWidth = firstCard.getBoundingClientRect().width;
            const gap = parseFloat(getComputedStyle(carousel).columnGap) || 0;

            carousel.scrollBy({
                left: direction * (cardWidth + gap),
                behavior: 'smooth'
            });
        }
    </script>

    <%
        LinkedList<Exhibition> exhibitionList =
            (LinkedList<Exhibition>) request.getAttribute("someExhibitions");
        LinkedList<Item> itemList =
            (LinkedList<Item>) request.getAttribute("someItems");
        LinkedList<Presentation> presentationList =
            (LinkedList<Presentation>) request.getAttribute("somePresentations");
    %>
</head>
<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>

    <div class="container mt-4">
        <!-- Título -->
        <h1>¡Bienvenido!</h1>
        <br>
        <p style="text-align: justify;">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </p>

        <!-- ==================== ITEMS ==================== -->
        <% if (itemList != null && !itemList.isEmpty()) { %>
            <h3 class="mt-4 mb-3 text-center">Algunos de nuestros artículos</h3>

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

                                        <p class="text-muted small mb-3"><%= item.getDescription() %></p>
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
            <h3 class="mt-4 mb-3 text-center">Algunas de nuestras presentaciones</h3>

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
            <h3 class="mt-4 mb-3 text-center">Algunas de nuestras exhibiciones</h3>

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
                                        	<b>Desde:</b> <%= ex.getStartDay().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
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
</body>
</html>
