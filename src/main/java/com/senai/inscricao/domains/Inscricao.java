package com.senai.inscricao.domains;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@SuppressWarnings("serial")
@Entity
@Table(name = "inscricao") 
public class Inscricao extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name="id_curso") 
	private Curso curso; 
	
	@ManyToOne
	@JoinColumn(name="id_candidato", unique = true)
	private Candidato candidato;
	
	
	@Column(name = "situacao", nullable = false)
	private Integer situacao; 
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "data_inscricao", nullable = true)
	private LocalDate dtInscricao;

	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Candidato getCandidato() {
		return candidato;
	}


	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}


	public Integer getSituacao() {
		return situacao;
	}


	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}


	public LocalDate getDtInscricao() {
		return dtInscricao;
	}


	public void setDtInscricao(LocalDate dtInscricao) {
		this.dtInscricao = dtInscricao;
	}


	
}
