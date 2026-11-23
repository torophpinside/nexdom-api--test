package com.nexdom.estoque.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nexdom.estoque.dto.LucroProdutoDTO;
import com.nexdom.estoque.dto.ProdutoRequestDTO;
import com.nexdom.estoque.dto.ProdutoTipoResumoDTO;
import com.nexdom.estoque.exception.RecursoNaoEncontradoException;
import com.nexdom.estoque.model.MovimentoEstoque;
import com.nexdom.estoque.model.TipoMovimentacao;
import com.nexdom.estoque.model.TipoProduto;
import com.nexdom.estoque.repository.MovimentoEstoqueRepository;
import com.nexdom.estoque.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

	@Mock
	private ProdutoRepository produtoRepository;

	@Mock
	private MovimentoEstoqueRepository movimentoRepository;

	@InjectMocks
	private ProdutoService produtoService;

	private ProdutoRequestDTO dto;
	private com.nexdom.estoque.model.Produto produto;

	@BeforeEach
	void setUp() {
		dto = new ProdutoRequestDTO();
		dto.setDescricao("Notebook");
		dto.setTipoProduto(TipoProduto.ELETRONICO);
		dto.setValorFornecedor(new BigDecimal("2000.00"));
		dto.setQuantidadeEstoque(10);

		produto = new com.nexdom.estoque.model.Produto("Notebook", TipoProduto.ELETRONICO,
				new BigDecimal("2000.00"), 10);
		produto.setCodigo(1L);
	}

	@Test
	void deveListarTodos() {
		when(produtoRepository.findAll()).thenReturn(List.of(produto));

		var result = produtoService.listarTodos();

		assertEquals(1, result.size());
		assertEquals("Notebook", result.get(0).getDescricao());
		verify(produtoRepository, times(1)).findAll();
	}

	@Test
	void deveBuscarPorId() {
		when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

		var result = produtoService.buscarPorId(1L);

		assertEquals(1L, result.getCodigo());
		assertEquals("Notebook", result.getDescricao());
	}

	@Test
	void deveLancarExcecaoAoBuscarProdutoInexistente() {
		when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RecursoNaoEncontradoException.class, () -> produtoService.buscarPorId(99L));
	}

	@Test
	void deveCriarProduto() {
		when(produtoRepository.existsByDescricao("Notebook")).thenReturn(false);
		when(produtoRepository.save(any(com.nexdom.estoque.model.Produto.class))).thenReturn(produto);

		var result = produtoService.criar(dto);

		assertEquals("Notebook", result.getDescricao());
		verify(produtoRepository, times(1)).save(any(com.nexdom.estoque.model.Produto.class));
	}

	@Test
	void deveLancarExcecaoAoCriarDescricaoDuplicada() {
		when(produtoRepository.existsByDescricao("Notebook")).thenReturn(true);

		assertThrows(IllegalArgumentException.class, () -> produtoService.criar(dto));
		verify(produtoRepository, never()).save(any(com.nexdom.estoque.model.Produto.class));
	}

	@Test
	void deveAtualizarProduto() {
		when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
		when(produtoRepository.save(any(com.nexdom.estoque.model.Produto.class))).thenReturn(produto);

		dto.setDescricao("Notebook Atualizado");
		var result = produtoService.atualizar(1L, dto);

		assertEquals("Notebook Atualizado", result.getDescricao());
	}

	@Test
	void deveLancarExcecaoAoAtualizarProdutoInexistente() {
		when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RecursoNaoEncontradoException.class, () -> produtoService.atualizar(99L, dto));
	}

	@Test
	void deveDeletarProduto() {
		when(produtoRepository.existsById(1L)).thenReturn(true);

		produtoService.deletar(1L);

		verify(produtoRepository, times(1)).deleteById(1L);
	}

	@Test
	void deveLancarExcecaoAoDeletarProdutoInexistente() {
		when(produtoRepository.existsById(99L)).thenReturn(false);

		assertThrows(RecursoNaoEncontradoException.class, () -> produtoService.deletar(99L));
	}

	@Test
	void deveListarPorTipoComQuantidadeDisponivelESaida() {
		when(produtoRepository.findByTipoProduto(TipoProduto.ELETRONICO)).thenReturn(List.of(produto));
		when(movimentoRepository.totalSaidaPorProduto(1L)).thenReturn(4);

		List<ProdutoTipoResumoDTO> result = produtoService.listarPorTipo(TipoProduto.ELETRONICO);

		assertEquals(1, result.size());
		assertEquals(TipoProduto.ELETRONICO, result.get(0).getTipoProduto());
		assertEquals(10, result.get(0).getQuantidadeDisponivel());
		assertEquals(4, result.get(0).getQuantidadeSaida());
	}

	@Test
	void deveCalcularLucroPorProduto() {
		when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
		MovimentoEstoque saida1 = new MovimentoEstoque(produto, TipoMovimentacao.SAIDA,
				new BigDecimal("3000.00"), java.time.LocalDateTime.now(), 2);
		MovimentoEstoque saida2 = new MovimentoEstoque(produto, TipoMovimentacao.SAIDA,
				new BigDecimal("3000.00"), java.time.LocalDateTime.now(), 3);
		when(movimentoRepository.saidasPorProduto(1L)).thenReturn(List.of(saida1, saida2));

		LucroProdutoDTO result = produtoService.lucroPorProduto(1L);

		assertEquals(1L, result.getCodigo());
		assertEquals(5, result.getQuantidadeTotalSaida());
		assertEquals(new BigDecimal("5000.00"), result.getTotalLucro());
	}
}
