package dev.processo_seletivo.gerenciador_ativos.controller;

import dev.processo_seletivo.gerenciador_ativos.dto.MovimentacaoDto;
import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.entity.Movimentacao;
import dev.processo_seletivo.gerenciador_ativos.service.MovimentacaoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v0/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @PostMapping
    public Movimentacao incluirMovimentacao(@RequestBody MovimentacaoDto movimentacaoDto) {
        return movimentacaoService.incluirMovimentacao(movimentacaoDto);
    }

    @GetMapping("/conta/{id}/")
    public List<Movimentacao> consultarMovimentacoesPorContaNoPeriodo(
        @PathVariable("id") Long contaCorrenteId,
        @RequestParam("data-i") LocalDateTime dataInicio,
        @RequestParam("data-f") LocalDateTime dataFim) {

        return movimentacaoService.consultarMovimentacoesPorContaNoPeriodo(
            contaCorrenteId,
            dataInicio,
            dataFim);
    }

}
