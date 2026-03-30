package br.com.alurafood.pagamentos.dto;

import br.com.alurafood.pagamentos.model.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record PagamentoDTO(
        Long id,

        @NotNull
        @Positive
        BigDecimal valor,

        @NotBlank
        @Size(max=100)
        String nome,

        @NotBlank
        @Size(max=19)
        String numero,

        @NotBlank
        @Size(max=7)
        String expiracao,

        @NotBlank
        @Size(min=3, max=3)
        String codigo,

        @NotNull
        Status status,

        @NotNull
        Long pedidoId,

        @NotNull
        Long formaDePagamentoId) {
}