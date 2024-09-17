package com.linkadinho.api_linkadinho.domain.feedback;

import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;

    @ManyToOne
    @JoinColumn(name = "usuario_id_remetente")
    private Usuario usuarioRemetente;

    private LocalDate data;
}
