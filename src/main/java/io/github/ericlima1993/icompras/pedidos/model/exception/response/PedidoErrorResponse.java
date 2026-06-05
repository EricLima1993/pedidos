package io.github.ericlima1993.icompras.pedidos.model.exception.response;

public record PedidoErrorResponse(String mensagem, String campo, String erro) {
}
