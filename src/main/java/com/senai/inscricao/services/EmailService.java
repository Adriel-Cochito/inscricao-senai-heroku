package com.senai.inscricao.services;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senai.inscricao.domains.EmailRequest;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;



@Service
public class EmailService {
	
	
	@Autowired
	SendGrid sendGrid;
	
	public Response sendemail() 
	{
		
		EmailRequest novoEmail = new EmailRequest(null, null, null);
		
		novoEmail.setTo("adrielcochito@gmail.com");
		novoEmail.setSubject("Hello, World!");
		novoEmail.setBody("Teste Body");
		System.out.println("Email service email: " + novoEmail);
		
		Mail mail = new Mail(new Email("adrielcochito.dev@gmail.com"), novoEmail.getSubject(), new Email(novoEmail.getTo()),new Content("text/plain", novoEmail.getBody()));
		mail.setReplyTo(new Email("adrielcochito@gmail.com"));
		Request request = new Request();

		Response response = null;

		try {

			request.setMethod(Method.POST);

			request.setEndpoint("mail/send");

			request.setBody(mail.build());

			response=this.sendGrid.api(request);

		} catch (IOException ex) {

			System.out.println(ex.getMessage());

		}

		System.out.println("Email finalizado ");
		return response;
		
	}

}
