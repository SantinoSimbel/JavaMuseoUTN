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
		case "showAddForm":
			showAddForm(request, response);
			break;
		case "showEditForm":
			showEditForm(request, response);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String operation = request.getParameter("operation");
		
		switch (operation) {
		case "add":
			addCategory(request, response);
			break;
		case "update":
			updateCategory(request, response);
			break;
		}
	}
	
	public void getAllCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoryDAO dao  = new CategoryDAO();
		LinkedList<Category> categories = dao.list();
		
		// guardo en el request la lista de categorias que le voy a mandar al jsp. ("name", objeto)
		request.setAttribute("allCategories", categories);;
		//le mando el request(con las allCategories) y la response al jsp para que que responda él.
		request.getRequestDispatcher("/WEB-INF/category/list.jsp").forward(request,response);
	}
	
	public void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoryDAO dao  = new CategoryDAO();
		LinkedList<Category> categories = dao.list();
		request.setAttribute("allCategories", categories);;
		request.getRequestDispatcher("/WEB-INF/category/add.jsp").forward(request,response);
	}
	
	public void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoryDAO dao  = new CategoryDAO();
		LinkedList<Category> categories = dao.list();
		request.setAttribute("allCategories", categories);;
		request.getRequestDispatcher("/WEB-INF/category/update.jsp").forward(request,response);
	}
	
	public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category newCat = new Category();
		newCat.setName(request.getParameter("name"));
		
		CategoryDAO dao  = new CategoryDAO();
		dao.add(newCat);
		//ver si esto funciona: (si funciona, pero como conviene hacerlo en el TP?)
		showAddForm(request, response);
		
	}
	
	public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category newCat = new Category();
		newCat.setId(Integer.parseInt(request.getParameter("id")));
		newCat.setName(request.getParameter("name"));
		
		CategoryDAO dao  = new CategoryDAO();
		dao.update(newCat);
		//ver si esto funciona: (si funciona, pero como conviene hacerlo en el TP?)
		showAddForm(request, response);
		
	}

}
