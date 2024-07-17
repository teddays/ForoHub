package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.domain.usuario.AutenticacionUsuarioDTO;
import com.forohub.ForoHub.domain.usuario.Usuario;
import com.forohub.ForoHub.infra.security.JWTtokenDTO;
import com.forohub.ForoHub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Acceso", description = "Permite a los usuarios iniciar sesión en la plataforma.")

public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Iniciar sesión", description = "Acceso.")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid AutenticacionUsuarioDTO autenticacionUsuarioDTO) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(autenticacionUsuarioDTO.login(),
                autenticacionUsuarioDTO.clave());


        var usuarioAutorizado = authenticationManager.authenticate(authToken);
        //var token = tokenService.
        var JWTtoken = tokenService.generaToken((Usuario) usuarioAutorizado.getPrincipal());
        return ResponseEntity.ok(new JWTtokenDTO(JWTtoken));
    }

}
