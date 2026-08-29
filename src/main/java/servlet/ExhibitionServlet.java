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
import entities.Item;
import entities.Exhibition;






/**
 * Servlet implementation class ExhibitionServlet
 */
@WebServlet("/ExhibitionServlet")
public class ExhibitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExhibitionServlet() {
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
			getAllExhibition(request, response);
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
			addExhibition(request, response);
			break;
		case "update":
			updateExhibition(request, response);
			break;
		case "delete":
			deleteExhibition(request, response);
			break;
		}
	}
	
	

	
	
	
	
	//GETALL
	public void getAllExhibition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ExhibitionDAO dao  = new ExhibitionDAO();
		LinkedList<Exhibition> exhibitions = dao.list();
		
		request.setAttribute("allExhibitions", exhibitions);
		request.getRequestDispatcher("/WEB-INF/exhibition/list.jsp").forward(request,response);
	}
	
	
	
	//SHOWFORM
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Exhibition ex;
		boolean editing;
		
		ItemDAO itemDAO = new ItemDAO();
		LinkedList<Item> items = itemDAO.list();
		
		String operation = request.getParameter("operation");
		
		if ("new".equals(operation)) {
			editing =  false;
			ex = new Exhibition();
		} else {
			editing = true;
			int id = Integer.parseInt(request.getParameter("id"));
			
			Exhibition e = new Exhibition();
			e.setId(id);
			
			ExhibitionDAO dao  = new ExhibitionDAO();
			ex = dao.search(e);
			
		}
		
		request.setAttribute("oneExhibition", ex);
		request.setAttribute("editing", editing);
		request.setAttribute("allItems", items);

		
		request.getRequestDispatcher("/WEB-INF/exhibition/form.jsp").forward(request,response);
	}

	
	
	
	
	
	
	//ADD
	public void addExhibition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		Exhibition newEx = new Exhibition();
		
		newEx.setTitle(request.getParameter("title"));
		newEx.setDescription(request.getParameter("description"));
		newEx.setEndTime(LocalTime.parse(request.getParameter("endTime")));
		newEx.setStartTime(LocalTime.parse(request.getParameter("startTime")));
		
		newEx.setEndDay(LocalDate.parse(request.getParameter("endDay")));
		newEx.setStartDay(LocalDate.parse(request.getParameter("startDay")));
		
		
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
		newEx.setItems(selectedItems);
		
		
		EventDAO daoEv  = new EventDAO();
		daoEv.add(newEx);	
		ExhibitionDAO daoEx = new ExhibitionDAO();
		daoEx.add(newEx);
		
		
		response.sendRedirect("ExhibitionServlet?operation=list");
		
	}
	
	
	
	
	
	
	
	
	//UPDATE
	public void updateExhibition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		Exhibition newEx = new Exhibition();
		
		newEx.setId(Integer.parseInt(request.getParameter("id")));
		newEx.setTitle(request.getParameter("title"));
		newEx.setDescription(request.getParameter("description"));
		newEx.setEndTime(LocalTime.parse(request.getParameter("endTime")));
		newEx.setStartTime(LocalTime.parse(request.getParameter("startTime")));
		
		newEx.setEndDay(LocalDate.parse(request.getParameter("endDay")));
		newEx.setStartDay(LocalDate.parse(request.getParameter("startDay")));

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
		newEx.setItems(selectedItems);
		
		
		EventDAO daoEv  = new EventDAO();
		daoEv.update(newEx);	
		ExhibitionDAO daoEx = new ExhibitionDAO();
		daoEx.update(newEx);
		
		
		response.sendRedirect("ExhibitionServlet?operation=list");
	}
	
	
	
	
	
	
	
	
	//DELETE
	public void deleteExhibition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Exhibition delEx = new Exhibition();
		delEx.setId(Integer.parseInt(request.getParameter("id")));
		
		EventDAO daoEv  = new EventDAO();
		ExhibitionDAO daoEx = new ExhibitionDAO();
		
		daoEx.delete(delEx);
		daoEv.delete(delEx);
		response.sendRedirect("ExhibitionServlet?operation=list");
	}





}
