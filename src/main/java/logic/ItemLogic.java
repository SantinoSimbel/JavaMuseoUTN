package logic;

import data.ItemDAO;
import data.EventDAO;
import entities.Item;


public class ItemLogic {
private EventDAO evDao = new EventDAO();
private ItemDAO itemDao = new ItemDAO();

	public void deleteItem(Item ite) throws Exception {
	    if (evDao.existsByItem(ite)) {
	        throw new Exception("No se puede eliminar el item porque está siendo usada por uno o más eventos.");
	    }
	    itemDao.delete(ite);
	}
	
	
}
