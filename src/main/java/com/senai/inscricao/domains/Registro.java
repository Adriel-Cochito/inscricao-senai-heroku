package com.senai.inscricao.domains;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@SuppressWarnings("serial")
@Entity
@Table(name = "registro")
public class Registro extends AbstractEntity {

	
	@Column(name = "titulo", nullable = true)
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@Column(name = "descricao", nullable = true)
	private String descricao;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "dataRegistro", nullable = true)
	private LocalDate dataRegistro;
	
	@Column(name = "horarioRegistro", nullable = true)
	private LocalTime horarioRegistro;
	
	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public LocalTime getHorarioRegistro() {
		return horarioRegistro;
	}

	public void setHorarioRegistro(LocalTime horarioRegistro) {
		this.horarioRegistro = horarioRegistro;
	}
	
	
}