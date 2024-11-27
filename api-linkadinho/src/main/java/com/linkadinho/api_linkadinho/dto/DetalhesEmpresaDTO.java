package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.empresa.Empresa;

public record DetalhesEmpresaDTO(Long id,
        String nome,
        String codigo,
        String admin,
                                 String logo_url) {
    public DetalhesEmpresaDTO(Empresa empresa) {
        this(empresa.getId(), empresa.getNome(), empresa.getCodigo(), empresa.getAdmin().getNome(), empresa.getImgUrl());
    }
}
