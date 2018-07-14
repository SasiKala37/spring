package com.bridgelabz.fundoonote.user.model;

public class MailDTO {
	private String id;
	private String toMailAddress;

	private String subject;

	private String salutation;

	private String body;

	private String mailSign;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToMailAddress() {
		return toMailAddress;
	}

	public void setToMailAddress(String toMailAddress) {
		this.toMailAddress = toMailAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getMailSign() {
		return mailSign;
	}

	public void setMailSign(String mailSign) {
		this.mailSign = mailSign;
	}

}
