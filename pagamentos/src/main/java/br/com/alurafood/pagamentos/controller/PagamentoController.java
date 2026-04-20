package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.service.PagamentoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Page<PagamentoDTO> list(@PageableDefault(size = 10) Pageable pageable) {
        return this.pagamentoService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> getById(@PathVariable @NotNull Long id) {
        PagamentoDTO dto = this.pagamentoService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> save(@RequestBody @Valid PagamentoDTO pagamentoDTO,
                                             UriComponentsBuilder uriComponentsBuilder) {
        PagamentoDTO response = this.pagamentoService.createPagamento(pagamentoDTO);
        URI endereco = uriComponentsBuilder.path("/pagamentos/{id}").buildAndExpand(response.id()).toUri();

        Message message = new Message(String.format("Criei um pagamento com o id %d", response.id()).getBytes());

        this.rabbitTemplate.convertAndSend("pagamentos.ex", "", response);

        return ResponseEntity.created(endereco).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> update(@PathVariable @NotNull Long id,
                                               @RequestBody @Valid PagamentoDTO pagamentoDTO) {
        PagamentoDTO response = this.pagamentoService.updatePagamento(id, pagamentoDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDTO> delete(@PathVariable @NotNull Long id) {
        this.pagamentoService.deletePagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPagamento(@PathVariable @NotNull Long id){
        pagamentoService.confirmarPagamento(id);
    }

    public void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e){
        pagamentoService.alteraStatus(id);
    }
}