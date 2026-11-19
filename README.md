# TP Java Museo UTN

## Integrantes

- 55033 Garcia, Miqueas Cristián
- 49438 Simbel Pagliero, Santino Lucio

## Descripción

Página para un museo virtual y presencial de artículos ficticios (procedentes de videojuegos, anime, películas)
Los usuarios pueden ver los artículos  expuestos en la página, pueden buscarlos por categoría (videojuegos, anime, películas), pueden ver la información de las  exhibiciones (eventos donde se muestran presencialmente muchos artículos con algo en comun), como tambien ver informacion y sacar cupo para presentaciónes (las presentaciónes son presenciales sobre uno o varios artículos y tienen cupo limitado).
El sistema tiene 3 tipos de usuarios, los administradores, los invitados y los registrados(los registrados pueden comprar entradas a presentaciónes).

## Modelo

[Imágen del Modelo](https://drive.google.com/file/d/1JlHYepIgV6Vz3JOAJYQTQy4ENsG2jMB9/view?usp=sharing)

[Documento de organizacion](https://docs.google.com/document/d/1ofofCWA-NhJqfKzBfnuxRcjVR1hn-_lQ-hIlwk1Gtow/edit?tab=t.0)



## Requerimientos

### Regularidad

|Requerimiento|Detalle/Listado de casos incluidos|
|:-|-:|
|ABMC simple|Categoría|
|ABMC dependiente|Artículo, Evento, compra|
|CU NO-ABMC|Comprar entrada|

### Aprobación Directa

|Requerimiento|Detalle/Listado de casos incluidos|
|:-|-:|
|ABMC|Categoría, Usuario, Artículo, Evento, reseña, compra|
|CU "Complejo"(nivel resumen)|Comprar entrada, reseñar presentación|
|Listado complejo|Artículos por categoría, Evento por estado|
|Nivel de acceso|Admin, Registrado, Invitado|
|Manejo de errores|no requiere detalle|
|requerimiento extra obligatorio (**)|Manejo de archivos|
|publicar el sitio|no requiere detalle|

### Requerimientos extra - AD
|Requerimiento |Detalle/Listado de casos incluidos|
|:-|:-|
|Manejo de archivos| Manejo de archivos en imagenes de items|
|Custom exceptions||
|Log de errores||
|Envio de emails||
