package com.nexdom.estoque.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.nexdom.estoque.model.TipoMovimentacao;

public class MovimentoEstoqueRequestDTO {

	@NotNull(message = "Produto e obrigatorio")
	private Long produtoId;

	@NotNull(message = "Tipo de movimentacao e obrigatorio")
	private TipoMovimentacao tipoMovimentacao;

	@DecimalMin(value = "0.0", inclusive = true, message = "Valor de venda nao pode ser negativo")
	private BigDecimal valorVenda;

	@NotNull(message = "Quantidade movimentada e obrigatoria")
	@Positive(message = "Quantidade movimentada deve ser maior que zero")
	private Integer quantidadeMovimentada;

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
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

	public Integer getQuantidadeMovimentada() {
		return quantidadeMovimentada;
	}

	public void setQuantidadeMovimentada(Integer quantidadeMovimentada) {
		this.quantidadeMovimentada = quantidadeMovimentada;
	}
}
