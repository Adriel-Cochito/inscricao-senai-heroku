package com.senai.inscricao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.domains.Assistente;
import com.senai.inscricao.domains.Candidato;
import com.senai.inscricao.repositories.AssistenteRepository;

@Service
public class AssistenteService {

	@Autowired
	private AssistenteRepository repository;

	@Transactional(readOnly = true)
	public Assistente buscarPorUsuarioId(Long id) {
		return repository.findByUsuarioId(id).orElse(new Assistente());
	}

	@Transactional(readOnly = false)
	public void salvar(Assistente Assistente) {
		repository.save(Assistente);

	}

	@Transactional(readOnly = false)
	public void editar(Assistente Assistente) {
		Assistente assistente = repository.findById(Assistente.getId()).get();
		assistente.setRe(Assistente.getRe());
		assistente.setNome(Assistente.getNome());

	}

	@Transactional(readOnly = true)
	public Assistente buscarPorUsuarioCpf(String cpf) {
		return repository.findByUsuarioCpf(cpf).orElse(new Assistente());
	}

	public List<Assistente> obterLista() { 
		return (List<Assistente>)repository.findAll(); 
	}

}



