package com.bridgelabz.fundoonote.note.model;

import java.util.Date;

public class NoteDTO {

	private String noteId;
	private String userId;
	private String title;
	private String description;
	private String color;
	private Date createAt;
	private Date updateAt;
	private Date remindAt;
	private boolean trash;
	private boolean archive;
	private boolean pin;
	

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Date getRemindAt() {
		return remindAt;
	}

	public void setRemindAt(Date remindAt) {
		this.remindAt = remindAt;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean isArchive) {
		this.archive = isArchive;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean isTrash) {
		this.trash = isTrash;
	}

}
