package br.com.fiap.gsjava.dto;

import java.time.LocalDateTime;

public record LembreteRequestDTO (String mensagem, LocalDateTime dataHora) {

}
