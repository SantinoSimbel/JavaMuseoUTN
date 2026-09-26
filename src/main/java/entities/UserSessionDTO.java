package entities;

public class UserSessionDTO {
	private int id;
	private String role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public UserSessionDTO(User user) {
		this.id = user.getId();
		this.role = user.getRole();
	}
	
}
