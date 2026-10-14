package logic;

import data.PurchaseDAO;
import entities.Purchase;

public class PurchaseLogic {
	private PurchaseDAO dao = new PurchaseDAO();

	public void addPurchase(Purchase newPur)throws Exception {
		if (false) {
			throw new Exception("error...");
		}
		dao.add(newPur);
		
	}

}
