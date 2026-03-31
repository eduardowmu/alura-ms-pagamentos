package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PagamentoDTO> getAll(Pageable pageable) {
        return this.pagamentoRepository.findAll(pageable)
                .map(p -> this.modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO getById(Long id) {
        Pagamento pagamento = this.pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Pagamento %d não encontrado", id)));

        return this.modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO createPagamento(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = this.modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        Pagamento pagamentoCriado = this.pagamentoRepository.save(pagamento);
        return this.modelMapper.map(pagamentoCriado, PagamentoDTO.class);
    }

    public PagamentoDTO updatePagamento(Long id, PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = this.modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setId(id);
        Pagamento pagamentoAtualizado = this.pagamentoRepository.save(pagamento);
        return this.modelMapper.map(pagamentoAtualizado, PagamentoDTO.class);
    }

    public void deletePagamento(Long id) {
        this.pagamentoRepository.deleteById(id);
    }
}