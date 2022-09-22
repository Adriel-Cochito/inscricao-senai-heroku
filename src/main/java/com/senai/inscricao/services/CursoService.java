package com.senai.inscricao.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.datatables.Datatables;
import com.senai.inscricao.datatables.DatatablesColunas;
import com.senai.inscricao.domains.Curso;
import com.senai.inscricao.repositories.CursoRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository repository;
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = true)
	public Curso buscarPorId(Long id) {
		return repository.findById(id).orElse(new Curso());
	}

	@Transactional(readOnly = false)
	public void salvar(Curso curso) {
		String teste = null;
		try {
			if (curso.getQtdSelecionados().equals(teste)) {
//				System.out.println("Quantidade nula!!! ");
				curso.setQtdSelecionados(0);
			} else {
//				System.out.println("Quantidade NÃO nula!!! ");
			}
		} catch (Exception e) {
//			System.out.println("Erro. Qtd  nula!!! ");
			curso.setQtdSelecionados(0);
		}
		
		repository.save(curso);
	}

	@Transactional(readOnly = false)
	public void editar(Curso curso) {
		Curso curso1 = repository.findById(curso.getId()).get();
		curso1.setTitulo(curso1.getTitulo());
		curso1.setTurno(curso1.getTurno());
		curso1.setQtdSelecionados(curso1.getQtdSelecionados());
	}

	@Transactional(readOnly = true)
	public Set<Curso> buscarPorTitulos(String[] titulos) {
		return repository.findByTitulos(titulos);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CURSOS);
		Page<Curso> page = datatables.getSearch().isEmpty() ? repository.findAll(datatables.getPageable())
				: repository.findByTituloOrTurno(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	public List<Curso> obterLista() { 
		return (List<Curso>)repository.findAll();
	}

	@Transactional(readOnly = true)
	public List<String> buscarCursoPeloTermo(String termo) {
		// TODO Auto-generated method stub
		return repository.findCursoByTermo(termo);
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		
		repository.deleteById(id);
	}
 

}

