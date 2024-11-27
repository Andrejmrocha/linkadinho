package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.empresa.Empresa;

public record ListarEmpresaDTO(Long id, String nome, String imgUrl) {
    public ListarEmpresaDTO(Empresa empresa) {
        this(empresa.getId(), empresa.getNome(), empresa.getImgUrl());
    }

}
