package dev.processo_seletivo.gerenciador_ativos.repository;

import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtivoFinanceiroRepository extends JpaRepository<AtivoFinanceiro, Long> {
}
