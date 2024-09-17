package com.linkadinho.api_linkadinho.dto;


import org.springframework.web.multipart.MultipartFile;

public record CadastrarEmpresaDTO(
        String nome,
        MultipartFile image
) {
}
