package com.nexdom.estoque.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import com.nexdom.estoque.model.TipoProduto;

public class ProdutoRequestDTO {

	@NotBlank(message = "Descricao e obrigatoria")
	private String descricao;

	@NotNull(message = "Tipo do produto e obrigatorio")
	private TipoProduto tipoProduto;

	@NotNull(message = "Valor no fornecedor e obrigatorio")
	@DecimalMin(value = "0.0", inclusive = false, message = "Valor no fornecedor deve ser maior que zero")
	private BigDecimal valorFornecedor;

	@NotNull(message = "Quantidade em estoque e obrigatoria")
	@PositiveOrZero(message = "Quantidade em estoque nao pode ser negativa")
	private Integer quantidadeEstoque;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public BigDecimal getValorFornecedor() {
		return valorFornecedor;
	}

	public void setValorFornecedor(BigDecimal valorFornecedor) {
		this.valorFornecedor = valorFornecedor;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
}
