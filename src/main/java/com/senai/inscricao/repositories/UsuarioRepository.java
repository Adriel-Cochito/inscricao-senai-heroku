package com.senai.inscricao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senai.inscricao.domains.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.cpf like :cpf")
	Optional<Usuario> findByCpf(@Param("cpf") String cpf);

	@Query("select distinct u from Usuario u "+
			" join u.perfis p "+
			"where u.cpf like :search% OR p.desc like :search% OR u.email like :search% OR u.inscricao like :search%")
	public Page<Usuario> findByCpfOrPerfil(@Param("search") String search, Pageable pageable);

	
	@Query("select distinct u from Usuario u "+
			" join u.perfis p "+
			"where u.id = :usuarioId AND p.id IN :perfisId")
	Optional<Usuario> findByIdAndPerfis(Long usuarioId, Long[] perfisId);

	@Query("select u from Usuario u where u.inscricao like :inscricao")
	List<Usuario> findListaNaoInscritos(String inscricao);
}
