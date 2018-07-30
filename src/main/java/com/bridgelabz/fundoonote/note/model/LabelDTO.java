package com.bridgelabz.fundoonote.note.model;

import io.swagger.annotations.ApiModelProperty;

public class LabelDTO {

	@ApiModelProperty(hidden = true)
	private String labelId;
	private String labelName;

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	/*
	 * public String getNoteId() { return noteId; }
	 * 
	 * public void setNoteId(String noteId) { this.noteId = noteId; }
	 */
}
