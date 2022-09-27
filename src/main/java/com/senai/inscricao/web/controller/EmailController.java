package com.senai.inscricao.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senai.inscricao.domains.EmailRequest;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.EmailService;
import com.senai.inscricao.services.UsuarioService;
import com.sendgrid.Response;


@Controller
public class EmailController {
	
	
	@Autowired
	private EmailService emailservice;
	
	@Autowired
	private UsuarioService usuarioService;
	
	

	@GetMapping("/sendemail")
	public String sendemail()
	{
		EmailRequest novoEmail = new EmailRequest(null, null, null);
		novoEmail.setSubject("Titulo");
		novoEmail.setBody("Corpo do email");
		novoEmail.setTo("adriel.cochito@al.infnet.edu.br");
		
		
		emailservice.sendemail(novoEmail);
		return "redirect:/assistentes/notifica/candidatos/pagina";
			    
	}
	
	@PostMapping("/sendemail/nao-inscritos")
	public String sendemailNaoInscritos(@RequestParam("titulo") String titulo, @RequestParam("mensagem") String mensagem,
			RedirectAttributes attr)
	{
		EmailRequest novoEmail = new EmailRequest(null, null, null);
		
//		String subject = "Inscrições Online";
//		String body = "Mensagem do body de email. Acesse: www.eletrica3d.tech ";
		
		String subject = titulo;
		String body = mensagem;
		
		
		
		novoEmail.setSubject(subject);
		novoEmail.setBody(body);
		
		List<Usuario> usersNaoInscritos = usuarioService.obterListaNaoInscrito();
		
		for (Usuario usuario : usersNaoInscritos) {
			novoEmail.setTo(usuario.getEmail());
			emailservice.sendemail(novoEmail);
		}
		
		attr.addFlashAttribute("sucesso", "Email enviado para candidatos não inscritos!");
//		attr.addFlashAttribute("falha", "Email não enviado!");
		
		return "redirect:/assistentes/notifica/candidatos/pagina";
			    
	}
	
//	@GetMapping("/sendemail/nao-inscritos")
//	public String sendemailNaoInscritos()
//	{
//		EmailRequest novoEmail = new EmailRequest(null, null, null);
//		
//		String subject = "Inscrições Online";
//		String body = "Mensagem do body de email. Acesse: www.eletrica3d.tech ";
//		
//		novoEmail.setSubject(subject);
//		novoEmail.setBody(body);
//		
//		List<Usuario> usersNaoInscritos = usuarioService.obterListaNaoInscrito();
//		
//		for (Usuario usuario : usersNaoInscritos) {
//			novoEmail.setTo(usuario.getEmail());
//			emailservice.sendemail(novoEmail);
//		}
//		return "/home";
//			    
//	}

}
