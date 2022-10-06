//datatables - lista de cursos
$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-registros').DataTable({
		searching : true,
		lengthMenu : [ 30, 40, 50 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/registros/datatables/server/registros',
			data : 'data'
		},
		columns : [
				{data : 'id'},
				{data : 'titulo'},
				{data : 'usuario.cpf'},
				{data : 'dataRegistro'},
				{data : 'descricao'},
				{data : 'horarioRegistro'}
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





