package br.com.fiap.gsjava.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "tb_lembrete")
@Entity
public class Lembrete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Usuario idUsuario;
    private String mensagem;
    private LocalDateTime dataHora;

    public Lembrete(Long id, Usuario idUsuario, String mensagem, LocalDateTime dataHora) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    public Lembrete() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

}
