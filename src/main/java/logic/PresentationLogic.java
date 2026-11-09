package logic;

import data.PresentationDAO;
import data.PurchaseDAO;
import entities.Presentation;
import java.time.*;
import java.util.Comparator;
import java.util.LinkedList;

public class PresentationLogic extends EventLogic {

	
	private PresentationDAO dao = new PresentationDAO();
	private PurchaseDAO purDao = new PurchaseDAO();
	
	
	//get presentations
	public LinkedList<Presentation> getPresentations(String status, String operation){
		LinkedList<Presentation> presentations = dao.list();
		for (Presentation pre : presentations) {
			pre.updateStatus();
			daoEv.update(pre);
		}		
		LinkedList<Presentation> filtered = new LinkedList<>();

		
		if (status == null || status.isEmpty()) {
			
			if ("userList".equals(operation)) {
				for (Presentation ex  : presentations) {
					if (!ex.getStatus().equals("Terminado")) {
						filtered.add(ex);
					} 	
				}				
			}
			else { filtered = presentations; }
			
			filtered.sort(Comparator.<Presentation>comparingInt(p -> getPriority(p)).thenComparing(Presentation::getDay));
		} else {
			for (Presentation ex  : presentations) {
				if (ex.getStatus().equals(status)) {
					filtered.add(ex);
				} 	
			}
			filtered.sort(Comparator.comparing(Presentation::getDay));
		} 
		
		return filtered;
	}
	

	
	
	
	
	//add presentation
	public void addPresentation(Presentation newPresentation) throws Exception{
		
		//validaciones
		if (newPresentation.getDay().isBefore(LocalDate.now()) || newPresentation.getDay().equals(LocalDate.now())){
			throw new Exception("La fecha debe ser superior al día de hoy.");
		}
		if (newPresentation.getCapacity() <= 0){
			throw new Exception("Los cupos no pueden ser menor o igual a 0.");
		}
		if (newPresentation.getPrice().intValue() <= 0.00){
			throw new Exception("El precio no puede ser menor o igual a 0.");
		}
		
		super.validationEvent(newPresentation);
		daoEv.add(newPresentation);
		dao.add(newPresentation);
	}
	
	
	
	
	
	//update presentation
	public void updatePresentation(Presentation newPresentation) throws Exception{

		
		//validaciones
		if (newPresentation.getDay().isBefore(LocalDate.now()) || newPresentation.getDay().equals(LocalDate.now())){
			throw new Exception("La fecha debe ser superior al día de hoy.");
		}
		if (newPresentation.getCapacity() <= 0){
			throw new Exception("Los cupos no pueden ser menor o igual a 0.");
		}
		if (newPresentation.getPrice().intValue() <= 0.00){
			throw new Exception("El precio no puede ser menor o igual a 0.");
		}
		
		super.validationEvent(newPresentation);
		daoEv.update(newPresentation);		
		dao.update(newPresentation);
	}
	
	
	
	
	
	//delete presentations
	public void deletePresentation(Presentation delPre) throws Exception {
		//si la presentacion tiene compras no borrar
		
		if (!purDao.searchByPresentation(delPre).isEmpty()){
			throw new Exception("No puede borrarse la presentacion porque tiene compras asociadas.");
		}
		//si no tiene, borrar
		dao.delete(delPre);
		daoEv.delete(delPre);
	}
	
	

	
}
