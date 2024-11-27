package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.feedback.FeedbackUsuario;

import java.time.LocalDate;

public record DetalhesFeedbackUsuarioDTO(String comentario, LocalDate data, UsuarioFeedbackDTO remetente, UsuarioFeedbackDTO destinatario) {
    public DetalhesFeedbackUsuarioDTO(FeedbackUsuario feedbackUsuario){
        this(feedbackUsuario.getComentario(), feedbackUsuario.getData(),
                new UsuarioFeedbackDTO(feedbackUsuario.getUsuarioRemetente()), new UsuarioFeedbackDTO(feedbackUsuario.getUsuarioDestinatario()));
    };
}
