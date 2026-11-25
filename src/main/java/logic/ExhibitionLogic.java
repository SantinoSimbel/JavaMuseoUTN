package logic;


import data.ExhibitionDAO;
import entities.Exhibition;
import entities.Item;
import entities.Presentation;

import java.time.*;
import java.util.LinkedList;
import java.util.Comparator;


public class ExhibitionLogic extends EventLogic{
	private ExhibitionDAO dao = new ExhibitionDAO();
	
	
	
	
	//get exhibitions
	public LinkedList<Exhibition> getExhibitions(String status, String operation) throws Exception {
		LinkedList<Exhibition> exhibitions = dao.list();
		
		for (Exhibition ex : exhibitions) {
			ex.updateStatus();
			daoEv.update(ex);
		}

		
		LinkedList<Exhibition> filtered = new LinkedList<>();
		
		if (status == null || status.isEmpty()) {
			
			exhibitions.sort(Comparator.<Exhibition>comparingInt(e -> getPriority(e, operation)).thenComparing(Exhibition::getStartDay));
			
			filtered = exhibitions; 
		} else {
			for (Exhibition ex  : exhibitions) {
				if (ex.getStatus().equals(status)) {
					filtered.add(ex);
				} 	
			}
			filtered.sort(Comparator.comparing(Exhibition::getStartDay));
		} 
		return filtered;
	}
	
	
	
	//get one
	public Exhibition getExhibition(Exhibition ex) throws Exception {
		Exhibition found = dao.search(ex);
    	
    	if (found == null) {
    		throw new Exception("No existe ninguna exhibición con ese id.");
    	}
    	
    	found.updateStatus();
    	return found;
	}

	
	
	//add exhibition 
	public void addExhibition(Exhibition newExhibition) throws Exception{
		
		//validaciones
		if (newExhibition.getEndDay().isBefore(newExhibition.getStartDay())){
			throw new Exception("La fecha de fin no puede ser menor a la de inicio.");
		}
		
		if (newExhibition.getEndDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().equals(LocalDate.now())){
			throw new Exception("Las fechas deben ser superiores al día de hoy.");
		}
		
		super.validationEvent(newExhibition);
		daoEv.add(newExhibition);
		dao.add(newExhibition);
	}
	
	
	
	
	
	//update exhibition
	public void updateExhibition(Exhibition newExhibition) throws Exception{
		
		//validaciones
		canUpdate(getExhibition(newExhibition));
		validationEvent(newExhibition);
		
		if (newExhibition.getEndDay().isBefore(newExhibition.getStartDay())){
			throw new Exception("La fecha de fin no puede ser menor a la de inicio.");
		}
				
		if (newExhibition.getEndDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().equals(LocalDate.now())){
			throw new Exception("Las fechas deben ser superiores al día de hoy.");
		}
		
		daoEv.update(newExhibition);				
		dao.update(newExhibition);
	}
	
	
	
	//delete exhibition
	public void deleteExhibition(Exhibition delEx) throws Exception {

		//comprobamos si existe
		Exhibition exist = getExhibition(delEx);
		
		canDelete(exist);
		
		//si no tiene, borrar
		dao.delete(exist);
		daoEv.delete(exist);
	}
	
	


}
