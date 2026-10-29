package com.trabalho.sistemachamadosifrn.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interacoes")
public class Interacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chamado_id",nullable = false)
    private Chamado chamado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    public Interacao() {}

    @PrePersist
    public void antesDeSalvar() {
        if (dataHora == null) dataHora = LocalDateTime.now();
    }

    public Long getId() {return id;}
    public String getTexto() {return texto;}
    public LocalDateTime getDataHora() {return  dataHora;}
    public Chamado getChamado() {return chamado;}
    public Usuario getAutor() {return autor;}

    public void setTexto(String texto) {this.texto = texto;}
    public void setDataHora(LocalDateTime dataHora){
        this.dataHora = dataHora;
    }
    public void setChamado(Chamado chamado){
        this.chamado = chamado;
    }
    public void setAutor(Usuario autor) {this.autor = autor;}
}
