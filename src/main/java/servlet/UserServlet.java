package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.CategoryDAO;
import data.UserDAO;
import entities.Category;
import entities.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
			//getAllCategories(request, response);
			break;
		case "new":
			showForm(request, response);
			break;
		case "edit":
			//showForm(request, response);
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
			addUser(request, response);
			break;
		case "update":
			//updateCategory(request, response);
			break;
		case "delete":
			//deleteCategory(request, response);
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
	
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//showform prepara lo necesario para el form.jsp
		User user;
		boolean editing;
		
		String operation = request.getParameter("operation");
		
		if ("new".equals(operation)) {
			user = new User();
			editing =  false;
		} else {
			editing = true;
			int id = Integer.parseInt(request.getParameter("id"));
			
			User u = new User();
			u.setId(id);
			
			UserDAO dao = new UserDAO();
			user = dao.search(u);
		}
		
		request.setAttribute("oneUser", user);
		request.setAttribute("editing", editing);
		
		request.getRequestDispatcher("/WEB-INF/user/form.jsp").forward(request,response);
	}

	
	public void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User newUser = new User();
		// ¿hacer antes un?:
		//String email = request.getParameter("email");
		//String password = request.getParameter("password");
		// ¿y aca validar en el logic UserLogic o LoginLogic que esten bien y luego setearlos?
		newUser.setDni(request.getParameter("dni"));
		newUser.setName(request.getParameter("name"));
		newUser.setSurname(request.getParameter("surname"));
		newUser.setEmail(request.getParameter("email"));
		newUser.setPassword(request.getParameter("password"));
		//Pensar como manejar rol tambien
		newUser.setRole(request.getParameter("role"));
		
		UserDAO dao = new UserDAO();
		dao.add(newUser);
		//response.sendRedirect("Mandarlo a mi perfil o al menu comun");
		
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

