package logic;


import data.EventDAO;
import data.ExhibitionDAO;
import entities.Exhibition;
import java.time.*;
import java.util.LinkedList;
import java.util.Comparator;


public class ExhibitionLogic {
	private ExhibitionDAO dao = new ExhibitionDAO();
	private EventDAO daoEv = new EventDAO();
	
	public LinkedList<Exhibition> getExhibitions(){
		LinkedList<Exhibition> exhibitions = dao.list();
		for (Exhibition ex : exhibitions) {
			ex.updateStatus();
			daoEv.update(ex);
		}

		exhibitions.sort(Comparator.<Exhibition>comparingInt(e -> obtenerPrioridad(e)).thenComparing(Exhibition::getStartDay));

		
		return exhibitions;
	}
	
	public void registerExhibition(Exhibition newExhibition) throws Exception{
		
		//validaciones
		if (newExhibition.getEndDay().isBefore(newExhibition.getStartDay())){
			throw new Exception("La fecha de fin no puede ser menor a la de inicio.");
		}
		
		if (newExhibition.getEndDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().equals(LocalDate.now())){
			throw new Exception("Las fechas deben ser superiores al día de hoy.");
		}
		
		daoEv.add(newExhibition);
		dao.add(newExhibition);
	}
	
	public void updateExhibition(Exhibition newExhibition) throws Exception{
		
		//validaciones
		if (newExhibition.getEndDay().isBefore(newExhibition.getStartDay())){
			throw new Exception("La fecha de fin no puede ser menor a la de inicio.");
		}
				
		if (newExhibition.getEndDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().isBefore(LocalDate.now()) || newExhibition.getStartDay().equals(LocalDate.now())){
			throw new Exception("Las fechas deben ser superiores al día de hoy.");
		}
				
		daoEv.update(newExhibition);				
		dao.update(newExhibition);
	}
	
	
	public int obtenerPrioridad(Exhibition e) {
        
		
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
