package entities;

import java.time.*;
import java.util.LinkedList;


public abstract class Event {
	private int id;
	private String title;
	private String description;
	private String status;
	private LocalTime endTime;
	private LocalTime startTime;
	private LinkedList<Item> items;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LinkedList<Item> getItems() {
		return items;
	}
	public void setItems(LinkedList<Item> items) {
		this.items = items;
	}
	public void addItem(Item ite) {
		this.items.add(ite);
	}
	public Event() {
	    this.items = new LinkedList<>();
	}
	

	public abstract void updateStatus();

	  

}
