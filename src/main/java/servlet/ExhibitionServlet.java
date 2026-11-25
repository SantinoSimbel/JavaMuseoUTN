package servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.time.*;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import data.ItemDAO;
import entities.Item;
import entities.Exhibition;
import logic.ExhibitionLogic;





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
		case "userList":
		    getAllExhibition(request, response, operation);
		    break;
		case "list":
			getAllExhibition(request, response, operation);
			break;
		case "detail":
			getOneExhibition(request, response);
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
	public void getAllExhibition(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException {
		
		ExhibitionLogic logicEx = new ExhibitionLogic();
		LinkedList<Exhibition> exhibitions = new LinkedList<Exhibition>();
				
		try {
			exhibitions = logicEx.getExhibitions(request.getParameter("status"), view);

		} catch(Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
		}
		
		request.setAttribute("allExhibitions", exhibitions);
		
	    String jsp = "list".equals(view)
	            ? "/WEB-INF/exhibition/list.jsp"
	            : "/WEB-INF/exhibition/userList.jsp";

	    request.getRequestDispatcher(jsp).forward(request, response);
	}
	
	
	
	
	
	//GETONE
	public void getOneExhibition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ExhibitionLogic exLogic = new ExhibitionLogic();
		
		Exhibition ex = new Exhibition();
		

		try {
			ex.setId(Integer.parseInt(request.getParameter("id")));
			ex = exLogic.getExhibition(ex);
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id de la exhibicion no es válido.");
			getAllExhibition(request, response, "userList");
			return;
			
		} catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	        getAllExhibition(request, response, "userList");
	        return;
	    }
		
		
		
		request.setAttribute("oneExhibition", ex);
		request.getRequestDispatcher("/WEB-INF/exhibition/detail.jsp").forward(request,response);

	}
	
	
	
	//SHOWFORM
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Exhibition ex = new Exhibition();
		boolean editing =  false;
		
		ItemDAO itemDAO = new ItemDAO();
		LinkedList<Item> items = new LinkedList<Item>();
		
		String operation = request.getParameter("operation");

				
		try {
				items = itemDAO.list();

				if ("edit".equals(operation)) {
					editing = true;
					int id = Integer.parseInt(request.getParameter("id"));
			
					ex.setId(id);
			
					ExhibitionLogic logic  = new ExhibitionLogic();
					ex = logic.getExhibition(ex);
					logic.canUpdate(ex);
			
				}
				
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id de la exhibición no es válido.");
			getAllExhibition(request, response, "list");
			return;
			
		} catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	        getAllExhibition(request, response, "list");
	        return;
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
		
		String[] itemIds = request.getParameterValues("item_ids");
		LinkedList<Item> selectedItems = new LinkedList<>();

		
		try {

			if (itemIds != null) {
				for (String itemId : itemIds) {
					Item item = new Item();
					item.setId(Integer.parseInt(itemId));
					selectedItems.add(item);
				}
			}
			newEx.setItems(selectedItems);
			
			newEx.setEndTime(LocalTime.parse(request.getParameter("endTime")));
			newEx.setStartTime(LocalTime.parse(request.getParameter("startTime")));
			newEx.setEndDay(LocalDate.parse(request.getParameter("endDay")));
			newEx.setStartDay(LocalDate.parse(request.getParameter("startDay")));
		
			ExhibitionLogic logicEx = new ExhibitionLogic();

			logicEx.addExhibition(newEx);
			
		} catch (Exception e) {
			
			try {
				ItemDAO itemDAO = new ItemDAO();
				selectedItems = itemDAO.list();
			} catch (Exception e2) {}
			
			String msg;
		    if (e instanceof NumberFormatException) {
		        msg = "Se ha detectado un id no válido.";
		    } else if (e instanceof DateTimeParseException) {
		        msg = "La fecha o la hora no son válidas.";
		    } else {
		        msg = e.getMessage();
		    }
		    
		    request.setAttribute("errorMessage", msg);			
		    request.setAttribute("allItems", selectedItems);
			request.setAttribute("oneExhibition", newEx); 
			request.setAttribute("editing", false);
			request.getRequestDispatcher("/WEB-INF/exhibition/form.jsp").forward(request,response);
			return;
		}
		
		
		response.sendRedirect("ExhibitionServlet?operation=list");
	}
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	//UPDATE
	public void updateExhibition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Exhibition updEx = new Exhibition();
		
		updEx.setTitle(request.getParameter("title"));
		updEx.setDescription(request.getParameter("description"));
		
		String[] itemIds = request.getParameterValues("item_ids");
		LinkedList<Item> selectedItems = new LinkedList<>();

		
		try {
			updEx.setId(Integer.parseInt(request.getParameter("id")));
			
			updEx.setEndTime(LocalTime.parse(request.getParameter("endTime")));
			updEx.setStartTime(LocalTime.parse(request.getParameter("startTime")));
		
			updEx.setEndDay(LocalDate.parse(request.getParameter("endDay")));
			updEx.setStartDay(LocalDate.parse(request.getParameter("startDay")));
		

			if (itemIds != null) {
				for (String itemId : itemIds) {
					Item item = new Item();
					item.setId(Integer.parseInt(itemId));
					selectedItems.add(item);
				}
			}
			updEx.setItems(selectedItems);
		
			ExhibitionLogic logicEx = new ExhibitionLogic();

			logicEx.updateExhibition(updEx);
			
		} catch (Exception e) {
			
			try {
				ItemDAO itemDAO = new ItemDAO();
				selectedItems = itemDAO.list();
			} catch (Exception e2) {}
			
			String msg;
		    if (e instanceof NumberFormatException) {
		        msg = "Se ha detectado un id no válido.";
		    } else if (e instanceof DateTimeParseException) {
		        msg = "La fecha o la hora no son válidas.";
		    } else {
		        msg = e.getMessage();
		    }
		    
		    request.setAttribute("errorMessage", msg);			
		    request.setAttribute("allItems", selectedItems);
		    request.setAttribute("oneExhibition", updEx); 
			request.setAttribute("editing", true);
			request.getRequestDispatcher("/WEB-INF/exhibition/form.jsp").forward(request,response);
			return;
		}
		
		
		response.sendRedirect("ExhibitionServlet?operation=list");
	}
	
	
		


	
	
	
	
	
	
	
	//DELETE
	public void deleteExhibition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Exhibition delEx = new Exhibition();
		ExhibitionLogic exLog = new ExhibitionLogic();		
		
		try {
			delEx.setId(Integer.parseInt(request.getParameter("id")));
			
			exLog.deleteExhibition(delEx);
			
		
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id de la exhibición no es válido.");
			getAllExhibition(request, response, "list");
			return;
			
		} catch (Exception e) {
			
			request.setAttribute("errorMessage", e.getMessage());
			getAllExhibition(request, response, "list");
			return;
		}
		
		
		response.sendRedirect("ExhibitionServlet?operation=list");
	}





}
