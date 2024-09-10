package com.linkadinho.api_linkadinho.domain.usuario;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    STARTER("starter");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
