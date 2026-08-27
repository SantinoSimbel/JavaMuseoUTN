<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<%@ include file="/WEB-INF/common/head.jsp"%>
</head>
<body>

	<!-- navbar -->
	<%@ include file="/WEB-INF/common/navbar.jsp"%>

	<!-- Titulo -->
	<div class="container mt-4">
		<h1 class="mb-2 text-center">Iniciar sesión</h1>
		<p class="text-muted mb-4 text-center">Ingrese sus datos para continuar:</p>
		
		<!-- Formulario -->
		<div class="row justify-content-center">
    		<div class="col-12 col-lg-6">
				<div class = "card shadow-sm">	
					<div class = "card-body">
						<form action="###" method="POST">
							<div class= "mb-3">
								<b><label class="form-label">Email:</label></b>
								<input type="text" name="email" class="form-control" required>
								<b><label class="form-label">Contraseña:</label></b>
								<input type="password" name="password" class="form-control" required>
							</div>
							<div class="text-center">
								<button type="submit" class="btn btn-success">Iniciar sesión</button>
							</div>
						</form>
						<hr>
						<p class="text-center mb-0">
							¿No tiene una cuenta? <a href="###" class="text-decoration-none"> Regístrese </a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--Script para que funcione el js de bootstrap-->
	<%@ include file="/WEB-INF/common/scripts.jsp"%>
</body>
</html>