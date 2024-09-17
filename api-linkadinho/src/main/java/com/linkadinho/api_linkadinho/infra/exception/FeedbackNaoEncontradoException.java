package com.linkadinho.api_linkadinho.infra.exception;

public class FeedbackNaoEncontradoException extends RuntimeException {
    public FeedbackNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
