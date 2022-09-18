package com.senai.inscricao.datatables;

public class DatatablesColunas {
	
	public static final String[] CANDIDATOS = {"id", "nome", "bairro", "cidade", "usuario.email", "dtNascimento", "telefone", "rendaPercapta"};

	public static final String[] ASSISTENTES = {"id", "nome", "re"};

	public static final String[] CURSOS = {"id", "titulo", "turno", "ativo", "cargaHoraria", "vagas", "liberaResultados"};
	
	public static final String[] INSCRICOES = {"id","curso.titulo","curso.turno", "candidato.nome", "candidato.rendaPercapta", "candidato.bairro", "candidato.cidade", "curso.cargaHoraria", "situacao" };

	public static final String[] USUARIOS = {"id", "cpf","email","dtInscricao","inscricao", "ativo", "perfis"};	
}
  