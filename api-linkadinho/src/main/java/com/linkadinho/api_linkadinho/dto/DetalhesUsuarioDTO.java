package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.usuario.Usuario;

public record DetalhesUsuarioDTO(Long id, String nome, String sobrenome, String email, String role, String foto_url, DetalhesEmpresaDTO empresa) {
    public DetalhesUsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getSobrenome(), usuario.getEmail(), usuario.getRole().name(), usuario.getFoto_url() != null ? usuario.getFoto_url() : "" ,usuario.getEmpresa() != null ? new DetalhesEmpresaDTO(usuario.getEmpresa()) : null);
    }
}
