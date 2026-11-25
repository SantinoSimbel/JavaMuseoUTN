package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.time.*;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.ItemDAO;
import data.PurchaseDAO;
import data.ReviewDAO;
import logic.ReviewLogic;
import entities.Item;
import entities.Presentation;
import entities.Purchase;
import entities.Review;
import entities.UserSessionDTO;
import logic.PresentationLogic;






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
		case "userList":
			getAllPresentation(request, response, operation);
			break;
		case "list":
			getAllPresentation(request, response, operation);
			break;
		case "detail":
			getOnePresentation(request, response);
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
	public void getAllPresentation(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException {
		
		PresentationLogic logicPre = new PresentationLogic();
		LinkedList<Presentation> presentations = new LinkedList<Presentation>();
		
		try {
			presentations = logicPre.getPresentations(request.getParameter("status"), view);

		} catch(Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
		}
		
		request.setAttribute("allPresentations", presentations);
		
		String jsp = "list".equals(view)
				?  "/WEB-INF/presentation/list.jsp"
			    :  "/WEB-INF/presentation/userList.jsp";
		
		request.getRequestDispatcher(jsp).forward(request,response);
	}
	
	
	
	
	
	
	//GETONE
	public void getOnePresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PresentationLogic preLog = new PresentationLogic();
		Presentation pre = new Presentation();
		Purchase pur = null;
		Review rev = null;
				

		try {
			pre.setId(Integer.parseInt(request.getParameter("id")));
			pre = preLog.getPresentation(pre);
			
			//para poder mostrar los datos de la compra en el modal:
			HttpSession session = request.getSession(false);
			UserSessionDTO userDTO = null;
			
			if (session != null) {
			    userDTO = (UserSessionDTO) session.getAttribute("user");
			}
			
			
			if (userDTO != null) {
				
				PurchaseDAO purDAO = new PurchaseDAO();
				pur = new Purchase();
				pur.setUserId(userDTO.getId());
				pur.setPresentationId(pre.getId());
				pur = purDAO.searchByUserAndPresentation(pur);
				
				
				ReviewDAO revDAO = new ReviewDAO();
				rev = new Review();
				rev = revDAO.search(pre.getId(), userDTO.getId());
			}
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id de la presentación no es válido.");
			getAllPresentation(request, response, "userList");
			return;
			
		} catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	        getAllPresentation(request, response, "userList");
	        return;
	    }
				
		
		request.setAttribute("onePresentation", pre);
		request.setAttribute("onePurchase", pur);
		request.setAttribute("oneReview", rev);
		request.getRequestDispatcher("/WEB-INF/presentation/detail.jsp").forward(request,response);

	}
	
	
	
	
	//SHOWFORM
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Presentation pre = new Presentation();
		boolean editing =  false;
		
		ItemDAO itemDAO = new ItemDAO();
		LinkedList<Item> items = new LinkedList<Item>();
		
		String operation = request.getParameter("operation");
		
		try {
			
			items = itemDAO.list();
			
			if ("edit".equals(operation)) {
				editing = true;
				int id = Integer.parseInt(request.getParameter("id"));
				
				pre.setId(id);
				PresentationLogic logic  = new PresentationLogic();
				pre = logic.getPresentation(pre);
				logic.canUpdate(pre);

			}
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id de la presentación no es válido.");
			getAllPresentation(request, response, "list");
			return;
			
		} catch (Exception e) {
	        request.setAttribute("errorMessage", e.getMessage());
	        getAllPresentation(request, response, "list");
	        return;
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
			newPre.setItems(selectedItems);
		
			newPre.setEndTime(LocalTime.parse(request.getParameter("endTime")));
			newPre.setStartTime(LocalTime.parse(request.getParameter("startTime")));
			newPre.setDay(LocalDate.parse(request.getParameter("day")));
			newPre.setCapacity(Integer.parseInt(request.getParameter("capacity")));
			newPre.setPrice(new BigDecimal(request.getParameter("price")));
		

			PresentationLogic logicPre = new PresentationLogic();
			logicPre.addPresentation(newPre);
			
		} catch (Exception e) {
			
			try {
				ItemDAO itemDAO = new ItemDAO();
				selectedItems = itemDAO.list();
			} catch (Exception e2) {}
			
			String msg;
		    if (e instanceof NumberFormatException) {
		        msg = "Se ha detectado un valor numérico no válido.";
		    } else if (e instanceof DateTimeParseException) {
		        msg = "La fecha o la hora no son válidas.";
		    } else {
		        msg = e.getMessage();
		    }
			
		    request.setAttribute("allItems", selectedItems);
			request.setAttribute("errorMessage", msg);
			request.setAttribute("onePresentation", newPre); 
			request.setAttribute("editing", false);
			request.getRequestDispatcher("/WEB-INF/presentation/form.jsp").forward(request,response);
			return;
		}
		
		response.sendRedirect("PresentationServlet?operation=list");
	}
	
	
		
		

	
	
	
	
	//UPDATE
	public void updatePresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		Presentation newPre = new Presentation();
		String[] itemIds = request.getParameterValues("item_ids");
		LinkedList<Item> selectedItems = new LinkedList<>();
		
		newPre.setTitle(request.getParameter("title"));
		newPre.setDescription(request.getParameter("description"));
		
		
		
		try {

			newPre.setEndTime(LocalTime.parse(request.getParameter("endTime")));
			newPre.setStartTime(LocalTime.parse(request.getParameter("startTime")));
			newPre.setDay(LocalDate.parse(request.getParameter("day")));
			
			newPre.setId(Integer.parseInt(request.getParameter("id")));
			newPre.setCapacity(Integer.parseInt(request.getParameter("capacity")));
			newPre.setPrice(new BigDecimal(request.getParameter("price")));
		

			if (itemIds != null) {
				for (String itemId : itemIds) {
					Item item = new Item();
					item.setId(Integer.parseInt(itemId));
					selectedItems.add(item);
				}
			}
			
			newPre.setItems(selectedItems);
		
		
			PresentationLogic logicPre = new PresentationLogic();
			logicPre.updatePresentation(newPre);
			
		} catch (Exception e) {
			
			try {
				ItemDAO itemDAO = new ItemDAO();
				selectedItems = itemDAO.list();
			} catch (Exception e2) {}
			
			String msg;
		    if (e instanceof NumberFormatException) {
		    	msg = "Se ha detectado un valor numérico no válido.";
		    } else if (e instanceof DateTimeParseException) {
		        msg = "La fecha o la hora no son válidas.";
		    } else {
		        msg = e.getMessage();
		    }
		    
		    request.setAttribute("allItems", selectedItems);
			request.setAttribute("errorMessage", msg);
			request.setAttribute("onePresentation", newPre); 
			request.setAttribute("editing", true);
			request.getRequestDispatcher("/WEB-INF/presentation/form.jsp").forward(request,response);
			return;
		}
		
		response.sendRedirect("PresentationServlet?operation=list");
	}
	
	
	

	
	
	
	
	//DELETE
	public void deletePresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Presentation delPre = new Presentation();
		PresentationLogic logicPre = new PresentationLogic();
		
		try {
			delPre.setId(Integer.parseInt(request.getParameter("id")));

			logicPre.deletePresentation(delPre);
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "El id de la presentación no es válido.");
			getAllPresentation(request, response, "list");
			return;
			
		} catch (Exception e) {
			
			request.setAttribute("errorMessage", e.getMessage());
			getAllPresentation(request, response, "list");
			return;
		}
		
		response.sendRedirect("PresentationServlet?operation=list");

		
	}





}
