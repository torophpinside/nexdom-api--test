package com.nexdom.estoque.dto;

import java.math.BigDecimal;

import com.nexdom.estoque.model.TipoProduto;

public class ProdutoResponseDTO {

	private Long codigo;
	private String descricao;
	private TipoProduto tipoProduto;
	private BigDecimal valorFornecedor;
	private Integer quantidadeEstoque;

	public ProdutoResponseDTO() {
	}

	public ProdutoResponseDTO(Long codigo, String descricao, TipoProduto tipoProduto, BigDecimal valorFornecedor,
			Integer quantidadeEstoque) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.tipoProduto = tipoProduto;
		this.valorFornecedor = valorFornecedor;
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

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
