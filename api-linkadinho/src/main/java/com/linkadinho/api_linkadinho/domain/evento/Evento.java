package com.linkadinho.api_linkadinho.domain.evento;

import com.linkadinho.api_linkadinho.domain.empresa.Empresa;
import com.linkadinho.api_linkadinho.dto.CadastrarEventoDTO;
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
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataCadastro;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    public Evento(CadastrarEventoDTO dados, Empresa empresa) {
        this.nome = dados.nome();
        this.dataCadastro = dados.data();
        this.empresa = empresa;
    }
}
