package com.senai.inscricao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.senai.inscricao.domains.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
	
	@Query("select c from Candidato c where c.usuario.id = :id")
	Optional<Candidato> findByUsuarioId(Long id);
	
	@Query("select c from Candidato c where c.usuario.cpf like :cpf")
	Optional<Candidato> findByUsuarioCpf(String cpf);
}
