package com.bridgelabz.fundoonote.note.model;

import java.util.Date;
import java.util.List;

public class CreateNoteDTO {

	private String title;
	private String description;
	private String color="white";
	private boolean pin=false;
	private boolean archive=false;
	private Date remindAt;
	private List<String> labelList;
	

	public List<String> getLadelList() {
		return labelList;
	}

	public void setLadelList(List<String> ladelList) {
		this.labelList = ladelList;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public Date getRemindAt() {
		return remindAt;
	}

	public void setRemindAt(Date remindAt) {
		this.remindAt = remindAt;
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

}
