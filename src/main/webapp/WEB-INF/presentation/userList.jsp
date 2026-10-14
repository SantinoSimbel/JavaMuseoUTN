<%@page import="java.util.LinkedList"%>
<%@page import="entities.Presentation"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User list presentation</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>
    <% 
        LinkedList<Presentation> presentationList = (LinkedList<Presentation>) request.getAttribute("allPresentations");
        String currentStatus = request.getParameter("status");
    %>
</head>
<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>
    
    <div>
    	<%for (Presentation pre: presentationList){ %>
    		<div class= "card">
    			<b><%= pre.getTitle() %></b>
    			<%= pre.getDescription() %>
    		</div>
    		
    		<div class="card-footer bg-transparent border-top-0 pt-0">
            	<form action="PresentationServlet" method="GET" class="w-100">
            		<input type="hidden" name="operation" value="detail">
            		<input type="hidden" name="id" value="<%= pre.getId() %>">
            		<button type="submit" class="btn btn-secondary w-100"> 
            			Ver más 
           			</button>
            </form>
            </div>
    	<% } %>
    </div>
</body>
</html>