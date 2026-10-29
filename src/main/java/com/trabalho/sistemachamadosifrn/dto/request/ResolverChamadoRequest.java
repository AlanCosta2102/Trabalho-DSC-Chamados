package com.trabalho.sistemachamadosifrn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ResolverChamadoRequest {

    @NotNull(message = "O ID do técnico é obrigatório")
    private Long tecnicoId;

    @NotBlank(message = "A solução do chamado é obrigatória")
    @Size(max = 2000, message = "A solução deve ter no máximo 2000 caracteres")
    private String solucao;

    public ResolverChamadoRequest() {
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }
}