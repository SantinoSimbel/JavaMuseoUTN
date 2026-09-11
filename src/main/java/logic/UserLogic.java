package logic;

import data.UserDAO;
import entities.User;

public class UserLogic {

	private UserDAO dao = new UserDAO();
	
	public void register(User user) throws Exception{
		
		//validaciones
		if (isUserDniTaken(user)) {
			throw new Exception("El DNI ingresado ya se encuentra registrado.");
		}
		
		if (isUserEmailTaken(user)) {
			throw new Exception("El correo ingresado ya se encuentra registrado.");
		}
		//logica negocio
		user.setRole("user");
		
		dao.add(user);
	}
	
	public boolean isUserDniTaken(User user){
		User userWithDni = dao.searchByDni(user);
		
		//si encontro el dni devuelve true
		if (userWithDni != null) {
			return true; 
		} else {
			return false;
		}
	}
	
	public boolean isUserEmailTaken(User user){
		User userWithEmail = dao.searchByEmail(user);
		
		if (userWithEmail != null) {
			return true;
		} else {
			return false;
		}
	}
}
