package com.senai.inscricao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.senai.inscricao.domains.Assistente;

public interface AssistenteRepository extends JpaRepository<Assistente, Long>{

	@Query("select a from Assistente a where a.usuario.id = :id")
	Optional<Assistente> findByUsuarioId(Long id);

	@Query("select a from Assistente a where a.usuario.cpf like :cpf")
	Optional<Assistente> findByUsuarioCpf(String cpf);

}
