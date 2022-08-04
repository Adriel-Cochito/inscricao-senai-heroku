package com.senai.inscricao.web.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.domains.Perfil;
import com.senai.inscricao.domains.PerfilTipo;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.InscricaoService;
import com.senai.inscricao.services.UsuarioService;

@Controller
public class HomeController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private InscricaoService inscricaoService;

	// abrir pagina home
		@GetMapping({ "/", "/home" })
		public String home(@AuthenticationPrincipal User user) {
			
			try {
				Usuario us = usuarioService.buscarPorCpf(user.getUsername());

				if (us.getPerfis().contains(new Perfil(PerfilTipo.CANDIDATO.getCod()))) {
					Integer status = us.getStatusCadastro();
					if (status == 0) {
						return "redirect:/candidatos/dados";
					} else if (status == 1) {
						return "redirect:/inscricoes/inscrever";
					} else if (status == 2) {
						return "redirect:/inscricoes/historico/candidato";
					} else {
						return "home-candidato";
					}
					
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
	
	// abrir pagina login
	@GetMapping({ "/login/admin" })
	public String loginAdmin() {
		return "login-admin";
	}
	

	
	// abrir pagina login
		@GetMapping({ "/ajuda" })
		public String ajuda() {
			return "ajuda";
		}
		
		
		// abrir pagina login
				@GetMapping({ "/criar/admin" })
				public String criarAdmin() {
					Usuario usuario = new Usuario();
					List<Perfil> perfil = Arrays.asList(new Perfil(1L), new Perfil(2L));
					LocalDate dataAtual = LocalDate.now();
					usuario.setPerfis(perfil);
					usuario.setAtivo(true);
					usuario.setCpf("12345678");
					usuario.setEmail("123@123");
					usuario.setDtInscricao(dataAtual);
					usuario.setSenha("123123");
					usuario.setStatusCadastro(0);	
					try {
						usuarioService.salvarUsuario(usuario);
					} catch (Exception e) {
						System.out.println("Erro ao criar admin");
					}
						
					return "ajuda";
				}
	
//	// abrir pagina recuperação de senha
//		@GetMapping({ "/recuperar-senha" })
//		public String recuperarSenha() {
//			return "/resetar-senha";
//		}

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