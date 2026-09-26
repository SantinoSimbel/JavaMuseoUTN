package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.CategoryDAO;
import data.ItemDAO;
import entities.Category;
import entities.Item;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/ItemServlet")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet() {
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
			getAllItem(request, response);
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
			addItem(request, response);
			break;
		case "update":
			updateItem(request, response);
			break;
		case "delete":
			deleteItem(request, response);
			break;
		}
	}
	
	
	
	
	
	//GETALL
	public void getAllItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ItemDAO dao  = new ItemDAO();
		LinkedList<Item> items = dao.list();
		
		request.setAttribute("allItems", items);
		request.getRequestDispatcher("/WEB-INF/item/list.jsp").forward(request,response);
	}
	
	
	
	//SHOWFORM
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item ite;
		boolean editing;
		CategoryDAO categoryDAO = new CategoryDAO();
		LinkedList<Category> categories = categoryDAO.list();
		
		String operation = request.getParameter("operation");
		
		if ("new".equals(operation)) {
			ite = new Item();
			editing =  false;
		} else {
			editing = true;
			int id = Integer.parseInt(request.getParameter("id"));
			
			Item i = new Item();
			i.setId(id);
			
			ItemDAO dao  = new ItemDAO();
			ite = dao.search(i);
		}
		
		request.setAttribute("oneItem", ite);
		request.setAttribute("editing", editing);
		request.setAttribute("allCategories", categories);

		
		request.getRequestDispatcher("/WEB-INF/item/form.jsp").forward(request,response);
	}

	
	
	
	
	
	
	//ADD
	public void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item newIte = new Item();
		newIte.setName(request.getParameter("name"));
		newIte.setDescription(request.getParameter("description"));
		newIte.setPicture(request.getParameter("picture"));
		
		Category cat = new Category();
	    cat.setId(Integer.parseInt(request.getParameter("category_id")));
	    
	    newIte.setCategory(cat);
		
		
		ItemDAO dao  = new ItemDAO();
		dao.add(newIte);
		response.sendRedirect("ItemServlet?operation=list");
		
	}
	
	
	
	
	
	
	
	
	//UPDATE
	public void updateItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item newIte = new Item();
		newIte.setId(Integer.parseInt(request.getParameter("id")));
		newIte.setName(request.getParameter("name"));
		newIte.setDescription(request.getParameter("description"));
		newIte.setPicture(request.getParameter("picture"));
		
		
		Category cat = new Category();
		cat.setId(Integer.parseInt(request.getParameter("category_id")));
	    newIte.setCategory(cat);
		
	    
		ItemDAO dao  = new ItemDAO();
		dao.update(newIte);
		response.sendRedirect("ItemServlet?operation=list");
	}
	
	
	
	
	
	
	
	
	//DELETE
	public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item delIte = new Item();
		delIte.setId(Integer.parseInt(request.getParameter("id")));
		
		ItemDAO dao  = new ItemDAO();
		dao.delete(delIte);
		response.sendRedirect("ItemServlet?operation=list");
	}



}
