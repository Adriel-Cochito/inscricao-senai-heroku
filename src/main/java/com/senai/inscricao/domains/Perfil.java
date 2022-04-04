package com.senai.inscricao.domains;


import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "perfis")
public class Perfil extends AbstractEntity {
	
	@Column(name = "descricao", nullable = false, unique = true)
	private String desc;
	
	public Perfil() {
		super();
	}

	public Perfil(Long id) {
		super.setId(id);
	}
	
	

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
