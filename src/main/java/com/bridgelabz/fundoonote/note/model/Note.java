package com.bridgelabz.fundoonote.note.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonote.note.services.NoteService;

@Document(collection = "notes")
public class Note {
	@Id
	private String noteId;
	private String userId;
	private String title;
	private String description;
	private String color;
	private Date createAt;
	private Date updateAt;
	private Date remindAt;
	private boolean pin;
	private boolean trash;
	private boolean archive;
	private List<LabelDTO> labelList;
	
	
	public List<LabelDTO> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelDTO> label) {
		this.labelList = label;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean isPin) {
		this.pin = isPin;
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

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean isTrash) {
		this.trash = isTrash;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String id) {
		this.noteId = id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
