package dev.processo_seletivo.gerenciador_ativos.repository;

import dev.processo_seletivo.gerenciador_ativos.model.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByContaCorrente(ContaCorrente contaCorrente);
}
