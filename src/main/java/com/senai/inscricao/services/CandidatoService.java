package com.senai.inscricao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.domains.Candidato;
import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.repositories.CandidatoRepository;

@Service
public class CandidatoService {

	@Autowired
	private CandidatoRepository repository;
	
	@Transactional(readOnly = true)
	public Candidato buscarPorUsuarioCpf(String cpf) {
		return repository.findByUsuarioCpf(cpf).orElse(new Candidato());
	}

	@Transactional(readOnly = true)
	public Candidato buscarPorUsuarioId(Long id) {
		return repository.findByUsuarioId(id).orElse(new Candidato());
	}
	
	@Transactional(readOnly = true)
	public Candidato buscarPorCandidatoId(Long id) {
		return repository.findById(id).orElse(new Candidato());
	}

	
	@Transactional(readOnly = false)
	public void salvar(Candidato candidato) {
		repository.save(candidato);		
	}
	
	@Transactional(readOnly = false)
	public void editar(Candidato candidato) {
		Candidato candidato1 = repository.findById(candidato.getId()).get();
		candidato1.setNome(candidato.getNome());
		candidato1.setDtNascimento(candidato.getDtNascimento());
		candidato1.setNome(candidato.getNome());
		candidato1.setBairro(candidato.getBairro());
		candidato1.setRendaPercapta(candidato.getRendaPercapta());
		candidato1.setTelefone(candidato.getTelefone());

	}

	public List<Candidato> obterLista() { 
		return (List<Candidato>)repository.findAll(); 
	}
	
}