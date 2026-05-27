package io.github.ericlima1993.icompras.pedidos.model;

import io.github.ericlima1993.icompras.pedidos.model.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {
    private String dados;
    private TipoPagamento tipoPagamento;
}
