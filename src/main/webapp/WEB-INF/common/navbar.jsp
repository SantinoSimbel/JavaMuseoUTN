<!-- Solo contiene el codigo navbar para no generar problemas html -->
<%@page import="entities.UserSessionDTO"%>
<%
	UserSessionDTO userDTO = (UserSessionDTO) session.getAttribute("user");
%>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
	<div class="container">
	
		<!--Izquierda-->
		<a class="navbar-brand fs-3 fw-semibold" href="index.jsp">Museo UTN</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<div class="collapse navbar-collapse" id="navbarNav">
			<!--Centro-->
			<ul class="navbar-nav position-absolute start-50 translate-middle-x navbar-links fs-5 gap-lg-5">
				<li class="nav-item"><a class="nav-link" href="#"><i class="bi bi-bank"></i> Museo virtual</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#"><i class="bi bi-easel"></i> Exhibiciones</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#"><i class="bi bi-person-video3"></i> Presentaciones</a>
				</li>
			</ul>
			
			<!--Derecha-->
			<ul class="navbar-nav ms-auto fs-5">
				<%if (userDTO == null) {%>
					<li class="nav-item"><a class="nav-link" href="login.jsp"><i class="bi bi-person-circle"></i> Iniciar sesión</a></li>
				<%} else {%>
					<li class="nav-item"><a class="nav-link" href="###"><i class="bi bi-person-circle"></i> Mi cuenta</a></li>
				<%}%>
			</ul>
		</div>
	</div>
</nav>