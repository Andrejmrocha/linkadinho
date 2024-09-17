package com.linkadinho.api_linkadinho.domain.empresa;

import com.linkadinho.api_linkadinho.domain.evento.Evento;
import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.CadastrarEmpresaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Empresa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String codigo;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Usuario admin;

    @OneToMany(mappedBy = "empresa")
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "empresa")
    private List<Evento> eventos = new ArrayList<>();

    public Empresa(CadastrarEmpresaDTO dados) {
        this.nome = dados.nome();
    }

}
