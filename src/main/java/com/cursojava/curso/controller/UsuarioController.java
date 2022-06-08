package com.cursojava.curso.controller;


import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utilis.JWTUtili;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtili jwt;

    @RequestMapping(value = "usuario/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Daniel");
        usuario.setApellido("Donado");
        usuario.setTelefono("3005540341");
        usuario.setEmail("ddonado170");
        return usuario;
    }

    @RequestMapping(value = "usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token) {
        if (!validarToken(token)) { return null; }
        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token) {
        String usuarioId = jwt.getKey(token);
        return usuarioId != null; 

    }

    @RequestMapping(value = "usuarios", method = RequestMethod.POST)
    public ResponseEntity<?> registrarUsuarios(@RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
        
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getContraseña());
        usuario.setContraseña(hash);
        
        try {
            usuarioDao.registrar(usuario);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la inserción");
            response.put("error", e.getMessage() + ": " + (e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "!El Usuario ha sido registrado con exito¡");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    } 

    @RequestMapping(value = "usuario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> eliminar(@RequestHeader(value="Authorization") String token, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
         if (!validarToken(token)) { return; }
         usuarioDao.eliminar(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al Eliminar el grupo");
            response.put("error", e.getMessage()+": "+(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
        }
        response.put("mensaje", "El usuario con el id ID: " + id + " Ha sido eliminado con éxito!" );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
