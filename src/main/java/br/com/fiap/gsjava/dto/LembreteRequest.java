package br.com.fiap.gsjava.dto;

import java.time.LocalDateTime;

public record LembreteRequest (Long id, String mensagem, LocalDateTime dataHora, Long idUsuario) {
}
