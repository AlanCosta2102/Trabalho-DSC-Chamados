package com.trabalho.sistemachamadosifrn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CriarCategoriaRequest {

        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        private String nome;

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        private String descricao;

        @NotNull(message = "O ID do setor é obrigatório")
        private Long setorId;

        public CriarCategoriaRequest() {
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getDescricao() {
                return descricao;
        }

        public void setDescricao(String descricao) {
                this.descricao = descricao;
        }

        public Long getSetorId() {
                return setorId;
        }

        public void setSetorId(Long setorId) {
                this.setorId = setorId;
        }
}