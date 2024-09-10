package com.linkadinho.api_linkadinho.controller;

import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.LoginDTO;
import com.linkadinho.api_linkadinho.dto.RegistrarUsuarioDTO;
import com.linkadinho.api_linkadinho.dto.RespostaLoginDTO;
import com.linkadinho.api_linkadinho.repositories.UsuarioRepository;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO dados) {
        var usuarioSenha = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var autenticacao = this.authenticationManager.authenticate(usuarioSenha);
        var token = this.tokenService.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new RespostaLoginDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody RegistrarUsuarioDTO dados) {
       if (this.usuarioRepository.findByEmail(dados.email()) != null) return ResponseEntity.badRequest().build();

       String senha_cripto = new BCryptPasswordEncoder().encode(dados.senha());
       Usuario novoUsuario = new Usuario(dados, senha_cripto);

       this.usuarioRepository.save(novoUsuario);

       return ResponseEntity.ok().build();
    }
}
