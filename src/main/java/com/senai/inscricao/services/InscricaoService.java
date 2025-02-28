package com.senai.inscricao.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.datatables.Datatables;
import com.senai.inscricao.datatables.DatatablesColunas;
import com.senai.inscricao.domains.Curso;
import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.domains.Usuario;
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
		Inscricao inscricao = repository.buscarInscricoescpf(cpf);
		return inscricao;
	}
	

	public Curso buscarCursoPorInscricaoCpf(String cpf) {
		Curso curso = repository.findCursoPorInscricaoCpf(cpf);
		return curso;
	}

	public List<String> buscarBairrosDistintos(List<Inscricao> listaInscricao) {
		List<String> todosBairros = new ArrayList<String>();
		List<String> mapBairros = new ArrayList<String>();
		Map<String, Integer> hm = new HashMap<String, Integer>();
		
		for (Inscricao inscricao : listaInscricao) {
			String br = inscricao.getCandidato().getBairro().toUpperCase();
			todosBairros.add(br);
		}
		
		// hashmap to store the frequency of element
        for (String i : todosBairros) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        // displaying the occurrence of elements in the arraylist
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            mapBairros.add( val.getKey()
                    + ": " + val.getValue() + " inscrições");
        }
        
		return mapBairros;
	}


}
