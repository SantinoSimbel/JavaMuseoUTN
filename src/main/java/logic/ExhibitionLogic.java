package logic;


import data.ExhibitionDAO;
import entities.Exhibition;
import java.time.*;
import java.util.LinkedList;
import java.util.Comparator;


public class ExhibitionLogic extends EventLogic{
	private ExhibitionDAO dao = new ExhibitionDAO();
	
	
	
	
	//get exhibitions
	public LinkedList<Exhibition> getExhibitions(String status, String operation){
		LinkedList<Exhibition> exhibitions = dao.list();
		
		for (Exhibition ex : exhibitions) {
			ex.updateStatus();
			daoEv.update(ex);
		}

		
		LinkedList<Exhibition> filtered = new LinkedList<>();
		
		if (status == null || status.isEmpty()) {
			
			if ("userList".equals(operation)) {
				for (Exhibition ex  : exhibitions) {
					if (!ex.getStatus().equals("Terminado")) {
						filtered.add(ex);
					} 	
				}				
			}
			else { filtered = exhibitions; }
			
			filtered.sort(Comparator.<Exhibition>comparingInt(e -> getPriority(e)).thenComparing(Exhibition::getStartDay));
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
		if (newExhibition.getEndDay().isBefore(newExhibition.getStartDay())){
			throw new Exception("La fecha de fin no puede ser menor a la de inicio.");
		}
				
		if (newExhibition.getEndDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().equals(LocalDate.now())){
			throw new Exception("Las fechas deben ser superiores al día de hoy.");
		}
		
		
		super.validationEvent(newExhibition);
		daoEv.update(newExhibition);				
		dao.update(newExhibition);
	}
	
	
	
	
	
	


}
