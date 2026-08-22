package servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.time.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import data.ItemDAO;
import data.EventDAO;
import data.ExhibitionDAO;
import data.PresentationDAO;
import entities.Item;
import entities.Presentation;
import entities.Exhibition;
import entities.Event;






/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventServlet() {
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
			getAllEvent(request, response);
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
			addEvent(request, response);
			break;
		case "update":
			updateEvent(request, response);
			break;
		case "delete":
			deleteEvent(request, response);
			break;
		}
	}
	
	

	
	
	
	
	//GETALL
	public void getAllEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EventDAO dao  = new EventDAO();
		LinkedList<Event> events = dao.list();
		
		request.setAttribute("allEvents", events);;
		request.getRequestDispatcher("/WEB-INF/event/list.jsp").forward(request,response);
	}
	
	
	
	//SHOWFORM
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Event eve;
		boolean editing;
		String currentType = "";
		
		ItemDAO itemDAO = new ItemDAO();
		LinkedList<Item> items = itemDAO.list();
		
		String operation = request.getParameter("operation");
		String eveType = request.getParameter("eveType");
		
		if ("new".equals(operation)) {
			editing =  false;
			if ("presentation".equals(eveType)) {
				eve = new Presentation();
				currentType = "presentation";
			} 
			else {
				eve = new Exhibition();
				currentType = "exhibition";
			}
					
			
			
		} else {
			editing = true;
			int id = Integer.parseInt(request.getParameter("id"));
			
			Event e = new Event();
			e.setId(id);
			
			EventDAO dao  = new EventDAO();
			eve = dao.search(e);
			
			if (eve instanceof Presentation) {
	            currentType = "presentation";
	        } else if (eve instanceof Exhibition) {
	            currentType = "exhibition";
	        }
		}
		
		request.setAttribute("oneEvent", eve);
		request.setAttribute("currentType", currentType);
		request.setAttribute("editing", editing);
		request.setAttribute("allItems", items);

		
		request.getRequestDispatcher("/WEB-INF/event/form.jsp").forward(request,response);
	}

	
	
	
	
	
	
	//ADD
	public void addEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String eveType = request.getParameter("eveType");
		
		Event newEve = new Event();
		
		newEve.setTitle(request.getParameter("title"));
		newEve.setDescription(request.getParameter("description"));
		newEve.setEndTime(LocalTime.parse(request.getParameter("endTime")));
		newEve.setStartTime(LocalTime.parse(request.getParameter("startTime")));
		
		Item ite = new Item();
		ite.setId(Integer.parseInt(request.getParameter("item_id")));
	    
	    newEve.setItem(ite);
		
		
		EventDAO dao  = new EventDAO();
		dao.add(newEve);
		
		
		
		if ("presentation".equals(eveType)) {
			Presentation newPre = new Presentation();
			newPre.setDay(LocalDate.parse(request.getParameter("day")));
			newPre.setCapacity(Integer.parseInt(request.getParameter("capacity")));
			newPre.setId(newEve.getId());
			
			PresentationDAO daoP = new PresentationDAO();
			daoP.add(newPre);
		} 
		else {
			Exhibition newExi = new Exhibition();
			newExi.setEndDay(LocalDate.parse(request.getParameter("endDay")));
			newExi.setStartDay(LocalDate.parse(request.getParameter("startDay")));
			newExi.setId(newEve.getId());
			
			ExhibitionDAO daoEx = new ExhibitionDAO();
			daoEx.add(newExi);
		}
		
		
		
		
		response.sendRedirect("EventServlet?operation=list");
		
	}
	
	
	
	
	
	
	
	
	//UPDATE
	public void updateEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String eveType = request.getParameter("eveType");
		
		Event newEve = new Event();
		
		
		newEve.setId(Integer.parseInt(request.getParameter("id")));
		newEve.setTitle(request.getParameter("title"));
		newEve.setDescription(request.getParameter("description"));
		newEve.setEndTime(LocalTime.parse(request.getParameter("endTime")));
		newEve.setStartTime(LocalTime.parse(request.getParameter("startTime")));
		
		
		Item ite = new Item();
		ite.setId(Integer.parseInt(request.getParameter("item_id")));
	    newEve.setItem(ite);
		
	    
		EventDAO dao  = new EventDAO();
		dao.update(newEve);
		
		if ("presentation".equals(eveType)) {
			Presentation newPre = new Presentation();
			newPre.setDay(LocalDate.parse(request.getParameter("day")));
			newPre.setCapacity(Integer.parseInt(request.getParameter("capacity")));
			newPre.setId(Integer.parseInt(request.getParameter("id")));
			
			PresentationDAO daoP = new PresentationDAO();
			daoP.update(newPre);
		} 
		else {
			Exhibition newExi = new Exhibition();
			newExi.setEndDay(LocalDate.parse(request.getParameter("endDay")));
			newExi.setStartDay(LocalDate.parse(request.getParameter("startDay")));
			newExi.setId(Integer.parseInt(request.getParameter("id")));
			
			ExhibitionDAO daoEx = new ExhibitionDAO();
			daoEx.update(newExi);
		}
		
		
		
		
		response.sendRedirect("EventServlet?operation=list");
	}
	
	
	
	
	
	
	
	
	//DELETE
	public void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Event delEve = new Event();
		delEve.setId(Integer.parseInt(request.getParameter("id")));
		
		EventDAO dao  = new EventDAO();
		PresentationDAO daoP = new PresentationDAO();
		ExhibitionDAO daoEx = new ExhibitionDAO();
		
		daoP.delete(delEve);
		daoEx.delete(delEve);
		dao.delete(delEve);
		response.sendRedirect("EventServlet?operation=list");
	}





}
