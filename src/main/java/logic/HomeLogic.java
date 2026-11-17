package logic;

import java.util.LinkedList;
import data.ItemDAO;
import entities.Exhibition;
import entities.Item;
import entities.Presentation;

public class HomeLogic {
	
	private int limit = 8;
	private String operation =  "homeList";
	
	private ExhibitionLogic exLogic = new ExhibitionLogic();
    private PresentationLogic preLogic = new PresentationLogic();
    private ItemDAO iteDAO = new ItemDAO();

    
    
    public LinkedList<Exhibition> getExhibitions() {
        return firstN(exLogic.getExhibitions(null, operation));
    }

    public LinkedList<Presentation> getPresentations() {
        return firstN(preLogic.getPresentations(null, operation));
    }

    public LinkedList<Item> getItems() {
        return firstN(iteDAO.list());
    }

    private <T> LinkedList<T> firstN(LinkedList<T> list) {
        if (list == null) return new LinkedList<>();
        return new LinkedList<>(list.subList(0, Math.min(limit, list.size())));
    }
	
}
