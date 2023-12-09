function eliminar(id){
	swal({
	  title: "¿Esta seguro?",
	  text: "¿Realmente desea eliminar este libro?",
	  icon: "warning",
	  buttons: true,
	  dangerMode: true,
	})
	.then((willDelete) => {
	  if (willDelete) {
	    location.href="/eliminarLibro/"+id;   
	  } else {
	    
	  }
	});
}
