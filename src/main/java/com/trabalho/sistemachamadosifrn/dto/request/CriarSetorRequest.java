package com.trabalho.sistemachamadosifrn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CriarSetorRequest {

    @NotBlank(message = "O nome do setor é obrigatório")
    private String nome;

    @NotBlank(message = "A sigla do setor é obrigatória")
    @Size(max = 10, message = "A sigla deve ter no máximo 10 caracteres")
    private String sigla;

    public CriarSetorRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}