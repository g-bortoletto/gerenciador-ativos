package dev.processo_seletivo.gerenciador_ativos.controller;

import dev.processo_seletivo.gerenciador_ativos.dto.AtivoFinanceiroDto;
import dev.processo_seletivo.gerenciador_ativos.dto.ValorMercadoDto;
import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.entity.ValorMercado;
import dev.processo_seletivo.gerenciador_ativos.service.AtivoFinanceiroService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v0/ativos-financeiros")
public class AtivoFinanceiroController {

    @Autowired
    private AtivoFinanceiroService ativoFinanceiroService;

    @PostMapping
    public ResponseEntity<AtivoFinanceiro> incluirAtivoFinanceiro(@RequestBody AtivoFinanceiroDto ativoFinanceiro) {
        return ResponseEntity.ok(ativoFinanceiroService.incluirAtivoFinanceiro(ativoFinanceiro));
    }

    @GetMapping
    public ResponseEntity<List<AtivoFinanceiro>> consultarAtivosFinanceiros() {
        return ResponseEntity.ok(ativoFinanceiroService.consultarAtivosFinanceiros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivoFinanceiro> consultarAtivoFinanceiroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ativoFinanceiroService.consultarAtivoFinanceiroPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtivoFinanceiro> editarAtivoFinanceiro(@PathVariable Long id, @RequestBody AtivoFinanceiroDto detalhesAtivoFinanceiro) {
        return ResponseEntity.ok(ativoFinanceiroService.editarAtivoFinanceiro(id, detalhesAtivoFinanceiro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AtivoFinanceiro> removerAtivoFinanceiro(@PathVariable Long id) {
        return ResponseEntity.ok(ativoFinanceiroService.removerAtivoFinanceiro(id));
    }

    @PostMapping("/{id}/valores-mercado")
    public ResponseEntity<AtivoFinanceiro> incluirValorMercado(
        @PathVariable("id") Long ativoFinanceiroId,
        @RequestBody @NotNull ValorMercadoDto valorMercado) {
        return ResponseEntity.ok(ativoFinanceiroService.incluirValorMercado(
            ativoFinanceiroId,
            valorMercado.getData(),
            valorMercado.getValor()));
    }

    @GetMapping("/{id}/valores-mercado")
    public ResponseEntity<List<ValorMercado>> consultarValoresMercado(@PathVariable("id") Long ativoFinanceiroId) {
        return ResponseEntity.ok(ativoFinanceiroService.consultarValoresMercado(ativoFinanceiroId));
    }

    @DeleteMapping(value = "/{id}/valores-mercado")
    public ResponseEntity<AtivoFinanceiro> removerValorMercado(@PathVariable("id") Long ativoFinanceiroId, @RequestBody Long valorMercadoId) {
        return ResponseEntity.ok(ativoFinanceiroService.removerValorMercado(valorMercadoId));
    }

}
