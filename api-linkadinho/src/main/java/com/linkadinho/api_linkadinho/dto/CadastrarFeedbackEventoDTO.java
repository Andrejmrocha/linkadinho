package com.linkadinho.api_linkadinho.dto;

import java.time.LocalDate;

public record CadastrarFeedbackEventoDTO(
        String comentario,
        LocalDate data,
        String nomeEvento
) {
}
