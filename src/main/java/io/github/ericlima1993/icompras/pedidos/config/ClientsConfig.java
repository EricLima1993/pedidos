package io.github.ericlima1993.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.github.ericlima1993.icompras.pedidos.client")
public class ClientsConfig {
}
