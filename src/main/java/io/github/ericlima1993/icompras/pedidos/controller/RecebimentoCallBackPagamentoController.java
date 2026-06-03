package io.github.ericlima1993.icompras.pedidos.controller;

import io.github.ericlima1993.icompras.pedidos.controller.dto.RecebimentoCallBackDTO;
import io.github.ericlima1993.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallBackPagamentoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(@RequestBody RecebimentoCallBackDTO recebimentoCallBackDTO, @RequestHeader(required = true, name = "apiKey") String apiKey) {
        pedidoService.atualizarStatusPagamento(recebimentoCallBackDTO.codigo(),recebimentoCallBackDTO.chavePagamento(), recebimentoCallBackDTO.status(), recebimentoCallBackDTO.observacoes());

        return ResponseEntity.ok().build();
    }
}
