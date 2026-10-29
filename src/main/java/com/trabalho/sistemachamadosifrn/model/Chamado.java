package com.trabalho.sistemachamadosifrn.model;

import com.trabalho.sistemachamadosifrn.model.enums.Prioridade;
import com.trabalho.sistemachamadosifrn.model.enums.StatusChamado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, length = 150)
    private String local;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Prioridade prioridade = Prioridade.MEDIA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusChamado status = StatusChamado.ABERTO;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @Column(columnDefinition = "TEXT")
    private String solucao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    public Chamado() {
    }

    @PrePersist
    public void antesDeSalvar() {
        if (dataAbertura == null) {
            dataAbertura = LocalDateTime.now();
        }

        if (status == null) {
            status = StatusChamado.ABERTO;
        }

        if (prioridade == null) {
            prioridade = Prioridade.MEDIA;
        }
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLocal() {
        return local;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public String getSolucao() {
        return solucao;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    // Setters

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }
}