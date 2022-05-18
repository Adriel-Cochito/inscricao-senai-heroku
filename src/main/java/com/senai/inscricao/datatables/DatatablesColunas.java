package com.senai.inscricao.datatables;

public class DatatablesColunas {
	
	public static final String[] CANDIDATOS = {"id", "nome", "bairro", "usuario.email", "dtNascimento", "telefone", "rendaPercapta"};

	public static final String[] ASSISTENTES = {"id", "nome", "re"};

	public static final String[] CURSOS = {"id", "titulo", "turno", "ativo", "cargaHoraria"};
	
	public static final String[] INSCRICOES = {"id","curso.titulo","curso.turno", "candidato.nome", "candidato.rendaPercapta", "candidato.bairro", "curso.cargaHoraria", "situacao" };

	public static final String[] USUARIOS = {"id", "cpf","email","dtInscricao", "ativo", "perfis"};	
}
  