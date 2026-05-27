package io.github.ericlima1993.icompras.pedidos.controller.dto;

import io.github.ericlima1993.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(String dados, TipoPagamento tipoPagamento) {
}
