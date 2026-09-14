package logic;

import data.EventDAO;
import data.PresentationDAO;
import entities.Exhibition;
import entities.Presentation;
import java.time.*;
import java.util.LinkedList;

public class PresentationLogic {

	
	private PresentationDAO dao = new PresentationDAO();
	private EventDAO daoEv = new EventDAO();
	
	
	public LinkedList<Presentation> getPresentations(){
		LinkedList<Presentation> presentations = dao.list();
		for (Presentation pre : presentations) {
			pre.updateStatus();
		}
		return presentations;
	}
	
	
	public void registerPresentation(Presentation newPresentation) throws Exception{
		
		//validaciones
		if (newPresentation.getDay().isBefore(LocalDate.now())){
			throw new Exception("La fecha no puede ser menor al día de hoy.");
		}
		if (newPresentation.getCapacity() <= 0){
			throw new Exception("Los cupos no pueden ser menor o igual a 0.");
		}
		
		daoEv.add(newPresentation);
		dao.add(newPresentation);
	}
	
	public void updatePresentation(Presentation newPresentation) throws Exception{

		Presentation oldPresentation = dao.search(newPresentation);
		
		//validaciones
		if (newPresentation.getDay().isBefore(LocalDate.now())){
			throw new Exception("La fecha no puede ser menor al día de hoy.");
		}
		if (newPresentation.getCapacity() <= 0){
			throw new Exception("Los cupos no pueden ser menor o igual a 0.");
		}
		
		daoEv.update(newPresentation);		
		dao.update(newPresentation);
	}
	
	

}
