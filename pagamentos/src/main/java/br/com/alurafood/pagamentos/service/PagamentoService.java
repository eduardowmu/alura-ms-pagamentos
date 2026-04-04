package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.mapper.PagamentoMapper;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Autowired
    private PedidoClient pedidoClient;

    public Page<PagamentoDTO> getAll(Pageable pageable) {
        return this.pagamentoRepository.findAll(pageable)
                .map(p -> this.pagamentoMapper.toDto(p));
    }

    public PagamentoDTO getById(Long id) {
        Pagamento pagamento = this.pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Pagamento %d não encontrado", id)));

        return this.pagamentoMapper.toDto(pagamento);
    }

    public PagamentoDTO createPagamento(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = this.pagamentoMapper.toPagamento(pagamentoDTO);
                //this.modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        Pagamento pagamentoCriado = this.pagamentoRepository.save(pagamento);
        return this.pagamentoMapper.toDto(pagamentoCriado);
    }

    public PagamentoDTO updatePagamento(Long id, PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = this.pagamentoMapper.toPagamento(pagamentoDTO);
        pagamento.setId(id);
        Pagamento pagamentoAtualizado = this.pagamentoRepository.save(pagamento);
        return this.pagamentoMapper.toDto(pagamentoAtualizado);
    }

    public void deletePagamento(Long id) {
        this.pagamentoRepository.deleteById(id);
    }

    public void confirmarPagamento(Long id){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        pagamentoRepository.save(pagamento.get());
        pedidoClient.atualizaPagamento(pagamento.get().getPedidoId());
    }

    public void alteraStatus(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Pagamento %d não encontrado", id)));

        pagamento.setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        pagamentoRepository.save(pagamento);
    }
}