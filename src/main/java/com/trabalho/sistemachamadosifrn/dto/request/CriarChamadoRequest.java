package com.trabalho.sistemachamadosifrn.dto.request;

import com.trabalho.sistemachamadosifrn.model.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CriarChamadoRequest {

        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
        private String titulo;

        @NotBlank(message = "A descrição é obrigatória")
        private String descricao;

        @NotBlank(message = "O local é obrigatório")
        @Size(max = 150, message = "O local deve ter no máximo 150 caracteres")
        private String local;

        private Prioridade prioridade;

        @NotNull(message = "O ID do solicitante é obrigatório")
        private Long solicitanteId;

        @NotNull(message = "O ID da categoria é obrigatório")
        private Long categoriaId;

        public CriarChamadoRequest() {
        }

        public String getTitulo() {
                return titulo;
        }

        public void setTitulo(String titulo) {
                this.titulo = titulo;
        }

        public String getDescricao() {
                return descricao;
        }

        public void setDescricao(String descricao) {
                this.descricao = descricao;
        }

        public String getLocal() {
                return local;
        }

        public void setLocal(String local) {
                this.local = local;
        }

        public Prioridade getPrioridade() {
                return prioridade;
        }

        public void setPrioridade(Prioridade prioridade) {
                this.prioridade = prioridade;
        }

        public Long getSolicitanteId() {
                return solicitanteId;
        }

        public void setSolicitanteId(Long solicitanteId) {
                this.solicitanteId = solicitanteId;
        }

        public Long getCategoriaId() {
                return categoriaId;
        }

        public void setCategoriaId(Long categoriaId) {
                this.categoriaId = categoriaId;
        }
}