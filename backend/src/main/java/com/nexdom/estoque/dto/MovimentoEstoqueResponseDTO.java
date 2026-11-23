package com.nexdom.estoque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nexdom.estoque.model.TipoMovimentacao;

public class MovimentoEstoqueResponseDTO {

	private Long id;
	private Long produtoId;
	private String produtoDescricao;
	private TipoMovimentacao tipoMovimentacao;
	private BigDecimal valorVenda;
	private LocalDateTime dataVenda;
	private Integer quantidadeMovimentada;

	public MovimentoEstoqueResponseDTO() {
	}

	public MovimentoEstoqueResponseDTO(Long id, Long produtoId, String produtoDescricao,
			TipoMovimentacao tipoMovimentacao, BigDecimal valorVenda, LocalDateTime dataVenda,
			Integer quantidadeMovimentada) {
		this.id = id;
		this.produtoId = produtoId;
		this.produtoDescricao = produtoDescricao;
		this.tipoMovimentacao = tipoMovimentacao;
		this.valorVenda = valorVenda;
		this.dataVenda = dataVenda;
		this.quantidadeMovimentada = quantidadeMovimentada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getProdutoDescricao() {
		return produtoDescricao;
	}

	public void setProdutoDescricao(String produtoDescricao) {
		this.produtoDescricao = produtoDescricao;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public LocalDateTime getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDateTime dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Integer getQuantidadeMovimentada() {
		return quantidadeMovimentada;
	}

	public void setQuantidadeMovimentada(Integer quantidadeMovimentada) {
		this.quantidadeMovimentada = quantidadeMovimentada;
	}
}
