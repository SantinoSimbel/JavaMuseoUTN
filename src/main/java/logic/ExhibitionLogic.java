package logic;

import data.EventDAO;
import data.ExhibitionDAO;
import entities.Exhibition;
import java.time.*;

public class ExhibitionLogic {
	private ExhibitionDAO dao = new ExhibitionDAO();
	private EventDAO daoEv = new EventDAO();
	
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
