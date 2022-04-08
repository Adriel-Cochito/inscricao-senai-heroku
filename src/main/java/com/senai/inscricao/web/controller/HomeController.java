package com.senai.inscricao.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.senai.inscricao.domains.Perfil;
import com.senai.inscricao.domains.PerfilTipo;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.UsuarioService;

@Controller
public class HomeController {

	@Autowired
	private UsuarioService usuarioService;

	// abrir pagina home
	@GetMapping({ "/", "/home" })
	public String home(@AuthenticationPrincipal User user) {
		try {
			Usuario us = usuarioService.buscarPorCpf(user.getUsername());

			if (us.getPerfis().contains(new Perfil(PerfilTipo.CANDIDATO.getCod()))) {
				return "home-candidato";
			} else if (us.getPerfis().contains(new Perfil(PerfilTipo.ASSISTENTE.getCod())) || us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod()))) {
				return "redirect:/inscricoes/estatisticas";
			} else {
				return "login";
			}
		} catch (Exception e) {
			return "login";
		}
		
	}

	// abrir pagina login
	@GetMapping({ "/login" })
	public String login() {
		return "login";
	}

	// abrir pagina cadastro
	@GetMapping({ "/cadastro" })
	public String cadastro(Usuario usuario) {
		return "cadastro";
	}

	// Login invalido
	@GetMapping({ "/login-error" })
	public String loginError(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Credenciais inválidas");
		model.addAttribute("texto", "Login incorreto, tente novamente");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
		return "login";
	}

	// Acesso negado
	@GetMapping({ "/acesso-negado" })
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão a esta área ou ação");
		return "error";
	}

}