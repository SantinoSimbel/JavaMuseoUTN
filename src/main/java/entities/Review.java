package entities;

public class Review {
	private int userId;
	private String content;
	private double punctuation;
	
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
