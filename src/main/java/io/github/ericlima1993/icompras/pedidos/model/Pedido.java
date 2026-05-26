package io.github.ericlima1993.icompras.pedidos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;
}
