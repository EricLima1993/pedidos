package io.github.ericlima1993.icompras.pedidos.controller.dto;

public record RecebimentoCallBackDTO(Long codigo, String chavePagamento, boolean status, String observacoes) {
}
