package com.senai.inscricao.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
import com.senai.inscricao.domains.Registro;
import com.senai.inscricao.services.CursoService;
import com.senai.inscricao.services.InscricaoService;
import com.senai.inscricao.services.RegistroService;
import com.senai.inscricao.services.UsuarioService;

@Controller
@RequestMapping("cursos")
public class CursoController {

	@Autowired
	private CursoService service;
	
	@Autowired
	private InscricaoService inscricaoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RegistroService registroService;

	// Salvar curso
	@PostMapping({ "/salvar" })
	public String salvar(Curso curso, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		System.out.println("Iniciando Salve");
		Registro registro = new Registro();
		registro.setTitulo("Curso criado/editado");
		registro.setUsuario(usuarioService.buscarPorCpf(user.getUsername()));
		
		
		try {
			if (service.buscarPorId(curso.getId()).getId() == null ) {
				registro.setTitulo("Curso criado");
				registro.setDescricao("Curso criado com o nome: (" + curso.getTitulo() + ") ");
				System.out.println("Curso criado com o nome: (" + curso.getTitulo() + ") ");
			} else {
				registro.setTitulo("Curso editado");
				registro.setDescricao("Curso editado com o nome: (" + curso.getTitulo() + ") ");
				System.out.println("Curso editado com o nome: (" + curso.getTitulo() + ") ");
			}
		} catch (Exception e) {
			registro.setTitulo("Curso editado");
			registro.setDescricao("Curso editado com o nome: (" + curso.getTitulo() + ") ");
			System.out.println("Error - Curso editado com o nome: (" + curso.getTitulo() + ") ");
		}
		
		service.salvar(curso);
		registroService.salvar(registro);
		
		
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		attr.addFlashAttribute("curso", curso);
		return "redirect:/cursos/lista";
	}
	
	@GetMapping({ "/resultado/liberar/{id}" })
	public String liberaResultado(@PathVariable("id") Long id, RedirectAttributes attr, HttpServletRequest request,
			@AuthenticationPrincipal User user) {
		
		Curso curso = service.buscarPorId(id);
		curso.setLiberaResultados(true);
		curso.setAtivo(false);
		
		service.salvar(curso);
		
		List<Inscricao> listaInscricao =  inscricaoService.obterLista();
		
		for (Inscricao inscricao : listaInscricao) {
		    if (inscricao.getCurso().getId() == id ) {

		    	if (inscricao.getSituacao() == 2) {
					inscricao.setSituacao(1);
					inscricaoService.salvar(inscricao);
				} else if (inscricao.getSituacao() == 0) {
					inscricao.setSituacao(3);
					inscricaoService.salvar(inscricao);
				}
		    	
			}
		}
		
		Registro registro = new Registro();
		registro.setTitulo("Resultado liberado");
		registro.setDescricao("Resultados liberados para o curso: (" + service.buscarPorId(id).getTitulo() + ") ");
		registro.setUsuario(usuarioService.buscarPorCpf(user.getUsername()));
		registroService.salvar(registro);

		attr.addFlashAttribute("sucesso", "Resultados liberados pra este curso! E inativado para inscrições ");
		return "redirect:/cursos/lista";
	}
	
	@GetMapping({ "/resultado/cancelar/{id}" })
	public String cancelaResultado(@PathVariable("id") Long id, RedirectAttributes attr, HttpServletRequest request,
			@AuthenticationPrincipal User user) {
		
		Curso curso = service.buscarPorId(id);
		curso.setLiberaResultados(false);
		
		service.salvar(curso);
		
		List<Inscricao> listaInscricao =  inscricaoService.obterLista();
		
		for (Inscricao inscricao : listaInscricao) {
		    if (inscricao.getCurso().getId() == id ) {

		    	if (inscricao.getSituacao() == 1) {
					inscricao.setSituacao(2);
					inscricaoService.salvar(inscricao);
				} else if (inscricao.getSituacao() == 3) {
					inscricao.setSituacao(0);
					inscricaoService.salvar(inscricao);
				}
		    	
			}
		}
		
		Registro registro = new Registro();
		registro.setTitulo("Resultado cancelado");
		registro.setDescricao("Resultados cancelados para o curso: (" + service.buscarPorId(id).getTitulo() + ") ");
		registro.setUsuario(usuarioService.buscarPorCpf(user.getUsername()));
		registroService.salvar(registro);
		
		attr.addFlashAttribute("sucesso", "Liberação de Resultados cancelados para este curso! Curso permanece Inativo para inscrições");
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
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		
		
		Registro registro = new Registro();
		registro.setTitulo("Curso removido");
		registro.setDescricao("Curso removido com o nome: (" + service.buscarPorId(id).getTitulo() + ") ");
		registro.setUsuario(usuarioService.buscarPorCpf(user.getUsername()));
		registroService.salvar(registro);
		
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Curso excluido com sucesso.");
		return "redirect:/cursos/lista";
	}
	

}
