package br.com.alurafood.pagamentos.mapper;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.valor", target = "valor")
    @Mapping(source = "dto.nome", target = "nome")
    @Mapping(source = "dto.numero", target = "numero")
    @Mapping(source = "dto.expiracao", target = "expiracao")
    @Mapping(source = "dto.codigo", target = "codigo")
    @Mapping(source = "dto.status", target = "status")
    @Mapping(source = "dto.pedidoId", target = "pedidoId")
    @Mapping(source = "dto.formaDePagamentoId", target = "formaDePagamentoId")
    Pagamento toPagamento(PagamentoDTO dto);

    @Mapping(source = "pagamento.id", target = "id")
    @Mapping(source = "pagamento.valor", target = "valor")
    @Mapping(source = "pagamento.nome", target = "nome")
    @Mapping(source = "pagamento.numero", target = "numero")
    @Mapping(source = "pagamento.expiracao", target = "expiracao")
    @Mapping(source = "pagamento.codigo", target = "codigo")
    @Mapping(source = "pagamento.status", target = "status")
    @Mapping(source = "pagamento.pedidoId", target = "pedidoId")
    @Mapping(source = "pagamento.formaDePagamentoId", target = "formaDePagamentoId")
    PagamentoDTO toDto(Pagamento pagamento);
}