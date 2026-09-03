package logic;

import data.UserDAO;
import entities.User;

public class UserLogic {

	UserDAO dao = new UserDAO();
	
	public boolean isUserDniTaken(User u){
		User userWithDni = dao.searchByDni(u);
		
		//si encontro el dni devuelve true
		if (userWithDni != null) {
			return true; 
		} else {
			return false;
		}
	}
	
	public boolean isUserEmailTaken(User u){
		User userWithEmail = dao.searchByEmail(u);
		
		if (userWithEmail != null) {
			return true;
		} else {
			return false;
		}
	}
}
