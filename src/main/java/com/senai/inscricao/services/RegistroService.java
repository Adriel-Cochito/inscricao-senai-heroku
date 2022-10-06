package com.senai.inscricao.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.datatables.Datatables;
import com.senai.inscricao.datatables.DatatablesColunas;
import com.senai.inscricao.domains.Registro;
import com.senai.inscricao.repositories.RegistroRepository;

@Service
public class RegistroService {

	@Autowired
	private RegistroRepository repository;
	@Autowired
	private Datatables datatables;
	
	@Autowired
	private UsuarioService usuarioService;

	@Transactional(readOnly = true)
	public Registro buscarPorId(Long id) {
		return repository.findById(id).orElse(new Registro());
	}

	@Transactional(readOnly = false)
	public void salvar(Registro registro) {
		LocalDate dataAtual = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		
		registro.setDataRegistro(dataAtual);
		registro.setHorarioRegistro(localTime);
		
		repository.save(registro);
		System.out.println("Registro salvo! ");

	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.REGISTRO);
		Page<Registro> page = datatables.getSearch().isEmpty() ? repository.findAll(datatables.getPageable())
				: repository.findByTituloOrDescricao(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	public List<Registro> obterLista() { 
		return (List<Registro>)repository.findAll(); 
	}
	
	@Transactional(readOnly = true)
	public List<Registro> buscarRegistrosPeloTitulo(String titulo) {
		// TODO Auto-generated method stub
		return repository.findRegistrosByTitulo(titulo);
	}
	
	@Transactional(readOnly = true)
	public List<Registro> buscarRegistrosPeloTituloEData(String titulo) {

		LocalDate dataAtual = LocalDate.now();
//		dataAtual = LocalDate.parse("2022-09-23", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
//		LocalDate dataAtual = LocalDate.parse("2022-09-23", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		return repository.findRegistrosByTituloAndDate(titulo, dataAtual);
	}
	
	
}
