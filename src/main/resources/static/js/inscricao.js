/**
 * busca as especialidades com auto-complete
 */
$("#curso").autocomplete({
    source: function (request, response) {
        $.ajax({
            method: "GET",
            url: "/cursos/titulo",
            data: {
            	termo: request.term
			},
            success: function (data) {
            	response(data);
            }
        });
    }
});

//
///**
// * após a especialidade ser selecionado busca 
// * os médicos referentes e os adiciona na página com
// * radio
// */
//$('#especialidade').on('blur', function() {
//    $('div').remove(".custom-radio");
//	var titulo = $(this).val();
//	if ( titulo != '' ) {			
//		$.get( "/medicos/especialidade/titulo/" + titulo , function( result ) {
//				
//			var ultimo = result.length - 1; 
//			
//			$.each(result, function (k, v) {
//				
//				if ( k == ultimo ) {
//	    			$("#medicos").append( 
//	    				 '<div class="custom-control custom-radio">'	
//	    				+  '<input class="custom-control-input" type="radio" id="customRadio'+ k +'" name="medico.id" value="'+ v.id +'" required>'
//						+  '<label class="custom-control-label" for="customRadio'+ k +'">'+ v.nome +'</label>'
//						+  '<div class="invalid-feedback">Médico é obrigatório</div>'
//						+'</div>'
//	    			);
//				} else {
//	    			$("#medicos").append( 
//	    				 '<div class="custom-control custom-radio">'	
//	    				+  '<input class="custom-control-input" type="radio" id="customRadio'+ k +'" name="medico.id" value="'+ v.id +'" required>'
//						+  '<label class="custom-control-label" for="customRadio'+ k +'">'+ v.nome +'</label>'
//						+'</div>'
//	        		);	            				
//				}
//		    });
//		});
//	}
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



//datatables - lista de inscrições
$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-inscricoes').DataTable({
		searching : true,
		lengthMenu : [ 10, 20, 30, 40 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/inscricoes/datatables/server/lista',
            data : 'data'
        },
        columns : [
            {data : 'id'},
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
            {data : 'candidato.nome'},
            {data : 'candidato.rendaPercapta'},
            {data : 'candidato.bairro'},
            {orderable : false,	data : 'candidato.id', "render" : function(id) {
            	console.log("Teste de impressão id: ")
            	console.log(id)
                    return '<a class="btn btn-outline-success btn-sm btn-block" href="/candidatos/dados/'
                            + id + '" role="button"><i class="bi bi-arrow-right-circle-fill"></i></a>';
                }
            }
        ]
    });
	
	console.log("Teste de impressão")
	
});



//$('#perfis').ready(function(){
//	var perfis = [[${usuario}]];
//	$.each(perfis, function(k, v) {
//		$('#perfis option[value="'+ v.id +'"]').attr('selected', 'selected');
//	})
//});















