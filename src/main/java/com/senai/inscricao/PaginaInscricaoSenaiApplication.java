package com.senai.inscricao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaginaInscricaoSenaiApplication {

	public static void main(String[] args) {
		//System.out.println("Senha 123123:");
		//System.out.println(new BCryptPasswordEncoder().encode("123123"));

		SpringApplication.run(PaginaInscricaoSenaiApplication.class, args);
	} 
   
}
