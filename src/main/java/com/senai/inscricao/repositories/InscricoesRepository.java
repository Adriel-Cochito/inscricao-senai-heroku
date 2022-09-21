package com.senai.inscricao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senai.inscricao.domains.Curso;
import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.repositories.projection.HistoricoCandidato;
import com.senai.inscricao.repositories.projection.HistoricoInscricoes;

public interface InscricoesRepository extends JpaRepository<Inscricao, Long>{


	@Query("select a.id as id,"
			+ "a.candidato as candidato,"
			+ "a.curso as curso, "
			+ "a.situacao as situacao "
		+ "from Inscricao a "
		+ "where a.candidato.usuario.cpf like :cpf")
	Page<HistoricoCandidato> findHistoricoByCandidatoCpf(String cpf, Pageable pageable);
	
	@Query("select b.id as id,"
			+ "b.candidato as candidato,"
			+ "b.curso as curso, "
			+ "b.situacao as situacao "
		+ "from Inscricao b "
		+ "where b.curso.id like :id AND (b.candidato.nome like :search% OR b.candidato.rendaPercapta like :search% OR b.candidato.cidade like :search% OR b.candidato.bairro like :search% OR b.situacao like :search%)")
	public Page<HistoricoInscricoes> findInscricoesPorCursoId(@Param("search") String search, Long id, Pageable pageable);
	
	@Query("select b.id as id,"
			+ "b.candidato as candidato,"
			+ "b.curso as curso, "
			+ "b.situacao as situacao "
		+ "from Inscricao b "
		+ "where b.curso.id like :id")
	public List<Inscricao> findInscricoesPorCursoSituacao(Long id);
	
//	@Query("select b.id as id,"
//			+ "b.candidato as candidato,"
//			+ "b.curso as curso, "
//		+ "from Inscricao b "
//		+ "where b.curso.id like :id")
//	public List<Inscricao> findInscricoesPorCurso(Long id);
	
	
//	@Query("select distinct u from Usuario u "+ 
//			" join u.perfis p "+
//			"where u.cpf like :search% OR p.desc like :search%")
	
	@Query("select i from Inscricao i "+
			" join i.candidato c "+
			"where i.curso.titulo like :search% OR c.nome like :search% OR c.rendaPercapta like :search% OR c.bairro like :search% OR c.cidade like :search% OR i.situacao like :search%")
	public Page<Inscricao> findByCursoOrCandidato(@Param("search") String search, Pageable pageable);


	@Query("select i from Inscricao i "
			+ "where "
			+ "	(i.id = :id AND i.candidato.usuario.cpf like :cpf) ")
	Optional<Inscricao> findByIdAndCandidato(Long id, String cpf);

	@Query("select a.id as id,"
			+ "a.candidato as candidato,"
			+ "a.curso as curso, "
			+ "a.situacao as situacao "
		+ "from Inscricao a "
		+ "where a.candidato.usuario.cpf like :cpf")
	public Inscricao buscarInscricoescpf(String cpf);
	
	@Query("select curso from Inscricao i "
			+ "where i.candidato.usuario.cpf like :cpf ")
	public Curso findCursoPorInscricaoCpf(String cpf);


}
