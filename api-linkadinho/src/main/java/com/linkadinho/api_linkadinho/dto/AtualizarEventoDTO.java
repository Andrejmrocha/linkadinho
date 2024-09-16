package com.linkadinho.api_linkadinho.dto;

import java.time.LocalDate;

public record AtualizarEventoDTO(Long id, String nome, LocalDate data) {
}
