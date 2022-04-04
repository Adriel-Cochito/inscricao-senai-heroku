	package com.senai.inscricao.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "cursos")
public class Curso extends AbstractEntity {


	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "turno", nullable = false)
	private Integer turno;
	
	@Column(name = "cargaHoraria", nullable = false)
	private Double cargaHoraria;
	
	@Column(name = "ativo", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean ativo;
	
//	public Curso() {
//		super();
//	}
//
//	public Curso(Long id) {
//		super.setId(id);
//	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getTurno() {
		return turno;
	}

	public void setTurno(Integer turno) {
		this.turno = turno;
	}

	public Double getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Double cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}


	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	
}
