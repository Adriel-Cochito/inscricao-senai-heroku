package com.senai.inscricao.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senai.inscricao.domains.Curso;
import com.senai.inscricao.services.CursoService;

@Controller
@RequestMapping("cursos")
public class CursoController {

	@Autowired
	private CursoService service;

	// Salvar curso
	@PostMapping({ "/salvar" })
	public String salvar(Curso curso, RedirectAttributes attr) {
		service.salvar(curso);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		attr.addFlashAttribute("curso", curso);
		return "redirect:/cursos/lista";
	}
	
	// Abrir cadstro de usuarios (assistente/admin/paciente)
	@GetMapping("/novo/cadastro")
	public String cadastrarCurso(Curso curso) {
		return "curso/cadastro";
	}
	
	// Listar sUsuarios na tebela
	@GetMapping("/datatables/server/cursos")
	public ResponseEntity<?> listarCursosDataTables(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarTodos(request));
	}
	
	// Abrir lista de usuarios
	@GetMapping("/lista")
	public String listarCursos() {

		return "curso/lista";
	}

	// Pre edição de credenciais de usuarios
	@GetMapping("/editar/{id}")
	public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {
		return new ModelAndView("curso/cadastro", "curso", service.buscarPorId(id));
	} 
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getCursoPorTermo(@RequestParam ("termo") String termo) {
		List<String> cursos = service.buscarCursoPeloTermo(termo);
		return ResponseEntity.ok(cursos);
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/cursos/lista";
	}
	

}
