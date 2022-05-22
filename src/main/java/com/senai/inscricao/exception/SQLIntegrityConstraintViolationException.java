package com.senai.inscricao.exception;

@SuppressWarnings("serial")
public class SQLIntegrityConstraintViolationException extends RuntimeException {

	public SQLIntegrityConstraintViolationException(String message) {
		super(message);
	}
}
