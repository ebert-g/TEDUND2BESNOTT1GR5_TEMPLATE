package br.com.ucsal.monitoria.exemplo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExemploRequest(
        @NotBlank @Size(max = 20) String sigla,
        @NotBlank @Size(max = 150) String descricao,
        @NotNull Long cursoId) {
}
