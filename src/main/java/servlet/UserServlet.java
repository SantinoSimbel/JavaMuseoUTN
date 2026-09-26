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
import entities.UserSessionDTO;
import logic.UserLogic;

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
			getAllUsers(request, response);
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
			addUser(request, response);
			break;
		case "update":
			updateUser(request, response);
			break;
		case "delete":
			deleteUser(request, response);
			break;
		case "changeRole":
			changeUserRole(request, response);
			break;
		}
	}
	
	public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDAO dao  = new UserDAO();
		LinkedList<User> users = dao.list();
		
		request.setAttribute("allUsers", users);
		request.getRequestDispatcher("/WEB-INF/user/list.jsp").forward(request,response);
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
			
			//En vez de sacar el id del link lo saco de la session
			UserSessionDTO userDTO = (UserSessionDTO) request.getSession().getAttribute("user");
			
			User u = new User();
			u.setId(userDTO.getId());
			
			UserDAO dao = new UserDAO();
			user = dao.search(u);
		}
		
		request.setAttribute("oneUser", user);
		request.setAttribute("editing", editing);
		
		request.getRequestDispatcher("/WEB-INF/user/form.jsp").forward(request,response);
	}

	
	public void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User newUser = new User();

		newUser.setDni(request.getParameter("dni"));
		newUser.setName(request.getParameter("name"));
		newUser.setSurname(request.getParameter("surname"));
		newUser.setEmail(request.getParameter("email"));
		newUser.setPassword(request.getParameter("password"));
		
		UserLogic logic = new UserLogic();
		
		try {
			logic.registerUser(newUser);
			
			UserSessionDTO userDTO = new UserSessionDTO(newUser);
			
			//guardamos el usuario en la session y permanece ahi
			request.getSession().setAttribute("user",userDTO);
			response.sendRedirect("index.jsp");
		} catch (Exception e) {
			
			request.setAttribute("errorMessage", e.getMessage());
			
			//mando el user con sus datos actuales para que el usuario no escriba todo de nuevo
			request.setAttribute("oneUser", newUser); 
			request.setAttribute("editing", false);
			request.getRequestDispatcher("/WEB-INF/user/form.jsp").forward(request,response);
		}

		
	}
	
	public void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User newUser = new User();
		//En vez de sacar el id del link lo saco de la session
	    UserSessionDTO userDTO = (UserSessionDTO) request.getSession().getAttribute("user");
	    
		newUser.setId(userDTO.getId());
		
		newUser.setDni(request.getParameter("dni"));
		newUser.setName(request.getParameter("name"));
		newUser.setSurname(request.getParameter("surname"));
		newUser.setEmail(request.getParameter("email"));
		newUser.setPassword(request.getParameter("password"));
		
		UserLogic logic = new UserLogic();
		
		try {
			logic.updateUser(newUser);
			
			UserSessionDTO updatedUserDTO = new UserSessionDTO(newUser);
			request.getSession().setAttribute("user",updatedUserDTO);
			
			response.sendRedirect("UserServlet?operation=edit");
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			
			//mando el user con sus datos actuales para que el usuario no escriba todo de nuevo
			request.setAttribute("oneUser", newUser); 
			request.setAttribute("editing", true);
			request.getRequestDispatcher("/WEB-INF/user/form.jsp").forward(request,response);
		}
				
	}
	
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User delUser = new User();
		
		//En vez de sacar el id del link lo saco de la session
	    UserSessionDTO userDTO = (UserSessionDTO) request.getSession().getAttribute("user");
	    
		delUser.setId(userDTO.getId());
		
		UserDAO dao  = new UserDAO();
		dao.delete(delUser);
		
		//cierro la session
		request.getSession().invalidate();
		
		response.sendRedirect("index.jsp");
	}
	
	public void changeUserRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User editUser = new User();
		editUser.setId(Integer.parseInt(request.getParameter("id")));
		
		UserSessionDTO userDTO = (UserSessionDTO) request.getSession().getAttribute("user");
		
		UserLogic logic = new UserLogic();
		try {
			logic.changeUserRole(editUser, userDTO);
			response.sendRedirect("UserServlet?operation=list");
			
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			  getAllUsers(request, response);
		}
		
	}
				
}


