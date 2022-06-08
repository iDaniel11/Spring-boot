// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
  });
  
  
  async function iniciarSesion(){
     let datos = {};
     datos.email = document.getElementById('txtEmail').value;
     datos.contraseña = document.getElementById('txtContraseña').value;
 
    const request = await fetch('login', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
     body: JSON.stringify(datos)
    });
    const respuesta = await request.text();
    if (respuesta != 'FAIL') {
      localStorage.token = respuesta;
      localStorage.email = datos.email;
      window.location.href = 'usuarios.html'
    } else {
      alert('Las Credenciales son incorrectas, Intente Nuevamente.');
    }
    
 }
  
  
  