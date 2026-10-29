package com.trabalho.sistemachamadosifrn.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;

    public Categoria() {}

    public Long getId() {return id;}
    public String getNome() {return nome;}
    public String getDescricao() {return descricao;}
    public Setor getSetor(){return setor;}

    public void setNome(String nome) {this.nome = nome;}
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public void setSetor(Setor setor) {this.setor = setor;}
}
