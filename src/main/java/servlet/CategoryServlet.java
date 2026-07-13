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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//agarramos el parametro action de la url. ...action="list"...
		String action = request.getParameter("action");
		//si action era = list entonces llamamos al getAll
		if ("list".equals(action)) {
			getAllCategories(request, response);
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void getAllCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoryDAO dao  = new CategoryDAO();
		LinkedList<Category> categories = dao.list();
		
		// guardo en el request la lista de categorias que le voy a mandar al jsp. ("name", objeto)
		request.setAttribute("allCategories", categories);;
		//le mando el request(con las allCategories) y la response al jsp para que que responda él.
		request.getRequestDispatcher("/WEB-INF/category/list.jsp").forward(request,response);
	}

}
