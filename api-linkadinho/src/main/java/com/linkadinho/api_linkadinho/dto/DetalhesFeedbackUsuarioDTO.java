package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.domain.feedback.FeedbackUsuario;

import java.time.LocalDate;

public record DetalhesFeedbackUsuarioDTO(String comentario, LocalDate data, String remetente, String destinatario) {
    public DetalhesFeedbackUsuarioDTO(FeedbackUsuario feedbackUsuario){
        this(feedbackUsuario.getComentario(), feedbackUsuario.getData(),
                feedbackUsuario.getUsuarioRemetente().getNome(), feedbackUsuario.getUsuarioDestinatario().getNome());
    };
}
