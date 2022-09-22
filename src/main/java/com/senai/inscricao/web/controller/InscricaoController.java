package com.senai.inscricao.web.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.convert.JodaTimeConverters.DateToLocalDateTimeConverter;
import org.springframework.data.convert.JodaTimeConverters.LocalDateToDateConverter;
import org.springframework.data.convert.Jsr310Converters.LocalDateTimeToDateConverter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	// Confirmar a senha
	@PostMapping("/resultado") 
	public String consultaResultado(@RequestParam("cpf") String cpf, 
			RedirectAttributes attr) {
		
		System.out.println("cpf: "+cpf);
		
		try {
			Inscricao inscricao = service.buscarInscricoescpf(cpf);
//			System.out.println("Nome: "+inscricao.getCandidato().getNome());
//			System.out.println("CPF: "+inscricao.getCandidato().getUsuario().getCpf());
//			System.out.println("Situacao: "+inscricao.getSituacao());
		} catch (Exception e) {
			System.out.println("CPF nao encontrado");
			System.out.println(e);
		}
		
		
		return "redirect:/inscricoes/resultado";
	}
	
	// abre a pagina de inscricoes de consultas
	@GetMapping({ "/inscrever" })
	public String abrirInscricao(Inscricao inscricao, Model model, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		
//		if (!(usuarioService.buscarPorCpf(user.getUsername()).isAtivo()) || candidatoService.buscarPorUsuarioCpf(user.getUsername()).) {
//			model.addAttribute("isAtivo", usuarioService.buscarPorCpf(user.getUsername()).isAtivo());
//			attr.addFlashAttribute("sucesso", "Finalize o cadastro de dados pessoais para se inscrever nos cursos.");
//			return "inscricao/cadastro";
//		} else {
//			model.addAttribute("cursos", cursoService.obterLista());
//			return "inscricao/cadastro";
//		}
		
		try {
			Candidato candidato = candidatoService.buscarPorUsuarioCpf(user.getUsername());
			if (candidato.getId() != null) {
				model.addAttribute("cursos", cursoService.obterLista());
				return "inscricao/cadastro";
			} else {
				model.addAttribute("isAtivo", usuarioService.buscarPorCpf(user.getUsername()).isAtivo());
				attr.addFlashAttribute("sucesso", "Finalize o cadastro de dados pessoais para se inscrever nos cursos.");
				return "inscricao/cadastro";
			}
			
				
		} catch (Exception e) {
			model.addAttribute("isAtivo", usuarioService.buscarPorCpf(user.getUsername()).isAtivo());
			attr.addFlashAttribute("sucesso", "Finalize o cadastro de dados pessoais para se inscrever nos cursos.");
			return "inscricao/cadastro";
		}
		
	}

	// abrir pagina de historico de agendamento do candidato
	@GetMapping({"/historico/candidato"})
	public String historicoCandidato(Model model, @AuthenticationPrincipal User user) {
		try {
			System.out.println("getUsername: "  + user.getUsername());
			String cpf = user.getUsername();
			Curso curso = service.buscarCursoPorInscricaoCpf(cpf); 
			
			if (curso.isLiberaResultados()) {
//				System.out.println("Libera resultados true");
				
//					System.out.println("msgAprovado");
					model.addAttribute("msgAprovado", curso.getMsgAprovado());
//					System.out.println("msgReprovado");
					model.addAttribute("msgReprovado", curso.getMsgReprovado());
					model.addAttribute("status", "true");
				
			} else {
//				System.out.println("Libera resultados FALSE");
				model.addAttribute("status", "false");
			}
			
//			System.out.println("getMsgAprovado: " + curso.getMsgAprovado());
//			System.out.println("getMsgReprovado: " + curso.getMsgReprovado());
			
			
		} catch (Exception e) {
			System.out.println("Throw error");
		}
		
		
		return "inscricao/historico-candidato";
	}
	
	// abrir pagina de historico de agendamento do candidato
	@GetMapping({"/consultar"})
	public String historicoGeral() {
		return "inscricao/historico-geral";
	}
	
	
	// Abrir lista de inscricoes
	@GetMapping("/lista/{id}")
	public String listarInscricoes(@PathVariable("id") Long id,Model model) {
		
		List<Curso> listaCursos = cursoService.obterLista();
		List<Inscricao> listaInscricao = service.obterLista();
		
		
		List<String> tamanho = new ArrayList<String>();
		
		for (Curso curso : listaCursos) {
		    tamanho.add(curso.getTitulo());
		    
		}
		
//		if(id == 0) {
//		} else {
//			listaInscricao = service.buscarInscricoesPorCursoSituacao(id);
//		}
		
		model.addAttribute("idCurso", id);
		model.addAttribute("tituloCurso", cursoService.buscarPorId(id).getTitulo());
		
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
		List<String> tamanho = new ArrayList<String>();
		
		List<String> bairros = new ArrayList<String>();
		
		bairros = service.buscarBairrosDistintos(listaInscricao);
		
		for (Curso curso : listaCursos) {
		    tamanho.add(curso.getTitulo());
		    
		}
		
//		List<Usuario> userNaoInscrito = new ArrayList<Usuario>();
//		
//		
//		for (Usuario usuario : listaUsuarios) {
//			try {
//				Inscricao inscricao = service.buscarInscricoescpf(usuario.getCpf());
//				System.out.println("Candidato já INSCRITO");
//				userNaoInscrito.add(usuario);
//				usuario.setInscricao("nao-inscrito");
//				usuarioService.salvarEdicaoUsuario(usuario);
//			} catch (Exception e) {
//				usuario.setInscricao("inscrito");
//				usuarioService.salvarEdicaoUsuario(usuario);
//					System.out.println("Candidato sem inscricao");
//			}
//			
//		}
//		
//		model.addAttribute("listaUsuariosData", userNaoInscrito);
		
		model.addAttribute("bairros", bairros);
		
		model.addAttribute("tamanho", tamanho.size());
		model.addAttribute("quantidadeInscricao", listaInscricao.size());
		model.addAttribute("quantidadeCursos", listaCursos.size());
		model.addAttribute("quantidadeUsuarios", listaUsuarios.size());
		model.addAttribute("quantidadeCandidatos", listaCandidatos.size());
		
		model.addAttribute("cursos", listaCursos);
		
		model.addAttribute("inscricoes", listaInscricao);
		return "inscricao/estatisticas";
	}
	
	
	// Listar Usuarios na tabela
	@GetMapping("/datatables/server/lista/{id}")
	public ResponseEntity<?> listarInscricoesDataTables(@PathVariable("id") Long id,HttpServletRequest request) {
//		Curso curso = cursoService.buscarPorId(id);
//		
//		System.out.print("Imprimindo cursoId: ");
//		System.out.println(id);
//		System.out.println(curso.getTitulo());
		
		if(id == 0) {
			return ResponseEntity.ok(service.buscarTodos(request));
		} else {
			return ResponseEntity.ok(service.buscarInscricoesPorCurso(id, request));
		}
		
	}
	
	// localizar o historico de inscricoes por usuario logado
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoInscricoesPorCandidato(HttpServletRequest request, @AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.CANDIDATO.getDesc()))) {
			System.out.println("Historico  ");
			return ResponseEntity.ok(service.buscarHistoricoPorCandidatoCpf(user.getUsername(), request));
		}	
		
		return ResponseEntity.notFound().build();
	}
	
	// localizar o historico de inscricoes por usuario logado
	@GetMapping("/datatables/server/historicos")
	public ResponseEntity<?> historicoInscricoes(HttpServletRequest request, @AuthenticationPrincipal User user) {
			System.out.println("HistoricoS S ");
		return ResponseEntity.ok(service.buscarHistoricoPorCandidatoCpf(user.getUsername(), request));
	}
	
	
	
	// salvar um consulta agendada
	@PostMapping({ "/salvar" })
	public String salvar(Inscricao inscricao, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		try {
		LocalDate dataAtual = LocalDate.now();
		Candidato candidato = candidatoService.buscarPorUsuarioCpf(user.getUsername());
		String titulo = inscricao.getCurso().getTitulo();
		Curso curso= cursoService.buscarPorTitulos(new String[] { titulo }).stream()
				.findFirst().get();
		inscricao.setCurso(curso); 
		inscricao.setSituacao(0); 
		inscricao.setCandidato(candidato);
		inscricao.setDtInscricao(dataAtual);
		
		Usuario usuario = usuarioService.buscarPorCpf(user.getUsername());
		usuario.setInscricao("inscrito"); 
		usuario.setStatusCadastro(2);
		usuarioService.salvarEdicaoUsuario(usuario);
		
		} catch (Exception ex) {
			attr.addFlashAttribute("falha", "Selecione o curso desejado para finalizar sua inscrição");
			return "redirect:/inscricoes/inscrever";
		}
		
		try {
			service.salvar(inscricao);
			attr.addFlashAttribute("sucesso", "Sua Inscrição foi realizada com sucesso!");
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Você já possui uma inscrição, tente editar ou excluir para criar uma nova inscrição");
			return "redirect:/inscricoes/inscrever";
		}
		
		
		return "redirect:/inscricoes/historico/candidato";
	}

	
	// localizar agendamento pelo id e envia-lo para a pagina de cadastro
	@GetMapping("/editar/inscricao/{id}") 
	public String preEditarConsultaPaciente(@PathVariable("id") Long id, RedirectAttributes attr, 
										    ModelMap model, @AuthenticationPrincipal User user) {
		List<Curso> listaCursos = cursoService.obterLista();
		Inscricao inscricao= service.buscarPorIdECandidato(id, user.getUsername());
		
		if (inscricao.getCurso().isAtivo() == true) {
			model.addAttribute("cursos", listaCursos);
			model.addAttribute("inscricao", inscricao);
			return "inscricao/cadastro";
		} else {
			attr.addFlashAttribute("falha", "Não é possivel editar. O periodo de Inscrição e Edição foi finalizado.");
			return "redirect:/inscricoes/historico/candidato";
		}
		
	}
	
	@GetMapping("/aprovar/{id}")
	public String aprovarCandidato(@PathVariable("id") Long id, RedirectAttributes attr, 
										    ModelMap model, @AuthenticationPrincipal User user) {
		Inscricao inscricao = service.buscarPorId(id);
		inscricao.setSituacao(2);
		service.salvar(inscricao);
		
		Curso curso = inscricao.getCurso();
		curso.setQtdSelecionados(curso.getQtdSelecionados() + 1);
		cursoService.salvar(curso);
		
		attr.addFlashAttribute("sucesso", "Usuario aprovado com sucesso.");
		return "redirect:/inscricoes/lista/"+inscricao.getCurso().getId();
	} 
	
	@GetMapping("/remover/{id}") 
	public String removerAprovacaoCandidato(@PathVariable("id") Long id, RedirectAttributes attr,
										    ModelMap model, @AuthenticationPrincipal User user) {
		Inscricao inscricao = service.buscarPorId(id);
		inscricao.setSituacao(0);
		service.salvar(inscricao);
		
		Curso curso = inscricao.getCurso();
		curso.setQtdSelecionados(curso.getQtdSelecionados() - 1);
		cursoService.salvar(curso);
		
		attr.addFlashAttribute("sucesso", "Aprovação removida com sucesso.");
		return "redirect:/inscricoes/lista/"+inscricao.getCurso().getId();
	}
	
	@GetMapping("/excluir/inscricao/{id}")
	public String excluirConsulta(@PathVariable("id") Long id, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Inscricao inscricao= service.buscarPorIdECandidato(id, user.getUsername());
		
		if (inscricao.getCurso().isAtivo() == true) {
		service.remover(id);
		
		Usuario usuario = usuarioService.buscarPorCpf(user.getUsername());
		usuario.setInscricao("nao-inscrito");
		usuarioService.salvarEdicaoUsuario(usuario);
		
		attr.addFlashAttribute("sucesso", "Inscrição excluída com sucesso.");
		return "redirect:/inscricoes/historico/candidato";
		} else {
			attr.addFlashAttribute("falha", "Não é possivel excluir. O periodo de Inscrição e Edição foi finalizado.");
			return "redirect:/inscricoes/historico/candidato";
		}
	}


}
