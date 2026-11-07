package br.com.ucsal.monitoria.exemplo.exception;

import java.time.LocalDateTime;

public record ErroResponse(
        int status,
        String erro,
        String mensagem,
        String caminho,
        LocalDateTime timestamp) {
}
