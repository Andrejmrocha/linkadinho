package com.linkadinho.api_linkadinho.domain.evento;

import com.linkadinho.api_linkadinho.domain.empresa.Empresa;
import com.linkadinho.api_linkadinho.domain.feedback.FeedbackEvento;
import com.linkadinho.api_linkadinho.dto.AtualizarEventoDTO;
import com.linkadinho.api_linkadinho.dto.CadastrarEventoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<FeedbackEvento> feedbacks = new ArrayList<>();

    public Evento(CadastrarEventoDTO dados, Empresa empresa) {
        this.nome = dados.nome();
        this.data = dados.data();
        this.empresa = empresa;
    }

    public void atualizarInformacoes(AtualizarEventoDTO dados) {
        if (dados.nome() != null) this.nome = dados.nome();
        if (dados.data() != null) this.data = dados.data();

    }
}
