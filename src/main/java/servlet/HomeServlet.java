package servlet;

import java.io.IOException;
import java.util.LinkedList;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import logic.HomeLogic;
import entities.Presentation;
import entities.Exhibition;
import entities.Item;





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
	    
	    LinkedList<Exhibition> exhibitions = new LinkedList<Exhibition>();
	    LinkedList<Presentation> presentations = new LinkedList<Presentation>();
	    LinkedList<Item> items = new LinkedList<Item>();
	    
	    try {
	    	exhibitions = logHome.getExhibitions();
	    	presentations = logHome.getPresentations();
	    	items = logHome.getItems();	    	

	    } catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	    }
        request.setAttribute("someExhibitions", exhibitions);
        request.setAttribute("somePresentations", presentations);
        request.setAttribute("someItems", items);
        
	    request.getRequestDispatcher("/WEB-INF/home/index.jsp").forward(request, response);
	}
	
	
	
	
}
