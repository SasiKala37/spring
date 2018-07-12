package com.bridgelabz.fundoonote.model;

public class ResponseDTO {
	private String message;
	private int status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseDTO [message=" + message + ", status=" + status + "]";
	}

}
