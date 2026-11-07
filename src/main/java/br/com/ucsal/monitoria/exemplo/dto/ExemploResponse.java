package br.com.ucsal.monitoria.exemplo.dto;

import br.com.ucsal.monitoria.exemplo.model.Exemplo;

import java.time.LocalDate;

public record ExemploResponse(
        Long id,
        String sigla,
        String descricao,
        Long cursoId,
        LocalDate dataCadastramento,
        boolean ativo) {

    public static ExemploResponse de(Exemplo exemplo) {
        return new ExemploResponse(exemplo.getId(), exemplo.getSigla(), exemplo.getDescricao(),
                exemplo.getCursoId(), exemplo.getDataCadastramento(), exemplo.isAtivo());
    }
}
