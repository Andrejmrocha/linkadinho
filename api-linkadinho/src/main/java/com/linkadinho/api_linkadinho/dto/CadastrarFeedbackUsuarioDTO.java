package com.linkadinho.api_linkadinho.dto;

import java.time.LocalDate;

public record CadastrarFeedbackUsuarioDTO(
        String comentario,
        LocalDate data,
        String emailUsuarioDestinatario
) {
}
