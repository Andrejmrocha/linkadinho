package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.usuario.UserRole;

public record RegistrarUsuarioDTO(
        String nome,
        String sobrenome,
        String email,
        String senha,
        UserRole role,
        Long organizacao
) {
}
