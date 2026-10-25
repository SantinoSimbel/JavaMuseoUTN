package logic;

import data.PresentationDAO;
import data.PurchaseDAO;
import data.UserDAO;
import entities.Presentation;
import entities.Purchase;
import entities.User;

public class PurchaseLogic {
	
	private PurchaseDAO purDao = new PurchaseDAO();
	private UserDAO userDao = new UserDAO();
	


	public void addPurchase(Purchase newPur)throws Exception {
		//validaciones (las otras se hacen en el dao)

	    if (newPur.getQuantity() <= 0) {
	        throw new Exception("La cantidad de entradas debe ser mayor que cero.");
	    }
	    //si es admin hacemos que no compre entradas
	    User user = new User();
	    user.setId(newPur.getUserId());
	    user = userDao.search(user);
	    
	    if ("admin".equals(user.getRole())) {
	        throw new Exception("Una cuenta de administrador no puede comprar entradas.");
	    }
	    purDao.addWithCapacityCheck(newPur);
	}
	

}