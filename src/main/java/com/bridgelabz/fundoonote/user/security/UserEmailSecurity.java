package com.bridgelabz.fundoonote.user.security;

import javax.mail.MessagingException;

import com.bridgelabz.fundoonote.user.model.MailDTO;

public interface UserEmailSecurity {
	public void sendEmail(MailDTO mailDTO) throws MessagingException;
}
