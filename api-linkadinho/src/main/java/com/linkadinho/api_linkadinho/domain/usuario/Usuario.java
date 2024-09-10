package com.linkadinho.api_linkadinho.domain.usuario;

import com.linkadinho.api_linkadinho.dto.RegistrarUsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private UserRole role;

    public Usuario(RegistrarUsuarioDTO dados, String senhaCripto) {
        this.nome = dados.nome();
        this.sobrenome = dados.sobrenome();
        this.email = dados.email();
        this.senha = senhaCripto;
        this.role = dados.role();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
