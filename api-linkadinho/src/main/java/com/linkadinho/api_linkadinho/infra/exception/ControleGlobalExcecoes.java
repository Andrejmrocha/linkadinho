package com.linkadinho.api_linkadinho.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControleGlobalExcecoes {

    @ExceptionHandler(EmailJaExistenteException.class)
    public ResponseEntity<String> handleEmailJaExistenteException(EmailJaExistenteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CredenciasInvalidasException.class)
    public ResponseEntity<String> handleCredenciasInvalidasException(CredenciasInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<String> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
