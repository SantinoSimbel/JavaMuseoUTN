package logic;

import entities.Review;
import entities.Presentation;
import entities.Purchase;
import data.PurchaseDAO;
import data.ReviewDAO;

public class ReviewLogic {
	
	private PresentationLogic logPRe = new PresentationLogic();
	private PurchaseDAO purDao = new PurchaseDAO();
	private ReviewDAO dao = new ReviewDAO();
	
	public void addReview(Review rev, int idPre, int idUser) throws Exception{
		
		Presentation pre = new Presentation();
		pre.setId(idPre);
		pre = logPRe.getPresentation(pre);
		
		if (!"Terminado".equals(pre.getStatus())) {
    		throw new Exception("No es posible realizar reseñas a presentaciones no terminadas.");
		}
		
		Purchase pur = new Purchase();
		
		pur.setUserId(idUser);
		pur.setPresentationId(idPre);
		
		if (purDao.searchByUserAndPresentation(pur) == null) {
    		throw new Exception("No es posible realizar reseñas si no ha participado de la presentación.");
		}
		
		if (!(rev.getPunctuation() >= 0 && rev.getPunctuation() <= 5)) { //utilizamos esta logica por si llega con valor NaN
    		throw new Exception("El puntaje no se encuentra en el rango valido");
		}
		
		dao.add(rev, idPre, idUser);

		
	}
	
	
	
	
	public void updateReview(Review rev, int idPre, int idUser) throws Exception {

		if (!(rev.getPunctuation() >= 0 && rev.getPunctuation() <= 5)) { //utilizamos esta logica por si llega con valor NaN
			throw new Exception("El puntaje no se encuentra en el rango valido");
		}

		dao.update(rev, idPre, idUser);
	}


}
