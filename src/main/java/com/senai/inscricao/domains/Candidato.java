package com.senai.inscricao.domains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "candidatos")
public class Candidato extends AbstractEntity {
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "bairro", nullable = false)
	private String bairro;
	
	@Column(name = "cidade", nullable = false)
	private String cidade;
	
	@Column(name = "dtNascimento", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dtNascimento;
	
	@Column(name = "telefone", nullable = false)
	private String telefone;
	
	@Column(name = "rendaPercapta", nullable = false)
	private Double rendaPercapta;
	
	@Column(name = "familiares", nullable = false)
	private String familiares;
	
	@JsonIgnore
	@OneToMany(mappedBy = "candidato")
	private List<Inscricao> inscricao;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_usuario", unique = true)
	private Usuario usuario;

	public Candidato() {
		super();
	}
	
	public Candidato(Long id) {
		super.setId(id);
	}
	
	public Candidato(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public LocalDate getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Double getRendaPercapta() {
		return rendaPercapta;
	}

	public void setRendaPercapta(Double rendaPercapta) {
		this.rendaPercapta = rendaPercapta;
	}


	public List<Inscricao> getInscricao() {
		return inscricao;
	}

	public void setInscricao(List<Inscricao> inscricao) {
		this.inscricao = inscricao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getFamiliares() {
		return familiares;
	}

	public void setFamiliares(String familiares) {
		this.familiares = familiares;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	
	
}
