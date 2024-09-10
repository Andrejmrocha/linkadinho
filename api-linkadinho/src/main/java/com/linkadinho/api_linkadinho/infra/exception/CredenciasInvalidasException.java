package com.linkadinho.api_linkadinho.infra.exception;

public class CredenciasInvalidasException extends RuntimeException{
    public CredenciasInvalidasException(String mensagem) {
        super(mensagem);
    }
}
