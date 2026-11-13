package servlet;

import java.io.IOException;

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
		newRev.setPunctuation(Double.parseDouble(request.getParameter("punctuation")));
		newRev.setContent(request.getParameter("content"));
		
		
		ReviewDAO dao  = new ReviewDAO();
		dao.add(newRev, idPre, userDTO.getId());
		response.sendRedirect("PresentationServlet?operation=detail&id=" + idPre);
		
	}
	
	
	
	
	
	
	
	
	//UPDATE
	public void updateReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UserSessionDTO userDTO = (UserSessionDTO) session.getAttribute("user");  
		int idPre = Integer.parseInt(request.getParameter("presentationId"));
		
		Review updRev = new Review();
		updRev.setPunctuation(Double.parseDouble(request.getParameter("punctuation")));
		updRev.setContent(request.getParameter("content"));
		
		
		ReviewDAO dao  = new ReviewDAO();
		dao.update(updRev, idPre, userDTO.getId());
		
		
		
		response.sendRedirect("PresentationServlet?operation=detail&id=" + idPre);
	}
	
	
	
	
	//DELETE
	public void deleteReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UserSessionDTO userDTO = (UserSessionDTO) session.getAttribute("user");  
		int idPre = Integer.parseInt(request.getParameter("presentationId"));
		
		
		ReviewDAO dao  = new ReviewDAO();
		dao.delete(userDTO.getId(), idPre);
		response.sendRedirect("PresentationServlet?operation=detail&id=" + idPre);
	}



}
