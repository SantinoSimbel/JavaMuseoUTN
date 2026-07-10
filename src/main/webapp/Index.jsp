<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>hola mundito UTN</h1>
	
	<p>CRUD categorias:</p>
	
	<div>
		<form action="CategoryServlet">
			<input type="hidden" name="action" value= "list">
			<button type= "submit">Mostrar Categorias</button>
		</form>
		
	</div>
</body>
</html>