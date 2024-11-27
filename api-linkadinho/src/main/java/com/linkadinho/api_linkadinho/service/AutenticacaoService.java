package com.linkadinho.api_linkadinho.service;

import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.LoginDTO;
import com.linkadinho.api_linkadinho.dto.RespostaLoginDTO;
import com.linkadinho.api_linkadinho.infra.exception.CredenciasInvalidasException;
import com.linkadinho.api_linkadinho.infra.exception.UsuarioNaoEncontradoException;
import com.linkadinho.api_linkadinho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RespostaLoginDTO login (LoginDTO dados) {
        var usuario = usuarioRepository.findByEmail(dados.email());

        if (usuario == null) throw new UsuarioNaoEncontradoException("Usuário com email fornecido não foi encontrado.");

        try {
            var usuarioSenha = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
            var autenticacao = this.authenticationManager.authenticate(usuarioSenha);
            var token = this.tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
            return new RespostaLoginDTO(token);
        } catch (BadCredentialsException e) {
            throw new CredenciasInvalidasException("Email ou senha inválidos.");
        }

    }
}
