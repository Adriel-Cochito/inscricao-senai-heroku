package com.senai.inscricao.domains;

import java.time.LocalDate;
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
	
	@Column(name = "cep", nullable = true)
	private String cep;
	
	@Column(name = "rg", nullable = true)
	private String rg;
	
	@Column(name = "naturalidade", nullable = true)
	private String naturalidade;
	
	@Column(name = "raca", nullable = true)
	private String raca;
	
	@Column(name = "endereco", nullable = true)
	private String endereco;
	
	@Column(name = "bairro", nullable = true)
	private String bairro;
	
	@Column(name = "estadoCivil", nullable = true)
	private String estadoCivil;
	
	@Column(name = "uf", nullable = true)
	private String uf;
	
	@Column(name = "cidade", nullable = true)
	private String cidade;
	
	@Column(name = "zonaRural", nullable = true, columnDefinition = "TINYINT(1)")
	private boolean zonaRural;
	
	@Column(name = "escolaridade", nullable = true)
	private String escolaridade;
	
	@Column(name = "escola", nullable = true)
	private String escola;
	
	@Column(name = "filiacaoPai", nullable = true)
	private String filiacaoPai;
	
	@Column(name = "filiacaoMae", nullable = true)
	private String filiacaoMae;
	
	@Column(name = "dtNascimento", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dtNascimento;
	
	@Column(name = "telefone", nullable = false)
	private String telefone;
	
	@Column(name = "rendaPercapta", nullable = true)
	private Double rendaPercapta;
	
	@Column(name = "familiares", nullable = true)
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public boolean isZonaRural() {
		return zonaRural;
	}

	public void setZonaRural(boolean zonaRural) {
		this.zonaRural = zonaRural;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getEscola() {
		return escola;
	}

	public void setEscola(String escola) {
		this.escola = escola;
	}


	public String getFiliacaoPai() {
		return filiacaoPai;
	}

	public void setFiliacaoPai(String filiacaoPai) {
		this.filiacaoPai = filiacaoPai;
	}

	public String getFiliacaoMae() {
		return filiacaoMae;
	}

	public void setFiliacaoMae(String filiacaoMae) {
		this.filiacaoMae = filiacaoMae;
	}

	
	
}
