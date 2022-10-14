package com.senai.inscricao.web.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senai.inscricao.domains.Assistente;
import com.senai.inscricao.domains.Candidato;
import com.senai.inscricao.domains.Curso;
import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.domains.Registro;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.AssistenteService;
import com.senai.inscricao.services.CandidatoService;
import com.senai.inscricao.services.CursoService;
import com.senai.inscricao.services.InscricaoService;
import com.senai.inscricao.services.RegistroService;
import com.senai.inscricao.services.UsuarioService;

@Controller
@RequestMapping("assistentes")
public class AssistenteController {

	@Autowired
	private AssistenteService service;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AssistenteService assistenteService;
	
	@Autowired
	private RegistroService registroService;
	
	@Autowired
	private CandidatoService candidatoService;
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private InscricaoService inscricaoService;

	

	// Abrir pagina de dados pessoais
	@GetMapping({ "/dados" })
	public String abrirPorAssistente(Assistente assistente, ModelMap model, @AuthenticationPrincipal User user) {
		if (assistente.hasNotId()) {
			assistente = service.buscarPorUsuarioCpf(user.getUsername());
			model.addAttribute("assistente", assistente);
		}

		Usuario usuario = usuarioService.buscarPorCpf(user.getUsername());
		
		
		model.addAttribute("usuario", usuario);
		return "assistente/cadastro"; 
	}
	
	// Salvar assistente
	@PostMapping({ "/salvar" }) 
	public String salvar(Assistente assistente, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		
		Usuario usuario = usuarioService.buscarPorCpf(user.getUsername());
		usuario.setAtivo(true);		
		usuarioService.salvarEdicaoUsuario(usuario);
		
		if (assistente.hasNotId() && assistente.getUsuario().hasNotId()) {
			assistente.setUsuario(usuario);		
		}
		
		service.salvar(assistente);
		attr.addFlashAttribute("sucesso", "Assistente salvo com sucesso");
		attr.addFlashAttribute("assistente", assistente);
		return "redirect:/assistentes/dados";
	}
	

	// Editar Assistente
	@PostMapping({ "/editar" })
	public String editar(Assistente assistente, RedirectAttributes attr) {
		Usuario usuario = usuarioService.buscarPorId(assistente.getUsuario().getId());
		usuario.setAtivo(true);
		
		service.editar(assistente);
		attr.addFlashAttribute("sucesso", "Assistente editado com sucesso");
		attr.addFlashAttribute("assistente", assistente);
		return "redirect:/assistentes/dados";
	}
	
	// Abrir pagina de notificação de candidatos
	@GetMapping({ "/notifica/candidatos/pagina" })
	public String paginaNotificaCandidatos(Model model) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		LocalDateTime dataTimeAtual = LocalDateTime.now();
		LocalTime localTime = LocalTime.now();
//		LocalDateTime timeAtual = LocalDateTime.parse("", DateTimeFormatter.ofPattern("HH-mm-ss"));
		
		String hojeFormatado = localTime.format(formatter);
		
		System.out.println("dataTimeAtual: " + dataTimeAtual);
		System.out.println("localTime: " + localTime);
		
		System.out.println("hora: " + hojeFormatado);
		
		List<Usuario> usersNaoInscritos = usuarioService.obterListaNaoInscrito();
		Integer quantidadeNaoInscritos = usersNaoInscritos.size();
		
//		List<Registro> emailsEnviados = registroService.buscarRegistrosPeloTitulo("Email Enviado");
		
		List<Registro> emailsEnviados = registroService.buscarRegistrosPeloTituloEData("Email Enviado");
		
		Integer qtdEmailsEnviados = emailsEnviados.size();

		model.addAttribute("qtdEmailsEnviados", qtdEmailsEnviados);
		model.addAttribute("quantidadeNaoInscritos", quantidadeNaoInscritos);
		model.addAttribute("emailsEnviados", emailsEnviados);
		return "assistente/notifica-candidatos"; 
	}
	
	// Abrir lista de inscricoes
		@GetMapping("/backup") 
		public String verEstatisticas(Model model) {
			
			List<Curso> listaCursos = cursoService.obterLista();
			List<Inscricao> listaInscricao = inscricaoService.obterLista();
			List<Candidato> listaCandidatos = candidatoService.obterLista();
			List<Assistente> listaAssistentes = assistenteService.obterLista();
			List<Registro> listaRegistros = registroService.obterLista();
			
			LocalDateTime dataTimeAtual = LocalDateTime.now();
			
			
			model.addAttribute("dataTimeAtual", dataTimeAtual);
			
			model.addAttribute("cursos", listaCursos);
			model.addAttribute("assistentes", listaAssistentes);
			model.addAttribute("candidatos", listaCandidatos);
			model.addAttribute("inscricoes", listaInscricao);
			model.addAttribute("registros", listaRegistros);
			
			return "assistente/backup";
		}

}



