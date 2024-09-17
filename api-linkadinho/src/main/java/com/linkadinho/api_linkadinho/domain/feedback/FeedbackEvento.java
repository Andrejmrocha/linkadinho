package com.linkadinho.api_linkadinho.domain.feedback;

import com.linkadinho.api_linkadinho.domain.evento.Evento;
import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.CadastrarFeedbackEventoDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("EVENTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackEvento extends Feedback{

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public FeedbackEvento(CadastrarFeedbackEventoDTO dados, Usuario usuarioRemetente, Evento evento) {
        this.setComentario(dados.comentario());
        this.setData(dados.data());
        this.setUsuarioRemetente(usuarioRemetente);
        this.setEvento(evento);

    }
}
