package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.amizade.Amizade;

import java.time.LocalDate;

public record DetalhesAmizadeDTO(Long id, LocalDate dataCricao, LocalDate dataAtualizacao, UsuarioFeedbackDTO remetente, UsuarioFeedbackDTO destinatario) {
    public DetalhesAmizadeDTO(Amizade amizade) {
        this(amizade.getId(), amizade.getDataCriacao(), amizade.getDataAtualizacao(), new UsuarioFeedbackDTO(amizade.getUsuarioRementente()), new UsuarioFeedbackDTO(amizade.getUsuarioDestinatario()));
    }
}
