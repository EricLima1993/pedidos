package io.github.ericlima1993.icompras.pedidos.controller;

import io.github.ericlima1993.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import io.github.ericlima1993.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.ericlima1993.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.ericlima1993.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.ericlima1993.icompras.pedidos.model.exception.ValidationException;
import io.github.ericlima1993.icompras.pedidos.model.exception.response.PedidoErrorResponse;
import io.github.ericlima1993.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO pedidoDTO) {
        try{
            var pedido = pedidoMapper.map(pedidoDTO);
            var novoPedido = pedidoService.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        }catch (ValidationException e){
            var erro = new PedidoErrorResponse("Erro validando pedido", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicaoNovoPagamentoDTO dto){
        try {
            pedidoService.adicionarNovoPagamento(dto.codigoPedido(), dto.dadosCartao(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        }catch(ItemNaoEncontradoException e){
            var erro = new PedidoErrorResponse("Item não encontrado","codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
}
