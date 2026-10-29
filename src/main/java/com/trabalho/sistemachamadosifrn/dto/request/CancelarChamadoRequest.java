package com.trabalho.sistemachamadosifrn.dto.request;

import jakarta.validation.constraints.NotNull;

public class CancelarChamadoRequest {

    @NotNull(message = "O ID do solicitante é obrigatório")
    private Long solicitanteId;

    public CancelarChamadoRequest() {
    }

    public Long getSolicitanteId() {
        return solicitanteId;
    }

    public void setSolicitanteId(Long solicitanteId) {
        this.solicitanteId = solicitanteId;
    }
}