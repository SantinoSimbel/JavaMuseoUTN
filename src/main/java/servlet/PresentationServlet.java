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
import data.PresentationDAO;
import entities.Item;
import entities.Presentation;






/**
 * Servlet implementation class PresentationServlet
 */
@WebServlet("/PresentationServlet")
public class PresentationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PresentationServlet() {
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
			getAllPresentation(request, response);
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
			addPresentation(request, response);
			break;
		case "update":
			updatePresentation(request, response);
			break;
		case "delete":
			deletePresentation(request, response);
			break;
		}
	}
	
	

	
	
	
	
	//GETALL
	public void getAllPresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PresentationDAO dao  = new PresentationDAO();
		LinkedList<Presentation> presentations = dao.list();
		
		request.setAttribute("allPresentations", presentations);
		request.getRequestDispatcher("/WEB-INF/presentation/list.jsp").forward(request,response);
	}
	
	
	
	//SHOWFORM
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Presentation pre;
		boolean editing;
		
		ItemDAO itemDAO = new ItemDAO();
		LinkedList<Item> items = itemDAO.list();
		
		String operation = request.getParameter("operation");
		
		if ("new".equals(operation)) {
			editing =  false;
			pre = new Presentation();
		} else {
			editing = true;
			int id = Integer.parseInt(request.getParameter("id"));
			
			Presentation e = new Presentation();
			e.setId(id);
			
			PresentationDAO dao  = new PresentationDAO();
			pre = dao.search(e);
			
		}
		
		request.setAttribute("onePresentation", pre);
		request.setAttribute("editing", editing);
		request.setAttribute("allItems", items);

		
		request.getRequestDispatcher("/WEB-INF/presentation/form.jsp").forward(request,response);
	}

	
	
	
	
	
	
	//ADD
	public void addPresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		Presentation newPre = new Presentation();
		
		newPre.setTitle(request.getParameter("title"));
		newPre.setDescription(request.getParameter("description"));
		newPre.setEndTime(LocalTime.parse(request.getParameter("endTime")));
		newPre.setStartTime(LocalTime.parse(request.getParameter("startTime")));
		
		newPre.setDay(LocalDate.parse(request.getParameter("day")));
		newPre.setCapacity(Integer.parseInt(request.getParameter("capacity")));
		
		String[] itemIds = request.getParameterValues("item_ids");
		
		if (itemIds == null || itemIds.length == 0) {
			request.setAttribute("error", "Debe seleccionar al menos un artículo.");
			showForm(request, response);
			return;
		}
		
		LinkedList<Item> selectedItems = new LinkedList<>();
		for (String itemId : itemIds) {
		    Item item = new Item();
		    item.setId(Integer.parseInt(itemId));
		    selectedItems.add(item);
		}
		newPre.setItems(selectedItems);
		
		
		EventDAO daoEv  = new EventDAO();
		daoEv.add(newPre);	
		PresentationDAO daoPre = new PresentationDAO();
		daoPre.add(newPre);
		
		
		response.sendRedirect("PresentationServlet?operation=list");
		
	}
	
	
	
	
	
	
	
	
	//UPDATE
	public void updatePresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		Presentation newPre = new Presentation();
		
		newPre.setId(Integer.parseInt(request.getParameter("id")));
		newPre.setTitle(request.getParameter("title"));
		newPre.setDescription(request.getParameter("description"));
		newPre.setEndTime(LocalTime.parse(request.getParameter("endTime")));
		newPre.setStartTime(LocalTime.parse(request.getParameter("startTime")));
		
		newPre.setDay(LocalDate.parse(request.getParameter("day")));
		newPre.setCapacity(Integer.parseInt(request.getParameter("capacity")));
		
		String[] itemIds = request.getParameterValues("item_ids");
		
		if (itemIds == null || itemIds.length == 0) {
			request.setAttribute("error", "Debe seleccionar al menos un artículo.");
			showForm(request, response);
			return;
		}
		
		LinkedList<Item> selectedItems = new LinkedList<>();
		for (String itemId : itemIds) {
		    Item item = new Item();
		    item.setId(Integer.parseInt(itemId));
		    selectedItems.add(item);
		}
		newPre.setItems(selectedItems);
		
		
		EventDAO daoEv  = new EventDAO();
		daoEv.update(newPre);	
		PresentationDAO daoPre = new PresentationDAO();
		daoPre.update(newPre);
		
		
		response.sendRedirect("PresentationServlet?operation=list");
	}
	
	
	
	
	
	
	
	
	//DELETE
	public void deletePresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Presentation delPre = new Presentation();
		delPre.setId(Integer.parseInt(request.getParameter("id")));
		
		EventDAO daoEv  = new EventDAO();
		PresentationDAO daoPre = new PresentationDAO();
		
		daoPre.delete(delPre);
		daoEv.delete(delPre);
		response.sendRedirect("PresentationServlet?operation=list");
	}





}
