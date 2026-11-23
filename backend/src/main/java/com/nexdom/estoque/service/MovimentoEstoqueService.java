package com.nexdom.estoque.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexdom.estoque.dto.MovimentoEstoqueRequestDTO;
import com.nexdom.estoque.dto.MovimentoEstoqueResponseDTO;
import com.nexdom.estoque.exception.RecursoNaoEncontradoException;
import com.nexdom.estoque.exception.SaldoInsuficienteException;
import com.nexdom.estoque.model.MovimentoEstoque;
import com.nexdom.estoque.model.Produto;
import com.nexdom.estoque.model.TipoMovimentacao;
import com.nexdom.estoque.repository.MovimentoEstoqueRepository;
import com.nexdom.estoque.repository.ProdutoRepository;

@Service
@Transactional
public class MovimentoEstoqueService {

	private final ProdutoRepository produtoRepository;
	private final MovimentoEstoqueRepository movimentoRepository;

	public MovimentoEstoqueService(ProdutoRepository produtoRepository,
			MovimentoEstoqueRepository movimentoRepository) {
		this.produtoRepository = produtoRepository;
		this.movimentoRepository = movimentoRepository;
	}

	public MovimentoEstoqueResponseDTO registrarMovimento(MovimentoEstoqueRequestDTO dto) {
		Produto produto = produtoRepository.findById(dto.getProdutoId())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Produto nao encontrado: " + dto.getProdutoId()));

		if (dto.getTipoMovimentacao() == TipoMovimentacao.SAIDA) {
			if (produto.getQuantidadeEstoque() < dto.getQuantidadeMovimentada()) {
				throw new SaldoInsuficienteException(
					"Saldo insuficiente para o produto '" + produto.getDescricao() + "'. Disponivel: "
						+ produto.getQuantidadeEstoque() + ", solicitado: " + dto.getQuantidadeMovimentada());
			}
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.getQuantidadeMovimentada());
		} else if (dto.getTipoMovimentacao() == TipoMovimentacao.ENTRADA) {
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + dto.getQuantidadeMovimentada());
		}

		produtoRepository.save(produto);

		MovimentoEstoque movimento = new MovimentoEstoque(produto, dto.getTipoMovimentacao(),
				dto.getValorVenda(), LocalDateTime.now(), dto.getQuantidadeMovimentada());
		MovimentoEstoque salvo = movimentoRepository.save(movimento);
		return toResponse(salvo);
	}

	@Transactional(readOnly = true)
	public List<MovimentoEstoqueResponseDTO> listarTodos() {
		return movimentoRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	private MovimentoEstoqueResponseDTO toResponse(MovimentoEstoque m) {
		return new MovimentoEstoqueResponseDTO(m.getId(), m.getProduto().getCodigo(),
				m.getProduto().getDescricao(), m.getTipoMovimentacao(), m.getValorVenda(), m.getDataVenda(),
				m.getQuantidadeMovimentada());
	}
}
