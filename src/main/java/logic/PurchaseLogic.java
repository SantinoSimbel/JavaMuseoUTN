package logic;

import data.PresentationDAO;
import data.PurchaseDAO;
import entities.Presentation;
import entities.Purchase;

public class PurchaseLogic {
	
	private PurchaseDAO PurDao = new PurchaseDAO();
	private PresentationDAO preDao = new PresentationDAO();
	


	public void addPurchase(Purchase newPur)throws Exception {
		//validaciones
		//existe la presentacion?
		Presentation p = new Presentation();
		p.setId(newPur.getPresentationId());
		
		Presentation actualPre = preDao.search(p);
		//si esto ya lo checkeo en el dao, lo borro?
		if (actualPre == null) {
			throw new Exception("El id de La presentacion no existe.");
		}
		//solamente dejar esta?
	    if (newPur.getQuantity() <= 0) {
	        throw new Exception(
	            "La cantidad de entradas debe ser mayor que cero."
	        );
	    }
		//hay cupo disponible (tambien lo reviso en el dao antes de insertar?
		if (!hasSpace(newPur,actualPre)) {
			throw new Exception("No queda cupo para esa cantidad de entradas.");
		}
		//actualizar el precio de purchase
		
		//si es actualizar... logica luego de eso
	}
	
	private boolean hasSpace(Purchase newpur, Presentation actualPre) {
		if (true) {
			return true;
		} else {
			return false;
		}

	}
}
