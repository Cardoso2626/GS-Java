package br.com.fiap.gsjava.repository;

import br.com.fiap.gsjava.model.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LembreteRepository extends JpaRepository<Lembrete, Long> {
}
