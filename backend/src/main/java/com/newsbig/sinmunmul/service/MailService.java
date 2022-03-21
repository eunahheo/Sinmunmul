package com.newsbig.sinmunmul.service;

import com.newsbig.sinmunmul.dto.EmailDto;

public interface MailService {

	public String generateKey();

	public void sendMail(EmailDto email);

}
