package io.github.ericlima1993.icompras.pedidos.service;

import io.github.ericlima1993.icompras.pedidos.client.ClientesClient;
import io.github.ericlima1993.icompras.pedidos.client.ProdutosClient;
import io.github.ericlima1993.icompras.pedidos.client.ServicoBancarioClient;
import io.github.ericlima1993.icompras.pedidos.client.representation.ClienteRepresentation;
import io.github.ericlima1993.icompras.pedidos.model.DadosPagamento;
import io.github.ericlima1993.icompras.pedidos.model.ItemPedido;
import io.github.ericlima1993.icompras.pedidos.model.Pedido;
import io.github.ericlima1993.icompras.pedidos.model.enums.StatusPedido;
import io.github.ericlima1993.icompras.pedidos.model.enums.TipoPagamento;
import io.github.ericlima1993.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.ericlima1993.icompras.pedidos.publisher.PagamentoPublisher;
import io.github.ericlima1993.icompras.pedidos.repository.ItemPedidoRepository;
import io.github.ericlima1993.icompras.pedidos.repository.PedidoRepository;
import io.github.ericlima1993.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient apiCliente;
    private final ProdutosClient apiProduto;
    private final PagamentoPublisher pagamentoPublisher;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        pedidoValidator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    public void atualizarStatusPagamento(Long codigo, String s, boolean status, String observacoes) {
        var pedidoEncontrado = pedidoRepository.findByCodigoAndChavePagamento(codigo, s);
        if(pedidoEncontrado.isEmpty()){
            var msg = String.format("Pedido não encontrado para a código %d e chave de pagamento %s", codigo, s);
            log.error(msg);
            return;
        }
        Pedido pedido = pedidoEncontrado.get();

        if(status){
            pedido.setStatus(StatusPedido.PAGO);
            carregarDadosCompletosPedido(pedido);
            pagamentoPublisher.publicar(pedido);
        }else{
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipo){
        var pedidoEncontrado = pedidoRepository.findById(codigoPedido);

        if(pedidoEncontrado.isEmpty()){
            throw new ItemNaoEncontradoException("Pedido não encontrado para o código informado.");
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipo);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando o processamento.");

        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);
    }

    public Pedido carregarDadosCompletosPedido(Pedido pedido){
        carregarDadosCliente(pedido);
        carregarItensPedido(pedido);

        return pedido;
    }

    private void carregarDadosCliente(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        var response = apiCliente.obterDados(codigoCliente);
        pedido.setDadosCliente(response.getBody());
    }

    private void carregarItensPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemPedido item){
        Long codigoProduto = item.getCodigoProduto();
        var response = apiProduto.obterDados(codigoProduto);
        item.setNome(response.getBody().nome());
    }
}
