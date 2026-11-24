package servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.HomeLogic;





/**
 * Servlet implementation class ExhibitionServlet
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		    getEventsAndItems(request, response);
		
	}

		
	
	
	//GETALL
	public void getEventsAndItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HomeLogic logHome = new HomeLogic();
	    try {
	        request.setAttribute("someExhibitions", logHome.getExhibitions());
	        request.setAttribute("somePresentations", logHome.getPresentations());
	        request.setAttribute("someItems", logHome.getItems());
	    } catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	    }
	    request.getRequestDispatcher("/WEB-INF/home/index.jsp").forward(request, response);
	}
	
	
	
	
}
