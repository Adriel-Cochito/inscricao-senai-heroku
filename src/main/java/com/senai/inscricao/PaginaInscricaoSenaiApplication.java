package com.senai.inscricao;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.senai.inscricao.domains.Perfil;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.UsuarioService;


@SpringBootApplication
public class PaginaInscricaoSenaiApplication  {
	
	@Autowired
	private static UsuarioService usuarioService;
	
	public static void main(String[] args) {
		SpringApplication.run(PaginaInscricaoSenaiApplication.class, args);
		
		Usuario usuario = new Usuario();
		List<Perfil> perfil = Arrays.asList(new Perfil(1L), new Perfil(2L));
		LocalDate dataAtual = LocalDate.now();
		usuario.setPerfis(perfil);
		usuario.setAtivo(true);
		usuario.setCpf("12345678901");
		usuario.setEmail("123@123");
		usuario.setDtInscricao(dataAtual);
		usuario.setSenha("12345678901");
		usuario.setStatusCadastro(0);	
		try {
			usuarioService.salvarUsuario(usuario);
		} catch (Exception e) {
			System.out.println("Erro ao criar admin");
		}
		
			
			
	} 
	
}
