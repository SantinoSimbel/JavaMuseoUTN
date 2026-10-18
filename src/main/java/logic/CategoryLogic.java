package logic;

import data.CategoryDAO;
import data.ItemDAO;
import entities.Category;

public class CategoryLogic {
private CategoryDAO dao = new CategoryDAO();
private ItemDAO itemDao = new ItemDAO();
	
	public void addCategory(Category newCat) throws Exception{
		
		//validaciones
		if (isCategoryNameTaken(newCat)) {
			throw new Exception("El nombre ingresado ya se encuentra registrado.");
		}
		
		dao.add(newCat);
	}
	
	public void updateCategory(Category newCat) throws Exception{
		
		if (isCategoryNameTaken(newCat)) {
			throw new Exception("El nombre ingresado ya se encuentra registrado.");
		}
		
		dao.update(newCat);
	}
	

	public void deleteCategory(Category cat) throws Exception {
	    if (itemDao.existsByCategory(cat)) {
	        throw new Exception("No se puede eliminar la categoría porque está siendo usada por uno o más items.");
	    }
	    dao.delete(cat);
	}
	
	
	public boolean isCategoryNameTaken(Category cat){
		Category catWithName = dao.searchByName(cat);
		
		//si encontro el dni devuelve true
		if (catWithName != null) {
			return true; 
		} else {
			return false;
		}
	}
	
}
