package com.trabalho.sistemachamadosifrn.dto.request;

import com.trabalho.sistemachamadosifrn.model.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CriarUsuarioRequest {

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        private String nome;

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Informe um e-mail válido")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
        private String email;

        @NotBlank(message = "A matrícula é obrigatória")
        @Size(max = 30, message = "A matrícula deve ter no máximo 30 caracteres")
        private String matricula;

        @NotNull(message = "O tipo de usuário é obrigatório")
        private TipoUsuario tipo;

        private Long setorId;

        public CriarUsuarioRequest() {
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getMatricula() {
                return matricula;
        }

        public void setMatricula(String matricula) {
                this.matricula = matricula;
        }

        public TipoUsuario getTipo() {
                return tipo;
        }

        public void setTipo(TipoUsuario tipo) {
                this.tipo = tipo;
        }

        public Long getSetorId() {
                return setorId;
        }

        public void setSetorId(Long setorId) {
                this.setorId = setorId;
        }
}