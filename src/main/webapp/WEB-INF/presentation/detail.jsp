<%@page import="entities.Presentation"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>One presentation</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>
    <% Presentation pre = (Presentation) request.getAttribute("onePresentation"); %>
</head>

<body>
    <!-- Navbar -->
    <%@ include file="/WEB-INF/common/navbar.jsp" %>

	<div>
		<h1><%=pre.getTitle()%></h1>
		<%=pre.getDescription()%>
		
		<form action="PurchaseServlet" method="POST">
			<input type="hidden" name="operation" value= "buy">
			<input type="hidden" name="id" value="<%= pre.getId() %>">
			<button class = "btn btn-primary">Comprar</button>
		</form>
	</div>
</body>
</html>