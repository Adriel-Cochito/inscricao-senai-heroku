package com.senai.inscricao.web.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

import com.senai.inscricao.domains.Assistente;
import com.senai.inscricao.domains.Perfil;
import com.senai.inscricao.domains.PerfilTipo;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.AssistenteService;
import com.senai.inscricao.services.UsuarioService;

@Controller
@RequestMapping("u")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private AssistenteService assistenteService;

	// Abrir cadstro de usuarios (assistente/admin/paciente)
	@GetMapping("/novo/cadastro/usuario")
	public String cadastroCandidato(Usuario usuario) {

		return "usuario/cadastro";
	}

	@GetMapping("/admin/novo/cadastro/usuario")
	public String cadastroPorAdminParaAdminAssistenteCandidato(Usuario usuario) {

		return "usuario/cadastro-admin";
	}

	// Abrir lista de usuarios
	@GetMapping("/lista")
	public String listarUsuarios(RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Usuario us = service.buscarPorCpf(user.getUsername());

		if (us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod()))) {
			attr.addFlashAttribute("isAdmin", "true");
			return "usuario/lista";
		} else {
			return "usuario/lista-simples";
		}

	}

	// Listar sUsuarios na tebela
	@GetMapping("/datatables/server/usuarios")
	public ResponseEntity<?> listarUsuariosDataTables(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarTodos(request));
	}

	// Salvar cadastro ussuarios por administrador
	@PostMapping("/cadastro/salvar")
	public String salvarUsuarios(Usuario usuario, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		List<Perfil> perfis = usuario.getPerfis();
		LocalDate dataAtual = LocalDate.now();

		if (usuario.getDtInscricao() == null) {
			usuario.setDtInscricao(dataAtual);
			System.out.println("DATA nula!! ");
		}

		System.out.println("DATA ATUAL: ");
		System.out.println(dataAtual);

		if (perfis.size() > 2 || perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(3L)))
				|| perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(3L)))) {
			attr.addFlashAttribute("falha", "Candidato não pode ser Admin e/ou Assistente");
			attr.addFlashAttribute("usuario", usuario);
		} else {
			try {
//				if (usuario.getEmail() == null) {
//					usuario.setEmail("");
//				}
				service.salvarUsuario(usuario);
				attr.addFlashAttribute("sucesso", "Cadastro realizad com sucesso! Agora entre com Login e Senha cadastrados");
			} catch (Exception e) {
				attr.addFlashAttribute("falha", "Cadastro não realizado, CPF já existente ou dados inválidos");
			}
		}

		try {
			if (user.isAccountNonExpired()) {

				Usuario us = service.buscarPorCpf(user.getUsername());

				if (us.getPerfis().contains(new Perfil(PerfilTipo.CANDIDATO.getCod()))) {
					return "redirect:/home";
				} else {
					return "redirect:/assistentes/dados";
				}

			}
		} catch (Exception e) {
			System.out.println("Usuario NÂO logado!!");
			return "redirect:/login";
		}
		return null;

	}

	// Salvar cadastro ussuarios por administrador
	@PostMapping("/admin/cadastro/salvar")
	public String salvarUsuariosPorAdmin(Usuario usuario, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		List<Perfil> perfis = usuario.getPerfis();
		LocalDate dataAtual = LocalDate.now();

		if (usuario.getDtInscricao() == null) {
			usuario.setDtInscricao(dataAtual);
			System.out.println("DATA nula!! ");
		}

		System.out.println("DATA ATUAL: ");
		System.out.println(dataAtual);

		if (perfis.size() > 2 || perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(3L)))
				|| perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(3L)))) {
			attr.addFlashAttribute("falha", "Candidato não pode ser Admin e/ou Assistente");
			attr.addFlashAttribute("usuario", usuario);
		} else {
//			try {
//				if (usuario.getEmail() == null) {
//				}
//
//			} catch (Exception e) {
//				usuario.setEmail("");
//				attr.addFlashAttribute("falha", "Cadastro não realizado, Dados inválidos");
//			}
			
			try {
				
				service.salvarUsuario(usuario);

				attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
			} catch (Exception e) {
				attr.addFlashAttribute("falha", "Cadastro não realizado, CPF já existente ou dados inválidos");
				return "redirect:/u/admin/novo/cadastro/usuario";
			}
		}

		return "redirect:/u/lista";

	}

	// Pre edição de credenciais de usuarios
	@GetMapping("/editar/credenciais/usuario/{id}")
	public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {

		return new ModelAndView("usuario/cadastro-admin", "usuario", service.buscarPorId(id));
	}

	// Pre edição de credenciais de usuarios
	@GetMapping("/editar/dados/usuario/{id}")
	public ModelAndView preEditarDados(@PathVariable("id") Long id) {
		return new ModelAndView("assistente/cadastro", "assistente", assistenteService.buscarPorUsuarioId(id));
	}

	// Pre edição de cadastro de usuarios
	@GetMapping("/editar/dados/usuario/{id}/perfis/{perfis}")
	public ModelAndView preEditarCadastroDadosPessoais(@PathVariable("id") Long usuarioId, RedirectAttributes attr,
			@PathVariable("perfis") Long[] perfisId) {

		try {
			Usuario us = service.buscarPorIdEPerfis(usuarioId, perfisId);

			if (us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod()))
					&& !us.getPerfis().contains(new Perfil(PerfilTipo.ASSISTENTE.getCod()))) {
				return new ModelAndView("usuario/cadastro", "usuario", us);
			} else if (us.getPerfis().contains(new Perfil(PerfilTipo.ASSISTENTE.getCod()))) {
				Assistente assistente = assistenteService.buscarPorUsuarioId(usuarioId);

				return assistente.hasNotId()
						? new ModelAndView("assistente/cadastro-pessoal", "assistente",
								new Assistente(new Usuario(usuarioId)))
						: new ModelAndView("assistente/cadastro-pessoal", "assistente", assistente);

			}
//			else if (us.getPerfis().contains(new Perfil(PerfilTipo.CANDIDATO.getCod()))) {
//				ModelAndView model = new ModelAndView("error");
//
//				model.addObject("status", 403);
//				model.addObject("error", "Área restrita");
//				model.addObject("message", "Os dados cadastrais de candidato são restritos a ele");
//				return model;
//			}
		} catch (IllegalStateException e) {
			attr.addFlashAttribute("falha", "Dados pessoais indisponíveis ou ausentes");
			return new ModelAndView("redirect:/u/lista");

		}

		return new ModelAndView("redirect:/u/lista");
	}

	// Editar a senha de usuario
	@GetMapping("/editar/senha")
	public String abrirEditarSenha() {

		return "usuario/editar-senha";
	}

	// Confirmar a senha
	@PostMapping("/confirmar/senha")
	public String editarSenha(@RequestParam("senha1") String s1, @RequestParam("senha2") String s2,
			@RequestParam("senha3") String s3, @AuthenticationPrincipal User user, RedirectAttributes attr) {

		if (!s1.equals(s2)) {
			attr.addFlashAttribute("falha", "Senhas não conferem, tente novamente");
			return "redirect:/u/editar/senha";
		}

		Usuario u = service.buscarPorCpf(user.getUsername());
		if (!UsuarioService.isSenhaCorreta(s3, u.getSenha())) {
			attr.addFlashAttribute("falha", "Senha atual não confere, tente novamente");
			return "redirect:/u/editar/senha";
		}

		service.alterarSenha(u, s1);
		attr.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
		return "redirect:/u/editar/senha";
	}

}
