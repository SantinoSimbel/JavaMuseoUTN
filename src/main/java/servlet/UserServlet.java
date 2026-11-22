package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.PurchaseDAO;
import data.UserDAO;
import entities.PurchasePresentationDTO;
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
		
		//la declaro aca para poder mandarla vacia si sale mal
		LinkedList<User> users = new LinkedList<>();
		
		try {
			UserDAO dao  = new UserDAO();
			
			users = dao.list();
			
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		
		request.setAttribute("allUsers", users);
		request.getRequestDispatcher("/WEB-INF/user/list.jsp").forward(request,response);
	}
	
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//showform prepara lo necesario para el form.jsp
		User user = new User();
		boolean editing;
		
		String operation = request.getParameter("operation");
		
		if ("new".equals(operation)) {
			editing =  false;
			
		} else {
			editing = true;
			
			LinkedList<PurchasePresentationDTO> purchasesWithPre = new LinkedList<>();
			
			try {
				//En vez de sacar el id del link lo saco de la session
				UserSessionDTO userDTO = (UserSessionDTO) request.getSession().getAttribute("user");
				
				User u = new User();
				u.setId(userDTO.getId());
				
				UserDAO dao = new UserDAO();
				user = dao.search(u);
				
				if (user == null) {
					user = new User();
					throw new Exception("El usuario no existe.");
				}
				
				PurchaseDAO purDao = new PurchaseDAO();
				purchasesWithPre  = purDao.searchAllByUser(user);
				
			} catch (Exception e) {
				request.setAttribute("errorMessage", e.getMessage());
			}
			
			request.setAttribute("allPurchasesWithPre", purchasesWithPre);
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

		} catch (Exception e) {
			
			request.setAttribute("errorMessage", e.getMessage());
			
			//mando el user con sus datos actuales para que el usuario no escriba todo de nuevo
			request.setAttribute("oneUser", newUser); 
			request.setAttribute("editing", false);
			request.getRequestDispatcher("/WEB-INF/user/form.jsp").forward(request,response);
			//volvemos para que no se ejecute el sendRedirect que hay afuera
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/");
		
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
			

		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			
			//mando el user con sus datos actuales para que el usuario no escriba todo de nuevo
			request.setAttribute("oneUser", newUser); 
			request.setAttribute("editing", true);
			
			LinkedList<PurchasePresentationDTO> purchasesWithPre = new LinkedList<>();
			try {
				PurchaseDAO purDao = new PurchaseDAO();
				purchasesWithPre = purDao.searchAllByUser(newUser);
				
			} catch (Exception e2) {
				//Se envia purchasesWithPre vacia
			}
			
			request.setAttribute("allPurchasesWithPre", purchasesWithPre);
			
			request.getRequestDispatcher("/WEB-INF/user/form.jsp").forward(request,response);
			return;
		}
		response.sendRedirect("UserServlet?operation=edit");
				
	}
	
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User delUser = new User();
		
		//En vez de sacar el id del link lo saco de la session
	    UserSessionDTO userDTO = (UserSessionDTO) request.getSession().getAttribute("user");
	    
		delUser.setId(userDTO.getId());
		
		UserLogic logic = new UserLogic();
		try {
			logic.deleteUser(delUser);
			
			//cierro la session
			request.getSession().invalidate();
			
		} catch (Exception e) {
			
			request.setAttribute("errorMessage", e.getMessage());
			
			LinkedList<PurchasePresentationDTO> purchasesWithPre = new LinkedList<>();
			try {
				//mando el user con sus datos actuales para que el usuario no escriba todo de nuevo
				UserDAO userDAO = new UserDAO();
				delUser = userDAO.search(delUser);
				
				if (delUser == null) {
					delUser = new User();
				}
					
				
				PurchaseDAO purDao = new PurchaseDAO();
				purchasesWithPre = purDao.searchAllByUser(delUser);
			} catch (Exception e2) {
				// Se envia delUser y purchasesWithPre vacios
			}
			request.setAttribute("oneUser", delUser);
			request.setAttribute("editing", true);
			request.setAttribute("allPurchasesWithPre", purchasesWithPre);
			
			request.getRequestDispatcher("/WEB-INF/user/form.jsp").forward(request,response);
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	public void changeUserRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserSessionDTO userDTO = (UserSessionDTO) request.getSession().getAttribute("user");
		
		UserLogic logic = new UserLogic();
		
		try {
			User editUser = new User();
			editUser.setId(Integer.parseInt(request.getParameter("id")));
			
			logic.changeUserRole(editUser, userDTO);
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id del usuario no es válido.");
			getAllUsers(request, response);
			return;
			
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			  getAllUsers(request, response);
			  return;
		}
		
		response.sendRedirect("UserServlet?operation=list");
	}
				
}


