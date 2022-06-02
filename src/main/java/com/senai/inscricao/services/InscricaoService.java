package com.senai.inscricao.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.datatables.Datatables;
import com.senai.inscricao.datatables.DatatablesColunas;
import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.exception.AcessoNegadoException;
import com.senai.inscricao.repositories.InscricoesRepository;
import com.senai.inscricao.repositories.projection.HistoricoCandidato;
import com.senai.inscricao.repositories.projection.HistoricoInscricoes;

@Service
public class InscricaoService {

	@Autowired
	private InscricoesRepository repository;
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Inscricao inscricao) {
		
		repository.save(inscricao);
	}
	
	@Transactional(readOnly = true)
	public Inscricao buscarPorId(Long id) {
		return repository.findById(id).orElse(new Inscricao());
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.INSCRICOES);
		Page<Inscricao> page = datatables.getSearch().isEmpty() ? repository.findAll(datatables.getPageable())
				: repository.findByCursoOrCandidato(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarHistoricoPorCandidatoCpf(String cpf, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.INSCRICOES);
		Page<HistoricoCandidato> page = repository.findHistoricoByCandidatoCpf(cpf, datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarInscricoesPorCurso(Long id, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.INSCRICOES);
		Page<HistoricoInscricoes> page = repository.findInscricoesPorCursoId(datatables.getSearch(), id, datatables.getPageable());
		return datatables.getResponse(page);
	}
	

	public List<Inscricao> obterLista() { 
		return (List<Inscricao>)repository.findAll(); 
	}
	
	@Transactional(readOnly = true)
	public Inscricao buscarPorIdECandidato(Long id, String cpf) {
		
		return repository
				.findByIdAndCandidato(id, cpf)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + cpf));
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<Inscricao> buscarInscricoesPorCursoSituacao(Long id) {

		return repository.findInscricoesPorCursoSituacao(id);
	}

	@Transactional(readOnly = true)
	public Inscricao buscarInscricoescpf(String cpf) {

		return repository.buscarInscricoescpf(cpf);
	}


}
