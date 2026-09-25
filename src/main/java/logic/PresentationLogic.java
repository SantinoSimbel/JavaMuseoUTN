package logic;

import data.EventDAO;
import data.PresentationDAO;
import entities.Exhibition;
import entities.Presentation;
import java.time.*;
import java.util.Comparator;
import java.util.LinkedList;

public class PresentationLogic {

	
	private PresentationDAO dao = new PresentationDAO();
	private EventDAO daoEv = new EventDAO();
	
	
	public LinkedList<Presentation> getPresentations(){
		LinkedList<Presentation> presentations = dao.list();
		for (Presentation pre : presentations) {
			pre.updateStatus();
			daoEv.update(pre);
		}
		
		
		presentations.sort(Comparator.<Presentation>comparingInt(p -> obtenerPrioridad(p)).thenComparing(Presentation::getDay));

		
		return presentations;
	}
	
	
	public void registerPresentation(Presentation newPresentation) throws Exception{
		
		//validaciones
		if (newPresentation.getDay().isBefore(LocalDate.now()) || newPresentation.getDay().equals(LocalDate.now())){
			throw new Exception("La fecha debe ser superior al día de hoy.");
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
		if (newPresentation.getDay().isBefore(LocalDate.now()) || newPresentation.getDay().equals(LocalDate.now())){
			throw new Exception("La fecha debe ser superior al día de hoy.");
		}
		if (newPresentation.getCapacity() <= 0){
			throw new Exception("Los cupos no pueden ser menor o igual a 0.");
		}
		
		daoEv.update(newPresentation);		
		dao.update(newPresentation);
	}
	
	
	public int obtenerPrioridad(Presentation e) {
        
		
		// Grupo 1: Creado"
        if ("Creado".equals(e.getStatus())) {
            return 1; 
        }
        // Grupo 2: Terminadas
        else if ("Terminado".equals(e.getStatus())) {
            return 2;
        }
        // Grupo 3: Empezadas
        else if ("Empezado".equals(e.getStatus())) {
            return 3;
        }
        
        return 4; // Por si acaso hay un estado inválido
    }
}
