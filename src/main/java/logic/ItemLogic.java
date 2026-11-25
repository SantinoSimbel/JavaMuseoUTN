package logic;

import data.ItemDAO;
import data.EventDAO;
import entities.Item;


public class ItemLogic {
private EventDAO evDao = new EventDAO();
private ItemDAO itemDao = new ItemDAO();

	public Item getItem(Item ite) throws Exception {
    	Item found = itemDao.search(ite);
    	
    	if (found == null) {
    		throw new Exception("No existe ningun item con ese id.");
    	}
    	return found;
	}


	public void deleteItem(Item ite) throws Exception {
	    if (evDao.existsByItem(ite)) {
	        throw new Exception("No se puede eliminar el item porque está siendo usada por uno o más eventos.");
	    }
	    itemDao.delete(ite);
	}
	
	
}