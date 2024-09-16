package com.linkadinho.api_linkadinho.service;

import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.RegistrarUsuarioDTO;
import com.linkadinho.api_linkadinho.infra.exception.EmailJaExistenteException;
import com.linkadinho.api_linkadinho.infra.exception.UsuarioNaoEncontradoException;
import com.linkadinho.api_linkadinho.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario registrar(RegistrarUsuarioDTO dados) {
        if (this.usuarioRepository.findByEmail(dados.email()) != null) {
            throw new EmailJaExistenteException("Email já está em uso");
        }

        String senhaCriptografada = bCryptPasswordEncoder.encode(dados.senha());
        Usuario usuarioNovo = new Usuario(dados, senhaCriptografada);
        return usuarioRepository.save(usuarioNovo);
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }
}
