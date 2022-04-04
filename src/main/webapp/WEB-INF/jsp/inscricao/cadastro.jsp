<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html xmlns:layout="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.w3.org/1999/xhtml" layout:decorate="~{layout}">
<head>
<meta charset="UTF-8" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>

	<section class="layout-content" layout:fragment="corpo">
		<main role="main">

		<section class="jumbotron text-center">
			<img class="d-block mx-auto" th:src="@{/image/spring-security.png}"
				width="72" height="72">
			<div class="container">
				<h1 class="jumbotron-heading">Inscrição</h1>
				<p class="lead text-muted">Inscrição de cursos</p>
			</div>
		</section>

		<div class="album py-5 bg-light">
			<div
				class="container d-flex justify-content-center align-items-center">

				<div class="row">
					<div class="col-md-12">

						<div th:replace="fragments/alert :: alerta-sucesso"></div>

						<form th:action="@{/inscricoes/salvar}" th:object="${inscricao}"
							method="POST" class="was-validated">

							<div class="form-row">
								<div class="form-group col-md-12">
									<small class="form-text text-muted">Selecione o curso
										para inscrição</small> <input type="text" class="form-control"
										id="curso" placeholder="Digite o curso desejado"
										th:field="*{curso.titulo}" required />
									<div class="invalid-feedback">Especialidade é
										obrigatória.</div>
								</div>
							</div>

							<div class="form-group">
								<label for="exampleFormControlSelect1">Escolha o
									Curso </label> <select class="form-control"
									id="exampleFormControlSelect1" name="curso" required>
									<c:forEach var="p" items="${cursos}">
										<option value="${c.id}">${c.titulo}"</option>
									</c:forEach>
								</select>
							</div>


							<!-- 							<div class="form-row"> -->
							<!-- 								<div class="form-group col-md-12"> -->
							<!-- 									<div class="card"> -->
							<!-- 									  <div class="card-header"> -->
							<!-- 									    Indique o curso desejado -->
							<!-- 									  </div> -->
							<!-- 									  <div class="card-body" id="cursos">										  									     -->
							<!-- 											options by jQuery -->
							<!-- 									  </div>									  							   -->
							<!-- 									</div> -->
							<!-- 								</div> -->
							<!-- 							</div> -->

							<!-- 							<div class="form-row">								 -->
							<!-- 								<div class="form-group col-md-12"> -->
							<!-- 									<small class="form-text text-muted">Selecione  -->
							<!-- 										a data da consulta</small> -->
							<!-- 									<input class="form-control" type="date" id="data"  -->
							<!-- 										th:field="*{dataConsulta}" required/> -->
							<!-- 									<div class="invalid-feedback"> -->
							<!-- 								      Data é obrigatória. -->
							<!-- 								    </div>	 -->
							<!-- 								</div> -->
							<!-- 							</div> -->

							<!-- 							<div class="form-row"> -->
							<!-- 								<div class="form-group col-md-12"> -->
							<!-- 									<small id="hr-small" class="form-text text-muted">Horários disponíveis -->
							<!-- 										para a consulta</small> -->
							<!-- 									<select id="horarios" class="custom-select" size="5"  -->
							<!-- 										th:field="*{horario.id}" required> -->
							<!-- 									  	<option th:value="*{horario.id}"  -->
							<!-- 									  			th:text="*{horario.horaMinuto}"></option> -->
							<!-- 									</select> -->
							<!-- 									<div class="invalid-feedback">Horário é obrigatório</div> -->
							<!-- 								</div> -->
							<!-- 							</div> -->

							<input type="hidden" th:field="*{id}" />
							<div class="form-group row">
								<div class="col-sm-10">
									<button id="btn-salvar" type="submit" class="btn btn-primary"
										data-toggle="tooltip" data-placement="right" title="Salvar">
										<i class="fas fa-save"></i>
									</button>
								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
		</div>

		</main>

	</section>
	<script th:src="@{/js/inscricao.js(v=${version})}" th:fragment="js"></script>
	<script th:inline="javascript" th:fragment="inlinescript"></script>
</body>
</html>