package dev.processo_seletivo.gerenciador_ativos.repository;

import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.entity.ValorMercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValorMercadoRepository extends JpaRepository<ValorMercado, Long> {
    List<ValorMercado> findByAtivoFinanceiro(AtivoFinanceiro ativoFinanceiro);
}
