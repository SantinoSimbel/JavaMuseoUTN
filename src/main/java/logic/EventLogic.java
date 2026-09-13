package logic;

import data.EventDAO;
import entities.Event;
import java.time.*;


public class EventLogic {

	
	public void registerEvent(Event newEvent) throws Exception{
		
		//validaciones
		if (newEvent.getItems() == null || newEvent.getItems().isEmpty()) {
			throw new Exception("Debe seleccionar al menos un artículo.");
		}
		
		if (newEvent.getEndTime().isBefore(newEvent.getStartTime())){
			throw new Exception("La hora de fin no puede ser menor a la de inicio.");
		}
		
	}
	
	public void updateEvent(Event newEvent) throws Exception{
		
		//validaciones
		if (newEvent.getItems() == null || newEvent.getItems().isEmpty()) {
			throw new Exception("Debe seleccionar al menos un artículo.");
		}
		
		if (newEvent.getEndTime().isBefore(newEvent.getStartTime())){
			throw new Exception("La hora de fin no puede ser menor a la de inicio.");
		}
		
				
	}
	
	/*if (newEvent.getStatus().equals("Empezado") || newEvent.getStatus().equals("Terminado")) {
			throw new Exception("No es posible editar este Evento.");
		}*/

	
}
