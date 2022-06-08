// Call the dataTables jQuery plugin
$(document).ready(function() {
   //on ready
 });
 
 
 async function registrarUsuarios(){
    let datos = {};
    datos.nombre = document.getElementById('txtNombre').value;
    datos.apellido = document.getElementById('txtApellido').value;
    datos.email = document.getElementById('txtEmail').value;
    datos.telefono = document.getElementById('txtTelefono').value;
    datos.contraseña = document.getElementById('txtContraseña').value;

    let repetirPassword = document.getElementById('txtRepetirContraseña').value;

    if (repetirPassword != datos.contraseña) {
        alert('La Contraseña que Digitaste es Diferente.');
        return;
    }

   const request = await fetch('usuarios', {
     method: 'POST',
     headers: {
       'Accept': 'application/json',
       'Content-Type': 'application/json'
     },
    body: JSON.stringify(datos)
   });
   alert("La cuenta fue registrada con exito")
   
}
 
 
 