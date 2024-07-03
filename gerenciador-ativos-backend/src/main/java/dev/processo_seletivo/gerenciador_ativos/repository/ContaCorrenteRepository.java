package dev.processo_seletivo.gerenciador_ativos.repository;

import dev.processo_seletivo.gerenciador_ativos.model.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {
}
