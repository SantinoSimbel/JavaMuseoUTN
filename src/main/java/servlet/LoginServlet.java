package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.UserDAO;
import entities.User;
import entities.UserSessionDTO;
import logic.UserLogic;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		case "login":
			//getAllCategories(request, response);
			break;
		case "logout":
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
		case "login":
			login(request, response);
			break;
		case "update":
			//updateCategory(request, response);
			break;
		case "delete":
			//deleteCategory(request, response);
			break;
		}
	}
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User newUser = new User();
		UserLogic logic = new UserLogic();
		
		newUser.setEmail(request.getParameter("email"));
		newUser.setPassword(request.getParameter("password"));
		
		UserDAO dao = new UserDAO();
		User actualUser = dao.searchByEmail(newUser);
		
		UserSessionDTO userDTO = new UserSessionDTO(actualUser);
		
		//guardamos el usuario en la session y permanece ahi
		request.getSession().setAttribute("user",userDTO);
		response.sendRedirect("index.jsp");
		
	}
}
