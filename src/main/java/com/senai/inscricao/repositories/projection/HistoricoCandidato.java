package com.senai.inscricao.repositories.projection;

import com.senai.inscricao.domains.Candidato;
import com.senai.inscricao.domains.Curso;

public interface HistoricoCandidato{

	Long getId();
	
	Candidato getCandidato();
	
	String getDataConsulta();
	
	Curso getCurso();
	
	Integer getSituacao();
	
}

