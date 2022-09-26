package com.senai.inscricao.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.senai.inscricao.domains.EmailRequest;
import com.senai.inscricao.services.EmailService;
import com.sendgrid.Response;


@Controller
public class EmailController {
	
	
	@Autowired
	private EmailService emailservice;
	

	@GetMapping("/sendemail")
	public String sendemail()
	{
		emailservice.sendemail();
		return "/home";
			    
	}
	
//	@PostMapping("/sendemail")
//	public ResponseEntity<String> sendemail(@RequestBody EmailRequest emailrequest)
//	{
//		
//		Response response=emailservice.sendemail(emailrequest);
//		if(response.getStatusCode()==200||response.getStatusCode()==202)
//			return new ResponseEntity<>("send successfully",HttpStatus.OK);
//		return new ResponseEntity<>("failed to sent",HttpStatus.NOT_FOUND);
//			    
//	}

}
