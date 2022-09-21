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
	
	@Column(name = "liberaResultados", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean liberaResultados;
	
	@Column(name = "vagas", nullable = false)
	private Integer vagas;
	
	@Column(name = "qtdSelecionados", nullable = true)
	private Integer qtdSelecionados;
	
	@Column(name = "msgAprovado", nullable = false)
	private String msgAprovado;
	
	@Column(name = "msgReprovado", nullable = false)
	private String msgReprovado;
	
	
	
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

	public boolean isLiberaResultados() {
		return liberaResultados;
	}

	public void setLiberaResultados(boolean liberaResultados) {
		this.liberaResultados = liberaResultados;
	}

	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

	public Integer getQtdSelecionados() {
		return qtdSelecionados;
	}

	public void setQtdSelecionados(Integer qtdSelecionados) {
		this.qtdSelecionados = qtdSelecionados;
	}

	public String getMsgAprovado() {
		return msgAprovado;
	}

	public void setMsgAprovado(String msgAprovado) {
		this.msgAprovado = msgAprovado;
	}

	public String getMsgReprovado() {
		return msgReprovado;
	}

	public void setMsgReprovado(String msgReprovado) {
		this.msgReprovado = msgReprovado;
	}
	
	
}
