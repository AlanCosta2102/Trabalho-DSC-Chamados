package com.trabalho.sistemachamadosifrn.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginaResponse<T>(
        List<T> items,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static <T> PaginaResponse<T> de(Page<T> resultado) {
        return new PaginaResponse<>(
                resultado.getContent(),
                resultado.getNumber(),
                resultado.getSize(),
                resultado.getTotalElements(),
                resultado.getTotalPages()
        );
    }
}
