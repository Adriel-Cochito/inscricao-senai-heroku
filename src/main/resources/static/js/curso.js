
//datatables - lista de cursos
$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-cursos').DataTable({
		searching : true,
		lengthMenu : [ 10, 20 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/cursos/datatables/server/cursos',
			data : 'data'
		},
		columns : [
				{data : 'id'},
				{data : 'titulo'},
				{	data : 'turno', 
					render : function(turno) {
						if(turno == '1'){
							return 'Manhã';
						} else if(turno == '2') {
							return 'Tarde';
						} else if(turno == '3') {
							return 'Noite';
						}
							
					}
				},
				{	data : 'ativo', 
					render : function(ativo) {
						return ativo == true ? 'Sim' : 'Não';
					}
				},
				{	data : 'cargaHoraria'},
				{	data : 'vagas'},
				{	data : 'liberaResultados', 
					render : function(liberaResultados) {
						return liberaResultados == true ? 'Liberado' : 'Não Liberado';
					}
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a class="btn btn-outline-success btn-sm btn-block"', ' ')
								 .concat('href="').concat('/cursos/editar/').concat(id, '"', ' ') 
								 .concat('role="button" title="Editar" data-toggle="tooltip" data-placement="right">', ' ')
								 .concat('<i class="fas fa-edit"></i></a>');
					},
					orderable : false
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a class="btn btn-outline-danger btn-sm btn-block"', ' ') 
								 .concat('href="').concat('/cursos/excluir/').concat(id, '"', ' ')
								 .concat('role="button" title="Excluir" data-toggle="modal" data-target="#confirm-modal">', ' ')
								 .concat('<i class="fas fa-edit"></i></a>');
					},
					orderable : false
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a class="btn btn-warning btn-sm btn-block"', ' ') 
								 .concat('href="').concat('/cursos/resultado/liberar/').concat(id, '"', ' ')
								 .concat('role="button" title="Libera" data-toggle="modal" data-target="#confirm-modal">', ' ')
								 .concat('Libera Resultados</a>')
								 .concat('<a class="btn btn-danger btn-sm btn-block"', ' ') 
								 .concat('href="').concat('/cursos/resultado/cancelar/').concat(id, '"', ' ')
								 .concat('role="button" title="Cancela" data-toggle="modal" data-target="#confirm-modal">', ' ')
								 .concat('Cancela Resultados</a>');
					},
					orderable : false
				}
		]
	});
	
	
	
//    $('#table-cursos tbody').on('click', '[id*="dp_"]', function () {
//    	var data = table.row($(this).parents('tr')).data();
//    	var aux = new Array();
//		$.each(data.perfis, function( index, value ) {
//			  aux.push(value.id);
//		});
//		document.location.href = '/cursos/excluir/' + data.id
//    } );
    
    
	
});	





