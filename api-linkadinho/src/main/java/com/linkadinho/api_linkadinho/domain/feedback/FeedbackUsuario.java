package com.linkadinho.api_linkadinho.domain.feedback;

import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.CadastrarFeedbackUsuarioDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("USUARIO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackUsuario extends Feedback{

    @ManyToOne
    @JoinColumn(name = "usuario_id_destinatario")
    private Usuario usuarioDestinatario;

    public FeedbackUsuario(CadastrarFeedbackUsuarioDTO dados, Usuario usuarioRementente, Usuario usuarioDestinatario) {
        this.setComentario(dados.comentario());
        this.setData(dados.data());
        this.setUsuarioRemetente(usuarioRementente);
        this.setUsuarioDestinatario(usuarioDestinatario);
    }
}
