package io.github.ericlima1993.icompras.pedidos.repository;

import io.github.ericlima1993.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
