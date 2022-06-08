package com.cursojava.curso.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utilis.JWTUtili;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtili jwt;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario) {

        Usuario usuariologeado = usuarioDao.ObtenerUsuarioPorCredencial(usuario);

       if(usuariologeado != null) {
           String tokenJwt = jwt.create(String.valueOf(usuariologeado.getId()), usuariologeado.getEmail());
           return tokenJwt;
       }
       return "FAIL"; 
    } 
}
