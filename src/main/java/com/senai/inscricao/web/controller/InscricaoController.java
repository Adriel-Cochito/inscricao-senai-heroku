package com.senai.inscricao.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senai.inscricao.domains.Candidato;
import com.senai.inscricao.domains.Curso;
import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.domains.PerfilTipo;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.CandidatoService;
import com.senai.inscricao.services.CursoService;
import com.senai.inscricao.services.InscricaoService;
import com.senai.inscricao.services.UsuarioService;

@Controller
@RequestMapping("inscricoes")
public class InscricaoController {

	@Autowired
	private InscricaoService service;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CandidatoService candidatoService;
	@Autowired
	private CursoService cursoService;
	
	// abre a pagina de inscricoes de consultas
	@GetMapping({ "/inscrever" })
	public String abrirInscricao(Inscricao inscricao, Model model, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		
		System.out.println("Imprimindo usuario da sessao: ");
		System.out.println(user);
		System.out.println("Imprimindo ATIVO do usuario da sessao: ");
		System.out.println(usuarioService.buscarPorCpf(user.getUsername()).isAtivo());
		
		if (!(usuarioService.buscarPorCpf(user.getUsername()).isAtivo())) {
			model.addAttribute("isAtivo", usuarioService.buscarPorCpf(user.getUsername()).isAtivo());
			attr.addFlashAttribute("sucesso", "Finalize o cadastro de dados pessoais para se inscrever nos cursos.");
			return "inscricao/cadastro";
		} else {
			model.addAttribute("cursos", cursoService.obterLista());
			return "inscricao/cadastro";
		}
		
		
	}

	// abrir pagina de historico de agendamento do candidato
	@GetMapping({"/historico/candidato"})
	public String historicoCandidato() {
		return "inscricao/historico-candidato";
	}
	
	// abrir pagina de historico de agendamento do candidato
	@GetMapping({"/consultar"})
	public String historicoGeral() {
		return "inscricao/historico-geral";
	}
	
	
	// Abrir lista de inscricoes
	@GetMapping("/lista")
	public String listarInscricoes(Model model) {
		
		List<Curso> listaCursos = cursoService.obterLista();
		List<Inscricao> listaInscricao = service.obterLista();
		
		System.out.println("Lista curso: ");
		System.out.println(listaCursos.size());
		
		List<String> tamanho = new ArrayList<String>();
		
		for (Curso curso : listaCursos) {
		    System.out.println(curso.getTitulo());
		    tamanho.add(curso.getTitulo());
		    
		}
		
		
		System.out.println("Tamanho do Array list :");
		System.out.println(tamanho.size());
		
		model.addAttribute("tamanho", tamanho.size());
		model.addAttribute("quantidadeInscricao", listaInscricao.size());
		
		model.addAttribute("cursos", listaCursos);
		model.addAttribute("inscricoes", listaInscricao);
		return "inscricao/lista";
	}
	
	// Abrir lista de inscricoes
	@GetMapping("/estatisticas")
	public String verEstatisticas(Model model) {
		
		List<Curso> listaCursos = cursoService.obterLista();
		List<Inscricao> listaInscricao = service.obterLista();
		List<Candidato> listaCandidatos = candidatoService.obterLista();
		List<Usuario> listaUsuarios = usuarioService.obterLista();
		
		System.out.println("Lista curso: ");
		System.out.println(listaCursos.size());
		
		List<String> tamanho = new ArrayList<String>();
		
		for (Curso curso : listaCursos) {
		    System.out.println(curso.getTitulo());
		    tamanho.add(curso.getTitulo());
		    
		}
		
		
		System.out.println("Tamanho do Array list :");
		System.out.println(tamanho.size());
		
		model.addAttribute("tamanho", tamanho.size());
		model.addAttribute("quantidadeInscricao", listaInscricao.size());
		model.addAttribute("quantidadeCursos", listaCursos.size());
		model.addAttribute("quantidadeUsuarios", listaUsuarios.size());
		model.addAttribute("quantidadeCandidatos", listaCandidatos.size());
		
		model.addAttribute("cursos", listaCursos);
		
		model.addAttribute("inscricoes", listaInscricao);
		return "inscricao/estatisticas";
	}
	
	
	// Listar sUsuarios na tebela
	@GetMapping("/datatables/server/lista")
	public ResponseEntity<?> listarInscricoesDataTables(HttpServletRequest request) {
		return ResponseEntity.ok(service.buscarTodos(request));
	}
	
	// localizar o historico de inscricoes por usuario logado
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoInscricoesPorCandidato(HttpServletRequest request, @AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.CANDIDATO.getDesc()))) {
			
			return ResponseEntity.ok(service.buscarHistoricoPorCandidatoCpf(user.getUsername(), request));
		}	
		
		return ResponseEntity.notFound().build();
	}
	
	// localizar o historico de inscricoes por usuario logado
	@GetMapping("/datatables/server/historicos")
	public ResponseEntity<?> historicoInscricoes(HttpServletRequest request, @AuthenticationPrincipal User user) {
			
		return ResponseEntity.ok(service.buscarHistoricoPorCandidatoCpf(user.getUsername(), request));
	}
	
	
	// salvar um consulta agendada
	@PostMapping({ "/salvar" })
	public String salvar(Inscricao inscricao, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Candidato candidato = candidatoService.buscarPorUsuarioCpf(user.getUsername());
		String titulo = inscricao.getCurso().getTitulo();
		Curso curso= cursoService.buscarPorTitulos(new String[] { titulo }).stream()
				.findFirst().get();
		inscricao.setCurso(curso);
		inscricao.setCandidato(candidato);
		service.salvar(inscricao);
		attr.addFlashAttribute("sucesso", "Sua Inscrição foi realizada com sucesso.");
		return "redirect:/home";
	}
	
	// localizar agendamento pelo id e envia-lo para a pagina de cadastro
	@GetMapping("/editar/inscricao/{id}") 
	public String preEditarConsultaPaciente(@PathVariable("id") Long id, 
										    ModelMap model, @AuthenticationPrincipal User user) {
		List<Curso> listaCursos = cursoService.obterLista();
		Inscricao inscricao= service.buscarPorIdECandidato(id, user.getUsername());
		
		model.addAttribute("cursos", listaCursos);
		model.addAttribute("inscricao", inscricao);
		return "inscricao/cadastro";
	}
	
	@GetMapping("/excluir/inscricao/{id}")
	public String excluirConsulta(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Inscrição excluída com sucesso.");
		return "redirect:/inscricoes/historico/candidato";
	}


}
