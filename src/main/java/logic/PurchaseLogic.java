package logic;

import data.PresentationDAO;
import data.PurchaseDAO;
import entities.Presentation;
import entities.Purchase;

public class PurchaseLogic {
	
	private PurchaseDAO dao = new PurchaseDAO();
	


	public void addPurchase(Purchase newPur)throws Exception {
		//validaciones

	    if (newPur.getQuantity() <= 0) {
	        throw new Exception(
	            "La cantidad de entradas debe ser mayor que cero."
	        );
	    }
	    
	    dao.addWithCapacityCheck(newPur);
	    

		//si es actualizar... logica luego de eso
	}
	

}
