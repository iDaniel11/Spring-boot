// Call the dataTables jQuery plugin
$(document).ready(function() {

   cargarUsuarios();
  //  eliminarUsuario();

  $('#usuarios').DataTable();
  actualizarEmailDelUsuario();
});

function actualizarEmailDelUsuario() {
  document.getElementById('txt-nombre-usuario');
}

async function cargarUsuarios(){
  const request = await fetch('usuarios', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json();

 


  let listadoHtml = '';
  for (let usuario of usuarios) {
  let botonEliminar = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle btn-lg"><i class="fas fa-trash"></i></a>'
  let usuarioHtml = '<tr><td>'+ usuario.id +'</td> <td>' + usuario.nombre +' '+ usuario.apellido +'</td><td>'
   + usuario.email +'</td> <td>'+ usuario.telefono
   +' </td><td>' + botonEliminar + '</td></tr>';
   listadoHtml += usuarioHtml;

  }

  document.querySelector('#usuarios tbody').outerHTML= listadoHtml;

  
}

function getHeaders() {
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token 
  };
}

async function eliminarUsuario(id) {
  if (!confirm('¿Desea eliminar este Usuario?')) {
    return;
  }

  const request = await fetch('usuario/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });
  location.reload()
}
