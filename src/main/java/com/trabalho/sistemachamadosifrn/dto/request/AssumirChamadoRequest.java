package com.trabalho.sistemachamadosifrn.dto.request;

import jakarta.validation.constraints.NotNull;

public class AssumirChamadoRequest {

    @NotNull(message = "O ID do técnico é obrigatório")
    private Long tecnicoId;

    public AssumirChamadoRequest() {
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }
}