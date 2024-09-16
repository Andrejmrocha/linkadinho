package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.domain.usuario.UserRole;
import com.linkadinho.api_linkadinho.domain.usuario.Usuario;

public record DetalhesUsuarioDTO(Long id, String nome, String sobrenome, String email, String role) {
    public DetalhesUsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getSobrenome(), usuario.getEmail(), usuario.getRole().name());
    }
}
