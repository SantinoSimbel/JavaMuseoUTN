package servlet;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import data.CategoryDAO;
import data.ItemDAO;
import entities.Category;
import entities.Item;
import logic.ItemLogic;	

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/ItemServlet")
@MultipartConfig
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String operation = request.getParameter("operation");
		
		switch (operation) {
		case "list":
			getAllItem(request, response, operation);
			break;
		case "userList":
			getAllItem(request, response, operation);
			break;
		case "detail":
			getOneItem(request, response);
			break;
		case "new":
			showForm(request, response);
			break;
		case "edit":
			showForm(request, response);
			break;
		}
	}

	
	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String operation = request.getParameter("operation");
		
		switch (operation) {
		case "add":
			addItem(request, response);
			break;
		case "update":
			updateItem(request, response);
			break;
		case "delete":
			deleteItem(request, response);
			break;
		}
	}
	
	
	
	
	
	//GETALL
	public void getAllItem(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException {
		
		String categoryId = request.getParameter("categoryId");
		
		ItemDAO dao  = new ItemDAO();
		CategoryDAO catDao = new CategoryDAO();
		
	    LinkedList<Item> items = new LinkedList<>();
	    LinkedList<Category> allCategories = new LinkedList<>();
		
		try {
			allCategories = catDao.list();
			
			//filtro del userList
			if (categoryId == null || categoryId.isEmpty()) {
				items = dao.list();	
				
			} else {
				Category c = new Category();
				c.setId(Integer.parseInt(categoryId));
				items = dao.listByCategory(c);
			}
			
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "Error al ejecutar el filtro. El id de la categoría no es valido");
			
		} catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	    }
		
		
	    request.setAttribute("allItems", items);
	    request.setAttribute("allCategories", allCategories);
	    request.setAttribute("selectedCategory", categoryId);
		
		String jsp = "list".equals(view)
				? "/WEB-INF/item/list.jsp"
				: "/WEB-INF/item/userList.jsp";
		
		request.getRequestDispatcher(jsp).forward(request,response);
	}
	
	
	
	
	
	
	
	
	
	//getOne
	public void getOneItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//estos parametros son para saber a que link volver al apretar el boton de volver
		String from =request.getParameter("from");
		String eventId =request.getParameter("eventId");
		
		ItemLogic logic  = new ItemLogic();
		Item item = new Item();
		
		try {
			item.setId(Integer.parseInt(request.getParameter("id")));
			item = logic.getItem(item);
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id del item no es válido.");
			getAllItem(request, response, "userList");
			return;
			
		} catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	        getAllItem(request, response, "userList");
	        return;
	    }
		
		
		
		request.setAttribute("oneItem", item);
		request.setAttribute("from", from);
		request.setAttribute("eventId", eventId);
		
		request.getRequestDispatcher("/WEB-INF/item/detail.jsp").forward(request,response);
	}
	
	
	
	
	
	
	
	
	
	//SHOWFORM
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item ite = new Item();
		boolean editing = false;
		CategoryDAO categoryDAO = new CategoryDAO();
		ItemLogic logic = new ItemLogic();
		LinkedList<Category> categories = new LinkedList<Category>();
		
		String operation = request.getParameter("operation");
		
		try {
			
			categories = categoryDAO.list();
			if ("edit".equals(operation)) {
				editing = true;
				int id = Integer.parseInt(request.getParameter("id"));
				
				ite.setId(id);
				ite = logic.getItem(ite);
			}
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id del item no es válido.");
	        getAllItem(request, response, "list");
			return;
			
		} catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	        getAllItem(request, response, "list");
	    }
		
		
		request.setAttribute("oneItem", ite);
		request.setAttribute("editing", editing);
		request.setAttribute("allCategories", categories);

		
		request.getRequestDispatcher("/WEB-INF/item/form.jsp").forward(request,response);
	}

	
	
	
	
	
	//ADD
	public void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item newIte = new Item();
		newIte.setName(request.getParameter("name"));
		newIte.setDescription(request.getParameter("description"));
		LinkedList<Category> categories = new LinkedList<Category>();

		
		Category cat = new Category();
		
		try {
			
		    cat.setId(Integer.parseInt(request.getParameter("category_id")));
		    newIte.setCategory(cat);
			
			//guardamos la imagen fisicamente y en la DB el path a ella
			String imagePath = saveImage(request);
			
			if (imagePath == null) {
			    throw new ServletException("Debe seleccionar una imagen.");
			}
			
			newIte.setPicture(imagePath);
			
			ItemDAO dao  = new ItemDAO();
			dao.add(newIte);

		
		} catch (NumberFormatException e) {
			try {
				CategoryDAO categoryDAO = new CategoryDAO();
				categories = categoryDAO.list();
			} catch (Exception e2) {}

			request.setAttribute("allCategories", categories);
			request.setAttribute("errorMessage", "El id de la categoría no es válido.");
			request.setAttribute("oneItem", newIte);
	        request.setAttribute("editing", false);
			request.getRequestDispatcher("/WEB-INF/item/form.jsp").forward(request, response);
			
			return;
			
		} catch (Exception e) {
			
			try {
				CategoryDAO categoryDAO = new CategoryDAO();
				categories = categoryDAO.list();
			} catch (Exception e2) {}

			request.setAttribute("allCategories", categories);
			request.setAttribute("errorMessage", e.getMessage());
			request.setAttribute("oneItem", newIte);
	        request.setAttribute("editing", false);
			request.getRequestDispatcher("/WEB-INF/item/form.jsp").forward(request, response);
			
			return;
		}
		
		
		response.sendRedirect("ItemServlet?operation=list");
		
	}
	
	
	
	
	
	
	
	//UPDATE
	public void updateItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item newIte = new Item();
		LinkedList<Category> categories = new LinkedList<Category>();
		
		newIte.setName(request.getParameter("name"));
		newIte.setDescription(request.getParameter("description"));
		
		Category cat = new Category();
		ItemLogic logic = new ItemLogic();

	    
	    try {
			Integer itemId = (Integer.parseInt(request.getParameter("id"))); 
			newIte.setId(itemId);
			
			cat.setId(Integer.parseInt(request.getParameter("category_id")));
		    newIte.setCategory(cat);
			
	    	ItemDAO dao  = new ItemDAO();
	    	//busco el item actual para conservar su imagen anterior en caso de que no se haya cambiado
	    	Item currentItem = new Item();
	    	currentItem.setId(itemId);
	    	currentItem = logic.getItem(currentItem);
	    	
	    	//recupero el path de la imagen anterior
	    	String oldImagePath = currentItem.getPicture();
	    	
			newIte.setPicture(oldImagePath);
	    	
	    	//Intento guardar nueva imagen (devuelve null si no hay una selecionada)
	    	
	    	String imagePath = saveImage(request);
	    	
	    	if (imagePath != null) {
				//nueva imagen, entonces guardo
	    		newIte.setPicture(imagePath);
			}
	    
			dao.update(newIte);
			
			//si habia nueva imagen => elimino fisicamente la vieja
			
	    	if (imagePath != null) {
				//nueva imagen, entonces guardo
	    		deleteImage(oldImagePath);
			}
			
			
		} catch (NumberFormatException e) {

			try {
				CategoryDAO categoryDAO = new CategoryDAO();
				categories = categoryDAO.list();
			} catch (Exception e2) {}

			request.setAttribute("allCategories", categories);
			request.setAttribute("errorMessage", "El id del item o la categoría no es válido.");
			request.setAttribute("oneItem", newIte);
	        request.setAttribute("editing", true);
			request.getRequestDispatcher("/WEB-INF/item/form.jsp").forward(request, response);
			return;
			
		} catch (Exception e) {

			try {
				CategoryDAO categoryDAO = new CategoryDAO();
				categories = categoryDAO.list();
			} catch (Exception e2) {}

			request.setAttribute("allCategories", categories);
			request.setAttribute("errorMessage", e.getMessage());
			request.setAttribute("oneItem", newIte);
	        request.setAttribute("editing", true);
			request.getRequestDispatcher("/WEB-INF/item/form.jsp").forward(request, response);
			return;

		}

	    response.sendRedirect("ItemServlet?operation=list");


	}
		    
	
	
	
	
	
	
	//DELETE
	public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item delIte = new Item();
		ItemLogic logic = new ItemLogic();
		
		try {
			delIte.setId(Integer.parseInt(request.getParameter("id")));

			//busco item actual para tener el path de la img
			Item currentItem = logic.getItem(delIte);
			
			logic.deleteItem(delIte);
			
			deleteImage(currentItem.getPicture());
			
		
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id del item no es válido.");
			getAllItem(request, response, "list");
			return;
			
		} catch (Exception e) {
			
			request.setAttribute("errorMessage", e.getMessage());
			getAllItem(request, response, "list");
			return;
		}
		
		response.sendRedirect("ItemServlet?operation=list");
	}
	
	

	
	

	private String saveImage(HttpServletRequest request) throws ServletException, IOException {
		
		//recupero del formulario la imagen. se usa gertPart por que es un archivo
		Part imagePart = request.getPart("picture");
		
		//si no se selecciono ninguna imagen -> null
		if (imagePart == null || imagePart.getSize()== 0) {
			return null;
		}
		
		//comprobamos que el archivo sea un imagen
		String contentType = imagePart.getContentType();
		
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new ServletException("El archivo seleccionado no es una imagen.");
		}
		
		//obtengo la extension para luego validar formato
		String submittedName = imagePart.getSubmittedFileName();
		String extension = "";
		
		//buscamos el ultimo punto del nombre (hola.PNG) y guardamos lo proximo en minuscula (.png)
		if (submittedName != null && submittedName.lastIndexOf(".")!= -1) {
			extension = submittedName.substring(submittedName.lastIndexOf(".")).toLowerCase();
		}
		
		//formatos permitidos
		if (!extension.matches("\\.(jpg|jpeg|png|webp)")) {
			throw new ServletException("Formato de imagen no permitido.");		
		}
		
		// creamos la carpeta donde se guardan las imagenes
		String uploadPath = getServletContext().getRealPath("/uploads/items");

		// uploadDir representa la ruta donde vamos a guardar los archivos, todavia no crea la carpeta
		File uploadDir = new File(uploadPath);
		
		if (!uploadDir.exists()) {
			//creamos las carpetas y subcarpetas necesarias
			uploadDir.mkdirs();
			
		}
		
		//generamos un nombre unico para la img
		
		String fileName = UUID.randomUUID().toString() + extension;
		
		//guardamos el archivo fisicamente 
		File imageFile = new File(uploadDir, fileName);
		imagePart.write(imageFile.getAbsolutePath());
		
		return "uploads/items/" + fileName;
	}
	
	
	
	
	
	
	
	private void deleteImage(String imagePath) {

		//si no hay imagePath termina metodo aca para no intentar borrar algo inexistente
	    if (imagePath == null || imagePath.isEmpty()) {
	        return;
	    }
	    
		//obtengo la ruta fisica de la carpeta donde  estan guardadas las img
		String uploadPath = getServletContext().getRealPath("/uploads/items");
		
		//si no localizamos la carpeta, no podemos buscar ni eliminar la imagen, asi que volvemos
		if (uploadPath == null) {
			return;
		}
		
		//representacion de la carpeta donde estan las img
		File uploadDir = new File(uploadPath);
		// uploadDir tiene la direccion a la carpeta correcta, y luego agarro solo el nombre de imagePath que llego como parametro. Para evitar que se borren cosas de otra carpeta. Entonces construyo nueva direccion con la carpeta correcta y el nombre de la img que quiero borrar.
		File imageFile = new File(uploadDir,new File(imagePath).getName());
		
		if (imageFile.exists()) {
			imageFile.delete();
		}
		
	}	

}
