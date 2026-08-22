package entities;

import java.time.*;

public class Exhibition extends Event {
	private LocalDate startDay;
	private LocalDate endDay;
	
	
	
	public LocalDate getStartDay() {
		return startDay;
	}
	public void setStartDay(LocalDate startDay) {
		this.startDay = startDay;
	}
	public LocalDate getEndDay() {
		return endDay;
	}
	public void setEndDay(LocalDate endDay) {
		this.endDay = endDay;
	}
	
	
	

}
