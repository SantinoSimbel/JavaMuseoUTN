package entities;

import java.math.BigDecimal;
import java.time.*;

public class Presentation extends Event {
	private LocalDate day;
	private int capacity;
	private BigDecimal  price;
	
	
	public LocalDate getDay() {
		return day;
	}
	public void setDay(LocalDate day) {
		this.day = day;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public void updateStatus() {
		LocalDateTime now = LocalDateTime.now();
		
		LocalDateTime startDateTime = LocalDateTime.of(getDay(), getStartTime());
		LocalDateTime endDateTime = LocalDateTime.of(getDay(), getEndTime());
		
		if (now.isBefore(startDateTime)) {
			this.setStatus("Creado"); 
		}
		else if (now.isBefore(endDateTime)) {
			this.setStatus("Empezado"); 
		} else {
			this.setStatus("Terminado");
		}
	}
	
	

}
