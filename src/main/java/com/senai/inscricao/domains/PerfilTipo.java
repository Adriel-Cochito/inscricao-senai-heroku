package com.senai.inscricao.domains;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), ASSISTENTE(2, "ASSISTENTE"), CANDIDATO(3, "CANDIDATO");
	
	private long cod;
	private String desc;

	private PerfilTipo(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
}

