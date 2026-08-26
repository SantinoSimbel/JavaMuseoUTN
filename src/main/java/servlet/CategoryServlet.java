package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.CategoryDAO;
import entities.Category;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//agarramos el parametro operation de la url. ...operation="list"...
		String operation = request.getParameter("operation");
		
		switch (operation) {
		case "list":
			getAllCategories(request, response);
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
			addCategory(request, response);
			break;
		case "update":
			updateCategory(request, response);
			break;
		case "delete":
			deleteCategory(request, response);
			break;
		}
	}
	
	public void getAllCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoryDAO dao  = new CategoryDAO();
		LinkedList<Category> categories = dao.list();
		
		// guardo en el request la lista de categorias que le voy a mandar al jsp. ("name", objeto)
		request.setAttribute("allCategories", categories);
		//le mando el request(con las allCategories) y la response al jsp para que que responda él.
		request.getRequestDispatcher("/WEB-INF/category/list.jsp").forward(request,response);
	}
	
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//showform prepara lo necesario para que el form.jsp pueda mostrarse o vacio o con la categoria a editar
		Category cat;
		boolean editing;
		
		String operation = request.getParameter("operation");
		
		if ("new".equals(operation)) {
			cat = new Category();
			editing =  false;
		} else {
			editing = true;
			int id = Integer.parseInt(request.getParameter("id"));
			
			Category c = new Category();
			c.setId(id);
			
			CategoryDAO dao  = new CategoryDAO();
			cat = dao.search(c);
		}
		
		request.setAttribute("oneCategory", cat);
		request.setAttribute("editing", editing);
		
		request.getRequestDispatcher("/WEB-INF/category/form.jsp").forward(request,response);
	}

	
	public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category newCat = new Category();
		newCat.setName(request.getParameter("name"));
		
		CategoryDAO dao  = new CategoryDAO();
		dao.add(newCat);
		response.sendRedirect("CategoryServlet?operation=list");
		
	}
	
	public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category newCat = new Category();
		newCat.setId(Integer.parseInt(request.getParameter("id")));
		newCat.setName(request.getParameter("name"));
		
		CategoryDAO dao  = new CategoryDAO();
		dao.update(newCat);
		response.sendRedirect("CategoryServlet?operation=list");
	}
	
	public void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category delCat = new Category();
		delCat.setId(Integer.parseInt(request.getParameter("id")));
		
		CategoryDAO dao  = new CategoryDAO();
		dao.delete(delCat);
		response.sendRedirect("CategoryServlet?operation=list");
	}

}
