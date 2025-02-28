package com.senai.inscricao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.domains.Registro;

public interface RegistroRepository  extends JpaRepository<Registro, Long> {

	@Query("select r from Registro r where r.id = :id")
	Optional<Registro> findById(Long id);
	

	@Query("select r "
		+ "from Registro r "
		+ "where r.titulo = :titulo")
	public List<Registro> findRegistrosByTitulo(String titulo);

	@Query("select distinct r from Registro r "+
			"where r.titulo like :search% "+
			"OR r.dataRegistro like :search% "+
			"OR r.horarioRegistro like :search%")
	public Page<Registro> findByTituloOrDescricao(@Param("search") String search, Pageable pageable);

	@Query("select r "
			+ "from Registro r "
			+ "where r.titulo = :titulo "
			+ "and r.dataRegistro = :data")
	List<Registro> findRegistrosByTituloAndDate(String titulo, LocalDate data);

	
	 
}
