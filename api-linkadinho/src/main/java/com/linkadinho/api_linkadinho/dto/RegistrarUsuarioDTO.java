package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.domain.usuario.UserRole;

public record RegistrarUsuarioDTO(
        String nome,
        String sobrenome,
        String email,
        String senha,
        UserRole role
) {
}
