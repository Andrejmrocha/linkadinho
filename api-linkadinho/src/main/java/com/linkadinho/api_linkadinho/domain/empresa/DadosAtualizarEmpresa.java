package com.linkadinho.api_linkadinho.domain.empresa;

import org.springframework.web.multipart.MultipartFile;

public record DadosAtualizarEmpresa(
        Long id,
        String nome,
        MultipartFile image
) {
}
