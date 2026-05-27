package io.github.ericlima1993.icompras.pedidos.controller.mappers;

import io.github.ericlima1993.icompras.pedidos.controller.dto.ItemPedidoDTO;
import io.github.ericlima1993.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);
}
