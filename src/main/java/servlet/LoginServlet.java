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
		response.sendRedirect("login.jsp");
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
		case "logout":
			logout(request, response);
			break;
		}
	}
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loginUser = new User();

		loginUser.setEmail(request.getParameter("email"));
		loginUser.setPassword(request.getParameter("password"));
		
		UserLogic logic = new UserLogic();
		
		try {
			User currentUser = logic.loginUser(loginUser);
			
			UserSessionDTO userDTO = new UserSessionDTO(currentUser);
			
			//guardamos el usuario en la session y permanece ahi
			request.getSession().setAttribute("user",userDTO);
			response.sendRedirect("index.jsp");
			
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.setAttribute("oneUser", loginUser);
			request.getRequestDispatcher("login.jsp").forward(request,response);
		}

		
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession().invalidate();
		response.sendRedirect("index.jsp");
		
	}
}
