package com.senai.inscricao.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.repositories.projection.HistoricoCandidato;

public interface InscricoesRepository extends JpaRepository<Inscricao, Long>{


	@Query("select a.id as id,"
			+ "a.candidato as candidato,"
			+ "a.curso as curso "
		+ "from Inscricao a "
		+ "where a.candidato.usuario.cpf like :cpf")
	Page<HistoricoCandidato> findHistoricoByCandidatoCpf(String cpf, Pageable pageable);

	
//	@Query("select distinct u from Usuario u "+
//			" join u.perfis p "+
//			"where u.cpf like :search% OR p.desc like :search%")
	
	@Query("select distinct i from Inscricao i "+
			" join i.candidato c "+
			"where i.curso.titulo like :search% OR c.nome like :search% OR c.rendaPercapta like :search% OR c.bairro like :search%")
	public Page<Inscricao> findByCursoOrCandidato(@Param("search") String search, Pageable pageable);


	@Query("select i from Inscricao i "
			+ "where "
			+ "	(i.id = :id AND i.candidato.usuario.cpf like :cpf) ")
	Optional<Inscricao> findByIdAndCandidato(Long id, String cpf);

}
