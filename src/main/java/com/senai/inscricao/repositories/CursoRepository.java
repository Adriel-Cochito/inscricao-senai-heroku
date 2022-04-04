package com.senai.inscricao.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senai.inscricao.domains.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	@Query("select c from Curso c where c.id = :id")
	Optional<Curso> findById(Long id);

	@Query("select c from Curso c where c.titulo IN :titulos")
	Set<Curso> findByTitulos(String[] titulos);

	@Query("select distinct c from Curso c "+
			"where c.titulo like :search%")
	public Page<Curso> findByTituloOrTurno(@Param("search") String search, Pageable pageable);

	@Query("select c.titulo from Curso c where c.titulo like :termo%")
	List<String> findCursoByTermo(String termo);

}
