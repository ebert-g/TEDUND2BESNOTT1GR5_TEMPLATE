package br.com.ucsal.monitoria.exemplo.controller;

import br.com.ucsal.monitoria.exemplo.dto.ExemploRequest;
import br.com.ucsal.monitoria.exemplo.dto.ExemploResponse;
import br.com.ucsal.monitoria.exemplo.service.ExemploService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/exemplos")
public class ExemploController {

    private final ExemploService service;

    public ExemploController(ExemploService service) {
        this.service = service;
    }

    @GetMapping
    public List<ExemploResponse> listar(@RequestParam(required = false) Long cursoId) {
        return service.listar(cursoId);
    }

    @GetMapping("/{id}")
    public ExemploResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping("/vinculos/curso/{cursoId}")
    public boolean possuiVinculoComCurso(@PathVariable Long cursoId) {
        return service.possuiVinculoComCurso(cursoId);
    }

    @PostMapping
    public ResponseEntity<ExemploResponse> criar(@RequestBody @Valid ExemploRequest request) {
        ExemploResponse criado = service.criar(request);
        return ResponseEntity.created(URI.create("/api/exemplos/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public ExemploResponse atualizar(@PathVariable Long id, @RequestBody @Valid ExemploRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/inativar")
    public ExemploResponse inativar(@PathVariable Long id) {
        return service.inativar(id);
    }

    @PatchMapping("/{id}/reativar")
    public ExemploResponse reativar(@PathVariable Long id) {
        return service.reativar(id);
    }
}
