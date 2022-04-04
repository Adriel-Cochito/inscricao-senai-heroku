package com.senai.inscricao.domains;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "assistentes")
public class Assistente extends AbstractEntity {

	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "re", unique = true, nullable = false)
	private Integer re;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Assistente() {
		super();
	}
	
	public Assistente(Long id) {
		super.setId(id);
	}
	
	public Assistente(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getRe() {
		return re;
	}

	public void setRe(Integer re) {
		this.re = re;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
