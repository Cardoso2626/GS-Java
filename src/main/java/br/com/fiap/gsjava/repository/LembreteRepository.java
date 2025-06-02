package br.com.fiap.gsjava.repository;

import br.com.fiap.gsjava.model.Lembrete;
import br.com.fiap.gsjava.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LembreteRepository extends JpaRepository<Lembrete, Long> {

}
