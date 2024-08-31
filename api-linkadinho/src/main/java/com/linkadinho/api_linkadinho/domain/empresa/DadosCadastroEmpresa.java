package com.linkadinho.api_linkadinho.domain.empresa;


import org.springframework.web.multipart.MultipartFile;

public record DadosCadastroEmpresa(
        String nome,
        MultipartFile image
) {
}
