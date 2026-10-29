package com.trabalho.sistemachamadosifrn.model;

import jakarta.persistence.*;

@Entity
@Table(name= "setores",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_setor_sigla",
                columnNames = "sigla"
        ))

public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String nome;

    @Column(nullable = false, length = 10)
    private String sigla;

    public Setor() {}

    public Long getId() {return id;}
    public String getNome() { return nome;}
    public String getSigla() {return sigla;}

    public void setNome(String nome) {this.nome = nome;}
    public void setSigla(String sigla) {this.sigla = sigla;}
}
