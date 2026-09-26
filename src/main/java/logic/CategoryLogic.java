package logic;

import data.CategoryDAO;
import entities.Category;

public class CategoryLogic {
private CategoryDAO dao = new CategoryDAO();
	
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
