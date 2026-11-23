package com.nexdom.estoque.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoProduto tipoProduto;

	private BigDecimal valorFornecedor;

	private Integer quantidadeEstoque;

	public Produto() {
	}

	public Produto(String descricao, TipoProduto tipoProduto, BigDecimal valorFornecedor,
			Integer quantidadeEstoque) {
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
