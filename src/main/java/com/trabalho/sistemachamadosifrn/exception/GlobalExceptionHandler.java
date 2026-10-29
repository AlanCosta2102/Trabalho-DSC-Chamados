package com.trabalho.sistemachamadosifrn.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> naoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request) {
        return resposta(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiError> conflito(
            RegraNegocioException ex,
            HttpServletRequest request) {
        return resposta(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> dadosInvalidos(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos.");
        return resposta(HttpStatus.BAD_REQUEST, mensagem, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> jsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        return resposta(HttpStatus.BAD_REQUEST,
                "JSON inválido ou valor incompatível.", request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> integridade(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {
        return resposta(HttpStatus.CONFLICT,
                "A operação viola uma restrição do banco de dados.",
                request);
    }

    private ResponseEntity<ApiError> resposta(
            HttpStatus status,
            String mensagem,
            HttpServletRequest request) {
        ApiError erro = new ApiError(
                LocalDateTime.now(),
                status.value(),
                mensagem,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(erro);
    }
}
