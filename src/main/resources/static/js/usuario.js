//datatables - lista de médicos
$(document).ready(function() {
	
	moment.locale('pt-BR');
	var table = $('#table-usuarios').DataTable({
		searching : true,
		lengthMenu : [ 30, 40, 50 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/u/datatables/server/usuarios',
			data : 'data'
		},
		columns : [
				{data : 'id'},
				{data : 'cpf'},
				{	data : 'ativo', 
					render : function(ativo) {
						return ativo == true ? 'Sim' : 'Não';
					}
				},
				{	data : 'perfis',									 
					render : function(perfis) {
						var aux = new Array();
						$.each(perfis, function( index, value ) {
							  aux.push(value.desc);
						});
						return aux;
					},
					orderable : false,
				},
				{data : 'email'},
				{data : 'dtInscricao'}
		]
		
	});
	
	

	// Compara as senhas se são iguais pra liberar senha atual
    $('.pass').keyup(function(){
    	if($('#senha1').val() == "" || $('#senha1').val() == ""){
    		$('#senha3').attr('readonly', 'readonly');
    	}else{
    		$('#senha1').val() === $('#senha2').val()
    			? $('#senha3').removeAttr('readonly')
    			: $('#senha3').attr('readonly', 'readonly'); //bloqueia o campo
    	}

   	});
    
});	
