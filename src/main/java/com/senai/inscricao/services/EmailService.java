package com.senai.inscricao.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senai.inscricao.domains.EmailRequest;
import com.senai.inscricao.domains.Registro;
import com.senai.inscricao.domains.Usuario;
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
	
	@Autowired
	RegistroService registroService;

	public Response sendemail(EmailRequest novoEmail) {
		
		String email = novoEmail.getTo();
		String titulo = novoEmail.getSubject();
		String mensagem = novoEmail.getBody();
		
		System.out.println("Email titulo: " + titulo);
		System.out.println("Email mensagem: " + mensagem);
		System.out.println("Email email: " + email);

		Mail mail = new Mail(new Email("inscricoesonline.net@gmail.com"), novoEmail.getSubject(), new Email(novoEmail.getTo()),new Content("text/plain", novoEmail.getBody()));
		mail.setReplyTo(new Email("sesisenaiaraxa@fiemg.com.br"));
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

//	public void sendEmailNaoInscritos(List<Usuario> usersNaoInscritos,String Titulo, String mensagem) {
//		EmailRequest novoEmail = new EmailRequest(null, null, null);
//		novoEmail.setBody(mensagem);
//		novoEmail.setSubject(Titulo);
//		for (Usuario usuario : usersNaoInscritos) {
//			novoEmail.setTo(usuario.getEmail());
//			sendemail(novoEmail);
//		}
//		
//	}

}
