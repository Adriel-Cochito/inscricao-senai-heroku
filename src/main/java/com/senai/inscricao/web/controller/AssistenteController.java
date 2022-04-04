package com.senai.inscricao.web.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senai.inscricao.domains.Assistente;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.AssistenteService;
import com.senai.inscricao.services.UsuarioService;

@Controller
@RequestMapping("assistentes")
public class AssistenteController {

	@Autowired
	private AssistenteService service;

	@Autowired
	private UsuarioService usuarioService;

	// Abrir pagina de dados pessoais de medicos pelo MEDICO
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
		
		System.out.print("Ativo: ");
		System.out.println(usuario.isAtivo());
		
		service.salvar(assistente);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		attr.addFlashAttribute("assistente", assistente);
		return "redirect:/assistentes/dados";
	}

	// Editar Assistente
	@PostMapping({ "/editar" })
	public String editar(Assistente assistente, RedirectAttributes attr) {
		Usuario usuario = usuarioService.buscarPorId(assistente.getUsuario().getId());
		usuario.setAtivo(true);
		
		service.editar(assistente);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		attr.addFlashAttribute("assistente", assistente);
		System.out.println(assistente);
		return "redirect:/assistentes/dados";
	}


}



