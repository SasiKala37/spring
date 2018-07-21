package com.bridgelabz.fundoonote.note.util;

import com.bridgelabz.fundoonote.note.exceptions.NoteCreationException;
import com.bridgelabz.fundoonote.note.model.CreateNoteDTO;

public class Utility {
	public static void validateTitleAndDesc(String title, String description) throws NoteCreationException {
		if ((title == null || title.trim().isEmpty()) && description == null || description.trim().isEmpty()) {
			throw new NoteCreationException("Title and Description fields cannot be empty");
		}
	}
}
