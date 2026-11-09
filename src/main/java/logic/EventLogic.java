package logic;

import data.EventDAO;
import entities.Event;



public abstract class EventLogic {


	protected EventDAO daoEv = new EventDAO();
	
	//add event
	public void validationEvent(Event newEvent) throws Exception{
		
		//validaciones
		if (newEvent.getItems() == null || newEvent.getItems().isEmpty()) {
			throw new Exception("Debe seleccionar al menos un artículo.");
		}
		
		if (newEvent.getEndTime().isBefore(newEvent.getStartTime())){
			throw new Exception("La hora de fin no puede ser menor a la de inicio.");
		}
		if (newEvent.getEndTime().equals(newEvent.getStartTime())){
			throw new Exception("La hora de fin no puede ser igual a la de inicio.");
		} 
		
		newEvent.setStatus("Creado");
	}
	
	
	
	
	
	
	protected int getPriority(Event e) {
        
		
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
