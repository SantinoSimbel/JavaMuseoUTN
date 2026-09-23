package logic;

import com.password4j.Password;

import data.UserDAO;
import entities.User;
import entities.UserSessionDTO;

public class UserLogic {

	private UserDAO dao = new UserDAO();
	
	public void registerUser(User newUser) throws Exception{
		
		//validaciones
		if (isUserDniTaken(newUser)) {
			throw new Exception("El DNI ingresado ya se encuentra registrado.");
		}
		
		if (isUserEmailTaken(newUser)) {
			throw new Exception("El correo ingresado ya se encuentra registrado.");
		}
		
		// hasheamos la contraseña con Bcrypt
		String hashedPassword = Password.hash(newUser.getPassword()).withBcrypt().getResult();
		
		newUser.setPassword(hashedPassword);
		
		//logica negocio
		newUser.setRole("user");
		
		dao.add(newUser);
	}
	
	public void updateUser(User newUser) throws Exception{
		//busco su rol viejo y le pongo el mismo (por si mandan un http malisioso)
		User oldUser = dao.search(newUser);
		
		newUser.setRole(oldUser.getRole());
		
		//valido que el nuevo mail no pertenezca a otro usuario
		User registerUser = dao.searchByEmail(newUser);
		
		if(registerUser != null && registerUser.getId() != newUser.getId() ) {
			throw new Exception("El correo ingresado ya pertenece a otra cuenta.");
		}
		
		// si no escribio nueva contraseña usamos el hash anterior, si la edito hacemos uno nuevo
		if (newUser.getPassword() ==null || newUser.getPassword().isEmpty()) {
			newUser.setPassword(oldUser.getPassword());
		} else {
			String hashedPassword = Password.hash(newUser.getPassword()).withBcrypt().getResult();
			newUser.setPassword(hashedPassword);
		}
		
		dao.update(newUser);
	}
	
	public User loginUser(User loginUser) throws Exception{
		
		User dbUser = dao.searchByEmail(loginUser);
		
		if (dbUser == null || !isPasswordCorrect(loginUser, dbUser)) {
			throw new Exception("El correo o la contraseña ingresados no son correctos.");
		}
		return dbUser;
	} 
	
	public void changeUserRole (User u, UserSessionDTO userDto) throws Exception{
		
		User userEdit = dao.search(u);
		
		//me fijo si el usuario logeado es distinto al que quiere editar
		if (userDto.getId() != userEdit.getId()) {
			if("admin".equals(userEdit.getRole())) {
				userEdit.setRole("user");
			} else {
				userEdit.setRole("admin");
			}
			dao.update(userEdit);
		} else {
			throw new Exception("No puede cambiarse el rol a si mismo.");
		}
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
	
	public boolean isPasswordCorrect(User loginUser, User dbUser) {
		if (Password.check(loginUser.getPassword(), dbUser.getPassword()).withBcrypt()) {
			return true;
		} else {
			return false;
		}
	}
}
