package io.github.ericlima1993.icompras.pedidos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Data
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;

    @JoinColumn(name = "codigo_pedido")
    @ManyToOne
    private Pedido pedido;

    @Column(name = "codigo_produto")
    private Long codigoProduto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor_unitario", precision = 16, scale = 2)
    private BigDecimal valorUnitario;
}
