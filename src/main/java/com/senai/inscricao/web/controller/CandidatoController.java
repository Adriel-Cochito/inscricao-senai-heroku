package com.senai.inscricao.web.controller;

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

import com.senai.inscricao.domains.Candidato;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.services.CandidatoService;
import com.senai.inscricao.services.InscricaoService;
import com.senai.inscricao.services.UsuarioService;

@Controller
@RequestMapping("candidatos")
public class CandidatoController {

	@Autowired
	private CandidatoService service;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private InscricaoService inscricaoService;

	// Abrir pagina de dados pessoais de medicos pelo CANDIDATO
	@GetMapping({ "/dados" })
	public String abrirPorCandidato(Candidato candidato, ModelMap model,RedirectAttributes attr, @AuthenticationPrincipal User user) {
		if (service.buscarPorUsuarioCpf(user.getUsername()).getId() == null) {
			
			System.out.println("Candidato SEM ID ! ! ! ");
			
		} else {
			model.addAttribute("nomeFamiliar", "Nome");
			model.addAttribute("valor", 0);
			candidato = service.buscarPorUsuarioCpf(user.getUsername());
			model.addAttribute("candidato", candidato);
			model.addAttribute("listaFamiliares", candidato.getFamiliares());
			System.out.println("Candidato COM ID ! ! ! ");
		}
		
		Usuario usuario = usuarioService.buscarPorCpf(user.getUsername());
		model.addAttribute("usuario", usuario);

		return "candidato/cadastro";
	}
	
	// Pre edição de credenciais de usuarios
	@GetMapping("/dados/{id}")
	public ModelAndView dadosCandidatoPorInscricao(@PathVariable("id") Long id, ModelMap model) {
		
		model.addAttribute("listaFamiliares", service.buscarPorCandidatoId(id).getFamiliares());
		
		System.out.print("Id: ");
		System.out.println(id);
		return new ModelAndView("candidato/cadastro", "candidato", service.buscarPorCandidatoId(id));
	} 

	// Pre edição de credenciais de usuarios
	@GetMapping("/dados/usuario/{id}")
	public ModelAndView dadosCandidatoPorUsuarioId(@PathVariable("id") Long id, ModelMap model) {	
		
		model.addAttribute("listaFamiliares", service.buscarPorUsuarioId(id).getFamiliares());
		
		System.out.print("Id: ");
		System.out.println(id);
		return new ModelAndView("candidato/cadastro", "candidato", service.buscarPorUsuarioId(id));
	}
	
	// Salvar assistente
	@PostMapping({ "/salvar" })
	public String salvar(Candidato candidato, RedirectAttributes attr,@AuthenticationPrincipal User user) {
		
		Usuario us = usuarioService.buscarPorCpf(user.getUsername());
		
		
		if (candidato.hasNotId() && candidato.getUsuario().hasNotId()) {
			candidato.setUsuario(us);
		}
		
		service.salvar(candidato);
		us.setAtivo(true);		
		usuarioService.salvarEdicaoUsuario(us);
		
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		attr.addFlashAttribute("candidato", candidato);
		
		return "redirect:/home";
	}
	

	// Editar Assistente
	@PostMapping({ "/editar" })
	public String editar(Candidato candidato, RedirectAttributes attr) {

		service.editar(candidato);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		attr.addFlashAttribute("candidato", candidato);
		attr.addFlashAttribute("listaFamiliares", candidato.getFamiliares());
		System.out.println(candidato);
		return "redirect:/candidatos/dados";
	}
	
}

