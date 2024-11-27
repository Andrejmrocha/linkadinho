package com.linkadinho.api_linkadinho.model.empresa;

import com.linkadinho.api_linkadinho.model.evento.Evento;
import com.linkadinho.api_linkadinho.model.usuario.Usuario;
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

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Usuario admin;

    @OneToMany(mappedBy = "empresa")
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "empresa")
    private List<Evento> eventos = new ArrayList<>();

    public Empresa(CadastrarEmpresaDTO dados) {
        this.nome = dados.nome();
        this.ativo = true;
    }

    public void excluir() {
        this.ativo = false;
    }

}
