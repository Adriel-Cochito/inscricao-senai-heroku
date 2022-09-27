package com.senai.inscricao.domains;

import java.util.ArrayList;

import com.sendgrid.helpers.mail.objects.Content;


public class EmailRequest {

	private String to;
	private String subject;
	private String body;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
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
	public void setBody(String string) {
		this.body = string;
	}
	public EmailRequest(String to, String subject, String body) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	
	
}
