package com.senai.inscricao.web.controller;

import java.util.List;
import java.util.Map;

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
import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.services.CursoService;
import com.senai.inscricao.services.InscricaoService;

@Controller
@RequestMapping("cursos")
public class CursoController {

	@Autowired
	private CursoService service;
	
	@Autowired
	private InscricaoService inscricaoService;

	// Salvar curso
	@PostMapping({ "/salvar" })
	public String salvar(Curso curso, RedirectAttributes attr) {
		service.salvar(curso);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		attr.addFlashAttribute("curso", curso);
		return "redirect:/cursos/lista";
	}
	
	@GetMapping({ "/libera/resultado/{id}" })
	public String liberaResultado(@PathVariable("id") Long id, RedirectAttributes attr, HttpServletRequest request) {
		
		Curso curso = service.buscarPorId(id);
		curso.setLiberaResultados(true);
		
		service.salvar(curso);
		
		List<Inscricao> listaInscricao =  inscricaoService.obterLista();
		
		for (Inscricao inscricao : listaInscricao) {
		    if (inscricao.getCurso().getId() == id ) {
		    	System.out.println("Iniciando Inscrição: ");
		    	System.out.println("Id Inscricao: "+inscricao.getId());
		    	System.out.println(inscricao.getCurso().getTitulo());
		    	System.out.println("Situação: " + inscricao.getSituacao());
		    	if (inscricao.getSituacao() == 2) {
					inscricao.setSituacao(1);
					inscricaoService.salvar(inscricao);
				} else if (inscricao.getSituacao() == 0) {
					inscricao.setSituacao(3);
					inscricaoService.salvar(inscricao);
				}
		    	
			}
		}

		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		return "redirect:/cursos/lista";
	}
	
	@GetMapping({ "/cancela/resultado/{id}" })
	public String cancelaResultado(@PathVariable("id") Long id, RedirectAttributes attr, HttpServletRequest request) {
		
		Curso curso = service.buscarPorId(id);
		curso.setLiberaResultados(false);
		
		service.salvar(curso);
		
		List<Inscricao> listaInscricao =  inscricaoService.obterLista();
		
		for (Inscricao inscricao : listaInscricao) {
		    if (inscricao.getCurso().getId() == id ) {
		    	System.out.println("Iniciando Inscrição: ");
		    	System.out.println("Id Inscricao: "+inscricao.getId());
		    	System.out.println(inscricao.getCurso().getTitulo());
		    	System.out.println("Situação: " + inscricao.getSituacao());
		    	if (inscricao.getSituacao() == 1) {
					inscricao.setSituacao(2);
					inscricaoService.salvar(inscricao);
				} else if (inscricao.getSituacao() == 3) {
					inscricao.setSituacao(0);
					inscricaoService.salvar(inscricao);
				}
		    	
			}
		}
		
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
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
