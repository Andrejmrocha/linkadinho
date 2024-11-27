package com.linkadinho.api_linkadinho.dto;

import org.springframework.web.multipart.MultipartFile;

public record AtualizarUsuarioDTO(Long id, String nome, String sobrenome, MultipartFile fotoPerfil) {
}
