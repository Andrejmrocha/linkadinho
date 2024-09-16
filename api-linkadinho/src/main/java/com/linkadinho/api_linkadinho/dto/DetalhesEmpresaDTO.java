package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.domain.empresa.Empresa;
import com.linkadinho.api_linkadinho.domain.usuario.Usuario;

public record DetalhesEmpresaDTO(Long id,
        String nome,
        String codigo,
        String admin) {
    public DetalhesEmpresaDTO(Empresa empresa) {
        this(empresa.getId(), empresa.getNome(), empresa.getCodigo(), empresa.getAdmin().getNome());
    }
}
