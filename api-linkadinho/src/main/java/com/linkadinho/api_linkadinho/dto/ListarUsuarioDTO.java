package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.usuario.Usuario;

public record ListarUsuarioDTO(Long id, String nome, String foto_url) {
    public ListarUsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getFoto_url());
    }

}
