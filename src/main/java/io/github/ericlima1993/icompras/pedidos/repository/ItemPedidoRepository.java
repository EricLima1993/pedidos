package io.github.ericlima1993.icompras.pedidos.repository;

import io.github.ericlima1993.icompras.pedidos.model.ItemPedido;
import io.github.ericlima1993.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
