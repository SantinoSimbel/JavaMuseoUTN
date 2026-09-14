package logic;

import data.EventDAO;
import data.ExhibitionDAO;
import entities.Exhibition;
import java.time.*;
import java.util.LinkedList;

public class ExhibitionLogic {
	private ExhibitionDAO dao = new ExhibitionDAO();
	private EventDAO daoEv = new EventDAO();
	
	public LinkedList<Exhibition> getExhibitions(){
		LinkedList<Exhibition> exhibitions = dao.list();
		for (Exhibition ex : exhibitions) {
			ex.updateStatus();
		}
		return exhibitions;
	}
	
	public void registerExhibition(Exhibition newExhibition) throws Exception{
		
		//validaciones
		if (newExhibition.getEndDay().isBefore(newExhibition.getStartDay())){
			throw new Exception("La fecha de fin no puede ser menor a la de inicio.");
		}
		
		if (newExhibition.getEndDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().isBefore(LocalDate.now())){
			throw new Exception("Las fechas no pueden ser menor al día de hoy.");
		}
		
		daoEv.add(newExhibition);
		dao.add(newExhibition);
	}
	
	public void updateExhibition(Exhibition newExhibition) throws Exception{
		
		//validaciones
		if (newExhibition.getEndDay().isBefore(newExhibition.getStartDay())){
			throw new Exception("La fecha de fin no puede ser menor a la de inicio.");
		}
				
		if (newExhibition.getEndDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().isBefore(LocalDate.now())){
			throw new Exception("Las fechas no pueden ser menor al día de hoy.");
		}
				
		daoEv.update(newExhibition);				
		dao.update(newExhibition);
	}
	
	

}
