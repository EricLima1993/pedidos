package io.github.ericlima1993.icompras.pedidos.repository;

import io.github.ericlima1993.icompras.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
