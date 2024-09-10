package com.linkadinho.api_linkadinho.controller;

import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.LoginDTO;
import com.linkadinho.api_linkadinho.dto.RegistrarUsuarioDTO;
import com.linkadinho.api_linkadinho.dto.RespostaLoginDTO;
import com.linkadinho.api_linkadinho.repositories.UsuarioRepository;
import com.linkadinho.api_linkadinho.service.AutenticacaoService;
import com.linkadinho.api_linkadinho.service.RegistroService;
import com.linkadinho.api_linkadinho.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autenticacao")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private RegistroService registroService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO dados) {
        RespostaLoginDTO resposta = autenticacaoService.login(dados);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody RegistrarUsuarioDTO dados) {
       registroService.registrar(dados);
       return ResponseEntity.ok().build();
    }
}
