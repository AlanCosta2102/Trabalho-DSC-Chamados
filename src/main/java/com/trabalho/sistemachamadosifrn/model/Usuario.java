package com.trabalho.sistemachamadosifrn.model;

import com.trabalho.sistemachamadosifrn.model.enums.TipoUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_usuario_email",
                    columnNames = "email"),
            @UniqueConstraint(
                    name = "uk_usuario_matricula",
                    columnNames = "matricula")

        })

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 180)
    private String email;

    @Column(nullable = false, length = 30)
    private String matricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id")
    private Setor setor;

    public Usuario() {}

    public Long getId() {return id;}
    public String getNome() {return nome;}
    public String getEmail() {return email;}
    public String getMatricula() {return matricula;}
    public TipoUsuario getTipo() {return tipo;}
    public Setor getSetor() {return setor;}

    public void setNome(String nome) {this.nome = nome;}
    public void setEmail(String email) {this.email = email;}
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setTipo(TipoUsuario tipo) {this.tipo = tipo;}
    public void setSetor(Setor setor) {this.setor = setor;}
}
