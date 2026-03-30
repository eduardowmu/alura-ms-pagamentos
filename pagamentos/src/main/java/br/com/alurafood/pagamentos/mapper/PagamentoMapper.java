package br.com.alurafood.pagamentos.mapper;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "expiracao", target = "expiracao")
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "pedidoId", target = "pedidoId")
    @Mapping(source = "formaDePagamentoId", target = "formaDePagamentoId")
    Pagamento toPagamento(PagamentoDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "valor", target = "valor")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "expiracao", target = "expiracao")
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "pedidoId", target = "pedidoId")
    @Mapping(source = "formaDePagamentoId", target = "formaDePagamentoId")
    PagamentoDTO toDto(Pagamento pagamento);
}