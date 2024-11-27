package com.linkadinho.api_linkadinho.model.amizade;

import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Amizade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id_remetente")
    private Usuario usuarioRementente;

    @ManyToOne
    @JoinColumn(name = "usuario_id_destinatario")
    private Usuario usuarioDestinatario;

    @Enumerated(EnumType.STRING)
    private StatusAmizade status;

    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    public Amizade(Usuario remetente, Usuario destinatario, StatusAmizade statusAmizade, LocalDate criacao, LocalDate atualizacao) {
        this.usuarioRementente = remetente;
        this.usuarioDestinatario = destinatario;
        this.status = statusAmizade;
        this.dataCriacao = criacao;
        this.dataAtualizacao = atualizacao;
    }
}
