package com.bridgelabz.fundoonote.user.producer;

import com.bridgelabz.fundoonote.user.model.MailDTO;

public interface IProducer {
	public void produceMsg(MailDTO mailDTO);
}
