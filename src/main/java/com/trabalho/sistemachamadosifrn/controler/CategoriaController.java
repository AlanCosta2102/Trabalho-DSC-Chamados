
        package com.trabalho.sistemachamadosifrn.controler;

import com.trabalho.sistemachamadosifrn.dto.request.CriarCategoriaRequest;
import com.trabalho.sistemachamadosifrn.dto.response.CategoriaResponse;
import com.trabalho.sistemachamadosifrn.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(
            @Valid @RequestBody CriarCategoriaRequest request) {

        CategoriaResponse categoria = categoriaService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> listar(
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(categoriaService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CriarCategoriaRequest request) {

        return ResponseEntity.ok(
                categoriaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        categoriaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
