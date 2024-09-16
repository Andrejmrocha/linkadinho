package com.linkadinho.api_linkadinho.dto;

import java.time.LocalDate;

public record CadastrarEventoDTO(
        String nome,
        LocalDate data,
        String empresa
) {
}
