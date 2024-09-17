package com.linkadinho.api_linkadinho.dto;

import org.springframework.web.multipart.MultipartFile;

public record AtualizarEmpresaDTO(
        Long id,
        String nome,
        MultipartFile image
) {
}
