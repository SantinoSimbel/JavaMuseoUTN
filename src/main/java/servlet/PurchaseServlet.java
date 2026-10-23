package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.PresentationDAO;
import entities.Presentation;
import entities.Purchase;
import entities.UserSessionDTO;
import logic.CategoryLogic;
import logic.PurchaseLogic;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String operation = request.getParameter("operation");
		
		switch (operation) {
		case "buy":
			purchasePresentation(request, response);
			break;
		}
	}

	
	public void purchasePresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   //valido que tenga la session iniciada
		HttpSession session = request.getSession(false);

	    UserSessionDTO userDTO = (UserSessionDTO) session.getAttribute("user");
	    
	    
		Purchase newPur = new Purchase();
		
		newPur.setUserId(userDTO.getId());
		
		PurchaseLogic logic = new PurchaseLogic();
		
		try {
			newPur.setPresentationId(Integer.parseInt(request.getParameter("presentationId")));
			//quantity son las nuevas entradas que va a comprar
			newPur.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			
			
			logic.addPurchase(newPur);
			
			response.sendRedirect("PresentationServlet?operation=userList");
			 
		} catch (Exception e) {
			PresentationDAO preDAO = new PresentationDAO();
			Presentation pre = new Presentation();
			pre.setId(newPur.getPresentationId());
			pre = preDAO.search(pre);
			
			request.setAttribute("onePresentation", pre);
			
			request.setAttribute("errorMessage", e.getMessage());

			request.getRequestDispatcher("/WEB-INF/presentation/detail.jsp").forward(request,response);
		}
	}
	
}

