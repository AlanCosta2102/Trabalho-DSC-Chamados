package com.trabalho.sistemachamadosifrn.dto.response;

import com.trabalho.sistemachamadosifrn.model.Categoria;

public class CategoriaResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Long setorId;
    private String setorNome;
    private String setorSigla;

    public CategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
        this.setorId = categoria.getSetor().getId();
        this.setorNome = categoria.getSetor().getNome();
        this.setorSigla = categoria.getSetor().getSigla();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getSetorId() {
        return setorId;
    }

    public String getSetorNome() {
        return setorNome;
    }

    public String getSetorSigla() {
        return setorSigla;
    }
}