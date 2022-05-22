

/**
 * busca as especialidades com auto-complete
 */
 // Quando habilitado, faz SELECT no DB somente ao selecionar, mesmo sem clicar em salvar
//$("#curso").autocomplete({
//    source: function (request, response) {
//        $.ajax({
//            method: "GET",
//            url: "/cursos/titulo",
//            data: {
//            	termo: request.term
//			},
//            success: function (data) {
//            	response(data);
//            }
//        });
//    }
//});



/**
 * Datatable histórico de consultas
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-candidato-historico').DataTable({
        searching : false,
        lengthMenu : [ 10, 20 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [2, 'desc'],
        ajax : {
            url : '/inscricoes/datatables/server/historicos',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'candidato.nome'},
            {data : 'curso.titulo'},
            {	data : 'curso.turno', 
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
			{	data : 'curso.cargaHoraria',
				render : function(turno) {
					return turno + ' horas';
				}
			},
			{data : 'situacao', 
					render : function(situacao) {
//						console.log('Situação: ');
//						console.log(situacao);
						if(situacao == '1') {
							return 'Aprovado';
						} else if(situacao == '2') {
							return 'Selecionado';
						} else if(situacao == '3') {
							return 'Não aprovado';
						} else {
							return 'Em analise';
						}
					}
				},
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/inscricoes/editar/inscricao/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/inscricoes/excluir/inscricao/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
        ]
    });
});







//$('#perfis').ready(function(){
//	var perfis = [[${usuario}]];
//	$.each(perfis, function(k, v) {
//		$('#perfis option[value="'+ v.id +'"]').attr('selected', 'selected');
//	})
//});




//datatables - lista de cursos
$(document).ready(function() {
	moment.locale('pt-BR');
	

	
	var table = $('#table-cursos').DataTable({
		searching : false,
		lengthMenu : [ 10 ],
		processing : false,
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
						return ''.concat('<a class="btn btn-primary btn-sm btn-block"', ' ')
								 .concat('href="').concat('/inscricoes/lista/').concat(id, '"', ' ') 
								 .concat('role="button" title="Editar" data-toggle="tooltip" data-placement="right">', ' ')
								 .concat('<i class="bi bi-arrow-down-circle"></i></a>');
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











