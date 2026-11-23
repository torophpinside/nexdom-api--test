package com.nexdom.estoque.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nexdom.estoque.dto.MovimentoEstoqueRequestDTO;
import com.nexdom.estoque.exception.RecursoNaoEncontradoException;
import com.nexdom.estoque.exception.SaldoInsuficienteException;
import com.nexdom.estoque.model.MovimentoEstoque;
import com.nexdom.estoque.model.Produto;
import com.nexdom.estoque.model.TipoMovimentacao;
import com.nexdom.estoque.model.TipoProduto;
import com.nexdom.estoque.repository.MovimentoEstoqueRepository;
import com.nexdom.estoque.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class MovimentoEstoqueServiceTest {

	@Mock
	private ProdutoRepository produtoRepository;

	@Mock
	private MovimentoEstoqueRepository movimentoRepository;

	@InjectMocks
	private MovimentoEstoqueService movimentoService;

	private Produto produto;

	@BeforeEach
	void setUp() {
		produto = new Produto("Notebook", TipoProduto.ELETRONICO, new BigDecimal("2000.00"), 10);
		produto.setCodigo(1L);
	}

	@Test
	void deveRegistrarEntradaEAumentarEstoque() {
		MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO();
		dto.setProdutoId(1L);
		dto.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		dto.setQuantidadeMovimentada(5);

		when(produtoRepository.findById(1L)).thenReturn(java.util.Optional.of(produto));
		when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
		when(movimentoRepository.save(any(MovimentoEstoque.class)))
			.thenAnswer(inv -> inv.getArgument(0));

		var result = movimentoService.registrarMovimento(dto);

		assertEquals(TipoMovimentacao.ENTRADA, result.getTipoMovimentacao());
		assertEquals(15, produto.getQuantidadeEstoque());
		verify(produtoRepository, times(1)).save(produto);
		verify(movimentoRepository, times(1)).save(any(MovimentoEstoque.class));
	}

	@Test
	void deveRegistrarSaidaEDiminuirEstoque() {
		MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO();
		dto.setProdutoId(1L);
		dto.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		dto.setValorVenda(new BigDecimal("3000.00"));
		dto.setQuantidadeMovimentada(4);

		when(produtoRepository.findById(1L)).thenReturn(java.util.Optional.of(produto));
		when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
		when(movimentoRepository.save(any(MovimentoEstoque.class)))
			.thenAnswer(inv -> inv.getArgument(0));

		var result = movimentoService.registrarMovimento(dto);

		assertEquals(TipoMovimentacao.SAIDA, result.getTipoMovimentacao());
		assertEquals(6, produto.getQuantidadeEstoque());
		assertEquals(4, result.getQuantidadeMovimentada());
	}

	@Test
	void deveLancarSaldoInsuficienteAoRegistrarSaida() {
		MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO();
		dto.setProdutoId(1L);
		dto.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		dto.setQuantidadeMovimentada(20);

		when(produtoRepository.findById(1L)).thenReturn(java.util.Optional.of(produto));

		assertThrows(SaldoInsuficienteException.class, () -> movimentoService.registrarMovimento(dto));
		verify(produtoRepository, times(0)).save(any(Produto.class));
		verify(movimentoRepository, times(0)).save(any(MovimentoEstoque.class));
	}

	@Test
	void mensagemSaldoInsuficienteDeveSerEspecifica() {
		MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO();
		dto.setProdutoId(1L);
		dto.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		dto.setQuantidadeMovimentada(20);

		when(produtoRepository.findById(1L)).thenReturn(java.util.Optional.of(produto));

		Exception ex = assertThrows(SaldoInsuficienteException.class,
			() -> movimentoService.registrarMovimento(dto));
		assertEquals("Saldo insuficiente para o produto 'Notebook'. Disponivel: 10, solicitado: 20", ex.getMessage());
	}

	@Test
	void deveLancarExcecaoAoRegistrarMovimentoProdutoInexistente() {
		MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO();
		dto.setProdutoId(99L);
		dto.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		dto.setQuantidadeMovimentada(5);

		when(produtoRepository.findById(99L)).thenReturn(java.util.Optional.empty());

		assertThrows(RecursoNaoEncontradoException.class, () -> movimentoService.registrarMovimento(dto));
	}
}
