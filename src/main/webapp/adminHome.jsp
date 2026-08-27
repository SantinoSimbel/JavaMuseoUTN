<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Admin Home</title>
	<%@ include file="/WEB-INF/common/head.jsp" %>
</head>
<body>

	<!-- navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp"%>
	
	<!-- Titulo -->
	<div class="container mt-4">
		<h1 class="mb-2 text-center">Admin Home</h1>
		<p class="text-muted mb-4 text-center">Seleccione qué desea administrar:</p>
		
		<!-- Admin menu -->
		<div class="row g-3">
		
			<!-- Articulos -->
			<div class="col-12 col-md-6 col-lg-4">
				<a href="ItemServlet?operation=list" class="card h-100 text-decoration-none shadow-sm">
					<div class="card-body">
						<h5 class="card-title">Artículos</h5>
						<p class="card-text">Administrar los artículos del museo.</p>
					</div>
				</a>
			</div>
			
			<!-- Exhibiciones -->
			<div class="col-12 col-md-6 col-lg-4">
				<a href=""
					class="card h-100 text-decoration-none shadow-sm">

					<div class="card-body">
						<h5 class="card-title">Exhibiciones</h5>
						<p class="card-text">Administrar las exhibiciones.</p>
					</div>
				</a>
			</div>
			
			<!-- Presentaciones -->
			<div class="col-12 col-md-6 col-lg-4">
				<a href=""
					class="card h-100 text-decoration-none shadow-sm">

					<div class="card-body">
						<h5 class="card-title">Presentaciones</h5>
						<p class="card-text">Administrar las presentaciones.</p>
					</div>
				</a>
			</div>
			
			<!-- Categorías -->
			<div class="col-12 col-md-6 col-lg-4">
				<a href="CategoryServlet?operation=list"
					class="card h-100 text-decoration-none shadow-sm">
					<div class="card-body">
						<h5 class="card-title">Categorías</h5>
						<p class="card-text">Administrar las categorías.</p>
					</div>
				</a>
			</div>
			
			<!-- Usuarios -->
			<div class="col-12 col-md-6 col-lg-4">
				<a href=""
					class="card h-100 text-decoration-none shadow-sm">

					<div class="card-body">
						<h5 class="card-title">Usuarios</h5>
						<p class="card-text">Administrar los usuarios.</p>
					</div>
				</a>
			</div>
		</div>
	</div>
<!--Script para que funcione el js de bootstrap-->
<%@ include file="/WEB-INF/common/scripts.jsp"%>
</body>
</html>