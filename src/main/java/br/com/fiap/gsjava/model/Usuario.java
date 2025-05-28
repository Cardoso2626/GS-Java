package br.com.fiap.gsjava.model;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "tb_usuarios")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;
    private String cpf;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "localizacao_id")
    private Localizacao localizacao;

    @OneToMany(mappedBy = "usuario")
    private List<Lembrete> lembretes;

    public Usuario(Long id, String email, String senha, String cpf, String nome, Localizacao localizacao, List<Lembrete> lembretes) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.nome = nome;
        this.localizacao = localizacao;
        this.lembretes = lembretes;
    }
    public Usuario() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public List<Lembrete> getLembretes() {
        return lembretes;
    }

    public void setLembretes(List<Lembrete> lembretes) {
        this.lembretes = lembretes;
    }
}
