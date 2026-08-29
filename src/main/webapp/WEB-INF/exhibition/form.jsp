<%@page import="entities.Item"%>
<%@page import="entities.Exhibition"%>
<%@page import="java.util.LinkedList" %>
<%@page import="java.time.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Form exhibition</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
	
	<%
		Exhibition exhibition = (Exhibition) request.getAttribute("oneExhibition");
		boolean editing = (boolean) request.getAttribute("editing");
	
		String title = exhibition.getTitle() == null ? "" : exhibition.getTitle();
		String description = exhibition.getDescription() == null ? "" : exhibition.getDescription();
		LocalTime startTime = exhibition.getStartTime() == null ? LocalTime.MIDNIGHT : exhibition.getStartTime();
		LocalTime endTime = exhibition.getEndTime() == null ? LocalTime.MIDNIGHT : exhibition.getEndTime();
		LocalDate startDay = exhibition.getStartDay() == null ? LocalDate.now() : exhibition.getStartDay();
		LocalDate endDay = exhibition.getEndDay() == null ? LocalDate.now() : exhibition.getEndDay();
	 	LinkedList<Item> items = (LinkedList<Item>) request.getAttribute("allItems");
	%>
</head>
<body>
	<!-- Navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp" %>
	
	<!-- Titulo y botones -->
	<div class= "container mt-4">
		<div class= "d-flex justify-content-between align-items-center mb-3 ">
			<h1>
				<%=editing ? "Editar Exhibixión" : "Crear Exhibixión" %>
			</h1>
		
			<a href="ExhibitionServlet?operation=list" 
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
				<form action="ExhibitionServlet" method="POST">
					<input type= "hidden" name="operation" value = "<%=editing ? "update" : "add"%>">
					<%if(editing){ %>
						<input type="hidden" name="id" value="<%=exhibition.getId()%>">
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
						<b><label class="form-label">Día de inicio:</label></b>
    					<input type="date" name="startDay" class="form-control" required value="<%=startDay%>">
						<b><label class="form-label">Día de fin:</label></b>
						<input type="date" name="endDay" class="form-control" required value="<%=endDay%>">
						<b><label class="form-label">Seleccione los artículos:</label></b>

						<div class="border rounded p-2" style="max-height: 250px; overflow-y: auto;" id="itemList">
							<% for (Item item : items) { %>
    							<%
    								boolean selected = false;
        							if (editing && exhibition.getItems() != null) {
            							for (Item selectedItem : exhibition.getItems()) {
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