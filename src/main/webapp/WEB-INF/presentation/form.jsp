<%@page import="entities.Item"%>
<%@page import="entities.Presentation"%>
<%@page import="java.util.LinkedList" %>
<%@page import="java.time.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Form presentation</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	
	<%
		Presentation presentation = (Presentation) request.getAttribute("onePresentation");
		boolean editing = (boolean) request.getAttribute("editing");
	
		String title = presentation.getTitle() == null ? "" : presentation.getTitle();
		String description = presentation.getDescription() == null ? "" : presentation.getDescription();
		LocalTime startTime = presentation.getStartTime() == null ? LocalTime.MIDNIGHT : presentation.getStartTime();
		LocalTime endTime = presentation.getEndTime() == null ? LocalTime.MIDNIGHT : presentation.getEndTime();
		LocalDate day = presentation.getDay() == null ? LocalDate.now() : presentation.getDay();
		int capacity = presentation.getCapacity() == 0 ? 0 : presentation.getCapacity();
	 	LinkedList<Item> items = (LinkedList<Item>) request.getAttribute("allItems");
	%>
</head>
<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4 mb-5">
		<div class= "d-flex justify-content-between align-items-center mb-3 ">
			<h1>
				<%=editing ? "Editar Presentación" : "Crear Presentación" %>
			</h1>
		
			<a href="PresentationServlet?operation=list" 
			   class = "btn btn-secondary">
				Volver
			</a>
		</div>
				
		<p class="text-muted mb-2"><%=editing ? 
			"Modifique los campos que quiera editar:" : "Complete los campos:" %>
		</p>
		
		
		<% if (request.getAttribute("error") != null) { %>
			<div class="alert alert-danger">
				<%= request.getAttribute("error") %>
			</div>
		<% } %>
		
		
		
		<!-- Formularios -->
		<div class = "card shadow-sm">	
			<div class = "card-body">
				<form action="PresentationServlet" method="POST">
					<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
					<%if(editing){ %>
						<input type="hidden" name="id" value="<%=presentation.getId()%>">
					<%}%>
					<div class= "mb-3">
						<b><label class="form-label">Titulo:</label></b>
						<input type="text" name="title" class="form-control" required value="<%=title%>">
						<b><label class="form-label">Descripción:</label></b>
						<input type="text" name="description" class="form-control" required value="<%=description%>">
						<b><label class="form-label">Hora de inicio:</label></b>
    					<input type="time" name="startTime" class="form-control" required value="<%=startTime%>">
						<b><label class="form-label">Hora de fin:</label></b>
						<input type="time" name="endTime" class="form-control" required value="<%=endTime%>">
						<b><label class="form-label">Día:</label></b>
    					<input type="date" name="day" class="form-control" required value="<%=day%>">
						<b><label class="form-label">Capacidad:</label></b>
						<input type="number" name="capacity" class="form-control" required value="<%=capacity%>">
						<b><label class="form-label">Seleccione los artículos:</label></b>

						<div class="border rounded p-2" style="max-height: 250px; overflow-y: auto;" id="itemList">
							<% for (Item item : items) { %>
    							<%
    								boolean selected = false;
        							if (editing && presentation.getItems() != null) {
            							for (Item selectedItem : presentation.getItems()) {
                							if (selectedItem.getId() == item.getId()) {
                    							selected = true;
                    							break;
                							}
            							}
        							}
    							%>

    							<div class="form-check item-option">
        							<input class="form-check-input" type="checkbox" name="item_ids" 
        								value="<%=item.getId()%>" id="item_<%=item.getId()%>" <%=selected ? "checked" : ""%> >

	    							<label class="form-check-label" for="item_<%=item.getId()%>">
    	        						<%=item.getName()%>
        							</label>
    							</div>

							<% } %>
						</div>

						<input type="text" id="itemSearch" class="form-control mt-2" placeholder="Buscar artículo..." >

						<script>
    						const search = document.getElementById("itemSearch");
    						const itemOptions = document.querySelectorAll(".item-option");
    						search.addEventListener("input", function () {
        					const text = search.value.toLowerCase();
    	    					for (let option of itemOptions) {
	            					const label = option.querySelector("label");
            						const name = label.textContent.toLowerCase();
            						option.style.display = name.includes(text) ? "" : "none";
        						}
    						});
						</script>
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