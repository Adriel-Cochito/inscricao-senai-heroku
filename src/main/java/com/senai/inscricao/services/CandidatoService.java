package com.senai.inscricao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.domains.Candidato;
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
		System.out.println("Salvando Candidato");
		repository.save(candidato);		
	}
	
	@Transactional(readOnly = false)
	public void editar(Candidato candidato) {
		System.out.println("Editando Candidato");
		Candidato candidato1 = repository.findById(candidato.getId()).get();
		candidato1.setNome(candidato.getNome());
		candidato1.setDtNascimento(candidato.getDtNascimento());
		candidato1.setNome(candidato.getNome());
		candidato1.setCep(candidato.getCep());
		candidato1.setZonaRural(candidato.isZonaRural());
		candidato1.setBairro(candidato.getBairro());
		candidato1.setCidade(candidato.getCidade());
		candidato1.setRendaPercapta(candidato.getRendaPercapta());
		candidato1.setTelefone(candidato.getTelefone());
		candidato1.setFamiliares(candidato.getFamiliares());
		
		candidato1.setEndereco(candidato.getFamiliares());
		candidato1.setEscola(candidato.getFamiliares());
		candidato1.setEscolaridade(candidato.getEscolaridade());
		candidato1.setEstadoCivil(candidato.getEstadoCivil());
		candidato1.setFiliacaoPai(candidato.getFiliacaoPai());
		candidato1.setFiliacaoMae(candidato.getFiliacaoMae());
		candidato1.setNaturalidade(candidato.getNaturalidade());
		candidato1.setRaca(candidato.getRaca());
		candidato1.setRg(candidato.getRg());
		candidato1.setUf(candidato.getUf());

	}

	public List<Candidato> obterLista() { 
		return (List<Candidato>)repository.findAll(); 
	}
	
}