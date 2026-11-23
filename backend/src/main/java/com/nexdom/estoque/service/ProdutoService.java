package com.nexdom.estoque.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexdom.estoque.dto.LucroProdutoDTO;
import com.nexdom.estoque.dto.MovimentoEstoqueRequestDTO;
import com.nexdom.estoque.dto.MovimentoEstoqueResponseDTO;
import com.nexdom.estoque.dto.ProdutoRequestDTO;
import com.nexdom.estoque.dto.ProdutoResponseDTO;
import com.nexdom.estoque.dto.ProdutoTipoResumoDTO;
import com.nexdom.estoque.exception.RecursoNaoEncontradoException;
import com.nexdom.estoque.exception.SaldoInsuficienteException;
import com.nexdom.estoque.model.MovimentoEstoque;
import com.nexdom.estoque.model.Produto;
import com.nexdom.estoque.model.TipoMovimentacao;
import com.nexdom.estoque.model.TipoProduto;
import com.nexdom.estoque.repository.MovimentoEstoqueRepository;
import com.nexdom.estoque.repository.ProdutoRepository;

@Service
@Transactional
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final MovimentoEstoqueRepository movimentoRepository;

	public ProdutoService(ProdutoRepository produtoRepository,
			MovimentoEstoqueRepository movimentoRepository) {
		this.produtoRepository = produtoRepository;
		this.movimentoRepository = movimentoRepository;
	}

	@Transactional(readOnly = true)
	public List<ProdutoResponseDTO> listarTodos() {
		return produtoRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ProdutoResponseDTO buscarPorId(Long codigo) {
		Produto produto = produtoRepository.findById(codigo)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Produto nao encontrado: " + codigo));
		return toResponse(produto);
	}

	public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
		if (produtoRepository.existsByDescricao(dto.getDescricao())) {
			throw new IllegalArgumentException("Ja existe produto com esta descricao: " + dto.getDescricao());
		}
		Produto produto = new Produto(dto.getDescricao(), dto.getTipoProduto(), dto.getValorFornecedor(),
				dto.getQuantidadeEstoque());
		return toResponse(produtoRepository.save(produto));
	}

	public ProdutoResponseDTO atualizar(Long codigo, ProdutoRequestDTO dto) {
		Produto produto = produtoRepository.findById(codigo)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Produto nao encontrado: " + codigo));
		produto.setDescricao(dto.getDescricao());
		produto.setTipoProduto(dto.getTipoProduto());
		produto.setValorFornecedor(dto.getValorFornecedor());
		produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
		return toResponse(produtoRepository.save(produto));
	}

	public void deletar(Long codigo) {
		if (!produtoRepository.existsById(codigo)) {
			throw new RecursoNaoEncontradoException("Produto nao encontrado: " + codigo);
		}
		produtoRepository.deleteById(codigo);
	}

	@Transactional(readOnly = true)
	public List<ProdutoTipoResumoDTO> listarPorTipo(TipoProduto tipo) {
		List<Produto> produtos = produtoRepository.findByTipoProduto(tipo);
		return produtos.stream().map(p -> {
			Integer saida = movimentoRepository.totalSaidaPorProduto(p.getCodigo());
			return new ProdutoTipoResumoDTO(tipo, p.getDescricao(), p.getQuantidadeEstoque(),
					saida == null ? 0 : saida);
		}).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public LucroProdutoDTO lucroPorProduto(Long codigo) {
		Produto produto = produtoRepository.findById(codigo)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Produto nao encontrado: " + codigo));
		List<MovimentoEstoque> saidas = movimentoRepository.saidasPorProduto(codigo);
		Integer quantidadeTotalSaida = saidas.stream().mapToInt(MovimentoEstoque::getQuantidadeMovimentada).sum();
		BigDecimal totalLucro = saidas.stream()
				.map(m -> m.getValorVenda().subtract(produto.getValorFornecedor())
						.multiply(BigDecimal.valueOf(m.getQuantidadeMovimentada())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return new LucroProdutoDTO(produto.getCodigo(), produto.getDescricao(), quantidadeTotalSaida, totalLucro);
	}

	private ProdutoResponseDTO toResponse(Produto produto) {
		return new ProdutoResponseDTO(produto.getCodigo(), produto.getDescricao(), produto.getTipoProduto(),
				produto.getValorFornecedor(), produto.getQuantidadeEstoque());
	}
}
