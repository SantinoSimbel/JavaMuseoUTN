package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.ReviewDAO;
import entities.Review;
import entities.UserSessionDTO;

/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewServlet() {
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
		case "add":
			addReview(request, response);
			break;
		case "update":
			updateReview(request, response);
			break;
		case "delete":
			deleteReview(request, response);
			break;
		}
	}
	
	
	
	

	
	
	
	
	
	
	//ADD
	public void addReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		UserSessionDTO userDTO = (UserSessionDTO) session.getAttribute("user");  
		int idPre = Integer.parseInt(request.getParameter("presentationId"));
		
		
		Review newRev = new Review();
		newRev.setUserId(userDTO.getId());
		newRev.setPunctuation(Double.parseDouble(request.getParameter("punctuation")));
		newRev.setContent(request.getParameter("content"));
		
		
		ReviewDAO dao  = new ReviewDAO();
		dao.add(newRev, idPre);
		request.getRequestDispatcher("/WEB-INF/presentation/detail.jsp").forward(request,response);
		
	}
	
	
	
	
	
	
	
	
	//UPDATE
	public void updateReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UserSessionDTO userDTO = (UserSessionDTO) session.getAttribute("user");  
		int idPre = Integer.parseInt(request.getParameter("presentationId"));
		
		Review updRev = new Review();
		updRev.setUserId(userDTO.getId());
		updRev.setPunctuation(Double.parseDouble(request.getParameter("punctuation")));
		updRev.setContent(request.getParameter("content"));
		
		
		ReviewDAO dao  = new ReviewDAO();
		dao.update(updRev, idPre);
		request.getRequestDispatcher("/WEB-INF/presentation/detail.jsp").forward(request,response);
	}
	
	
	
	
	
	
	
	
	//DELETE
	public void deleteReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UserSessionDTO userDTO = (UserSessionDTO) session.getAttribute("user");  
		int idPre = Integer.parseInt(request.getParameter("presentationId"));
		
		
		ReviewDAO dao  = new ReviewDAO();
		dao.delete(userDTO.getId(), idPre);
		request.getRequestDispatcher("/WEB-INF/presentation/detail.jsp").forward(request,response);
	}



}
