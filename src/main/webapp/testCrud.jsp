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
		<!-- action: cuando se envie el form hace la peticion a tal servlet, con tal metodo -->
		<form action="CategoryServlet" method="GET">
			<input type="hidden" name="operation" value= "list">
			<button type= "submit">Mostrar Categorias</button>
		</form>
		<!-- tambien al solo hacer un get podria ponerse:
				<a href="CategoryServlet?operation=list">Mostrar categorías</a>
		(y darle estilo css, los form convienen cuando uno esta enviando cosas para buscar,
		si no directamente usar <a>)
		-->
		<a href="CategoryServlet?operation=showAddForm">guardar categorías</a>
		
	</div>
</body>
</html>