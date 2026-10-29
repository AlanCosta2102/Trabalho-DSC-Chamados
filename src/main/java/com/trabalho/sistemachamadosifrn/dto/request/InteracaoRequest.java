package com.trabalho.sistemachamadosifrn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InteracaoRequest {

        @NotBlank(message = "O texto da interação é obrigatório")
        @Size(max = 2000, message = "O texto deve ter no máximo 2000 caracteres")
        private String texto;

        @NotNull(message = "O ID do autor é obrigatório")
        private Long autorId;

        public InteracaoRequest() {
        }

        public String getTexto() {
                return texto;
        }

        public void setTexto(String texto) {
                this.texto = texto;
        }

        public Long getAutorId() {
                return autorId;
        }

        public void setAutorId(Long autorId) {
                this.autorId = autorId;
        }
}