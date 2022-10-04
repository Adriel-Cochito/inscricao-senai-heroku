package com.senai.inscricao.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senai.inscricao.services.RegistroService;

@Controller
@RequestMapping("registros")
public class RegistroController {
	
	@Autowired
	private RegistroService service;
	
	// Abrir lista de usuarios
		@GetMapping("/lista")
		public String listarRegistros() {

			return "registro/lista";
		}
		
	
	// Listar sUsuarios na tebela
		@GetMapping("/datatables/server/registros")
		public ResponseEntity<?> listarRegistrosDataTables(HttpServletRequest request) {

			return ResponseEntity.ok(service.buscarTodos(request));
		}

}