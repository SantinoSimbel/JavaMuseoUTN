package entities;

public class Review {
	private User user;
	private String content;
	private double punctuation;
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getPunctuation() {
		return punctuation;
	}
	public void setPunctuation(double  punctuation) {
		this.punctuation = punctuation;
	}

	
	
}
