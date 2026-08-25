<%@page import="entities.Item"%>
<%@page import="entities.Event"%>
<%@page import="entities.Presentation"%>
<%@page import="entities.Exhibition"%>
<%@page import="java.util.LinkedList" %>
<%@page import="java.time.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Form Event</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	
	<%
		Event event = (Event) request.getAttribute("oneEvent");
		boolean editing = (boolean) request.getAttribute("editing");
		String currentType = (String) request.getAttribute("currentType");
	
		String title = event.getTitle() == null ? "" : event.getTitle();
		String description = event.getDescription() == null ? "" : event.getDescription();
		LocalTime startTime = event.getStartTime() == null ? LocalTime.MIDNIGHT : event.getStartTime();
		LocalTime endTime = event.getEndTime() == null ? LocalTime.MIDNIGHT : event.getEndTime();
	 	LinkedList<Item> items = (LinkedList<Item>) request.getAttribute("allItems");
	%>
</head>
<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class= "d-flex justify-content-between align-events-center mb-3 ">
			<h1>
				<%=editing ? "Editar articulo" : "Crear articulo" %>
			</h1>
		
			<a href="EventServlet?operation=list" 
			   class = "btn btn-secondary">
				Volver
			</a>
		</div>
				
		<p class="text-muted mb-2"><%=editing ? 
			"Modifique los campos que quiera editar:" : "Complete los campos:" %>
		</p>
		
		<!-- Formularios -->
		<div class = "card shadow-sm">	
			<div class = "card-body">
				<form action="EventServlet" method="POST">
				
				
					<!-- ======================================== -->
    				<!-- DATOS OCULTOS                            -->
    				<!-- ======================================== -->
				
				
				
				
				
					<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
					<%if(editing){ %>
						<input type="hidden" name="id" value="<%=event.getId()%>">
					<%}%>
					
					
					
					
					<!-- ======================================== -->
					<!-- PESTAÑAS                                 -->
					<!-- ======================================== -->
					
					<ul class="nav nav-tabs mb-4" id="eventTabs">

        				<li class="nav-item">
            				<a class="nav-link <%= "exhibition".equals(currentType) ? "active" : "" %>"
           						href="EventServlet?operation=new&eveType=exhibition"> Exhibición
        					</a>
        				</li>

				        <li class="nav-item">
            				<a class="nav-link <%= "presentation".equals(currentType) ? "active" : "" %>"
            					href="EventServlet?operation=new&eveType=presentation">	Presentación
            				</a>
        				</li>

    				</ul>


    				<!-- ======================================== -->
    				<!-- DATOS COMUNES DEL EVENTO                 -->
    				<!-- ======================================== -->
					
					<div class= "mb-3">
						<b><label class="form-label">Titulo:</label></b>
						<input type="text" name="title" class="form-control" required value="<%=title%>">
						<b><label class="form-label">Descripción:</label></b>
						<input type="text" name="description" class="form-control" required value="<%=description%>">
						<b><label class="form-label">Hora de inicio:</label></b>
						<input type="text" name="startTime" class="form-control" required value="<%=startTime%>">
						<b><label class="form-label">Hora de fin:</label></b>
						<input type="text" name="endTime" class="form-control" required value="<%=endTime%>">
						<b><label class="form-label">Seleccione el articulo:</label></b>
						<select name="item_id" class="form-select" required>
							<% for (Item item : items) { %>
								<option value="<%=item.getId()%>"
    								<%= event.getItem() != null &&
    									event.getItem().getId() == item.getId()
								        ? "selected"
        								: "" %>>

    								<%=item.getName()%>
								</option>
							<% } %>
						</select>
					</div>
					
					
					<!-- ======================================== -->
    				<!-- CONTENEDOR DE CAMPOS ESPECÍFICOS        -->
    				<!-- ======================================== -->

    					<div class="border rounded p-3 mb-4">
				
        					<h5 class="mb-3" id="specificDataTitle">
            					<%= "presentation".equals(currentType) ? "Datos de la presentación" : "Datos de la exhibición" %>
        					</h5>


        					<!-- EXHIBICIÓN ========================= -->
        					<div id="exhibitionFields" style="<%= "exhibition".equals(currentType) ? "" : "display:none;" %>">

            					<div class="row">
                					<div class="col-md-6 mb-3">
					                    <label class="form-label fw-bold"> Fecha de inicio: </label>	
                    					<input type="date" name="startDay" class="form-control">
                					</div>
                					<div class="col-md-6 mb-3">
                    					<label class="form-label fw-bold"> Fecha de fin: </label>
                    					<input type="date" name="endDay" class="form-control">
									</div>
								</div>
							</div>
		

   					     
      					 <!-- PRESENTACIÓN ========================= -->
        					<div id="presentationFields"
             					style="<%= "presentation".equals(currentType) ? "" : "display:none;" %>">
					            <div class="row">
					                <div class="col-md-6 mb-3">
                    					<label class="form-label fw-bold"> Día: </label>
					                    <input type="date" name="day" class="form-control">
					                </div>
        					        <div class="col-md-6 mb-3">
					                    <label class="form-label fw-bold"> Capacidad: </label>	
                    					<input type="number" name="capacity" class="form-control" min="1">
					                </div>
            					</div>
        					</div>	
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