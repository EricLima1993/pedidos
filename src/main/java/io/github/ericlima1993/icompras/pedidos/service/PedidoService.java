package io.github.ericlima1993.icompras.pedidos.service;

import io.github.ericlima1993.icompras.pedidos.model.Pedido;
import io.github.ericlima1993.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.ericlima1993.icompras.pedidos.repository.PedidoRepository;
import io.github.ericlima1993.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;

    public Pedido criarPedido(Pedido pedido) {

    }
}
