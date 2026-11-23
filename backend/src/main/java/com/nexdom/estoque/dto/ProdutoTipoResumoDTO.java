package com.nexdom.estoque.dto;

import java.math.BigDecimal;

import com.nexdom.estoque.model.TipoProduto;

public class ProdutoTipoResumoDTO {

	private TipoProduto tipoProduto;
	private String descricao;
	private Integer quantidadeDisponivel;
	private Integer quantidadeSaida;

	public ProdutoTipoResumoDTO() {
	}

	public ProdutoTipoResumoDTO(TipoProduto tipoProduto, String descricao, Integer quantidadeDisponivel,
			Integer quantidadeSaida) {
		this.tipoProduto = tipoProduto;
		this.descricao = descricao;
		this.quantidadeDisponivel = quantidadeDisponivel;
		this.quantidadeSaida = quantidadeSaida;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public Integer getQuantidadeSaida() {
		return quantidadeSaida;
	}

	public void setQuantidadeSaida(Integer quantidadeSaida) {
		this.quantidadeSaida = quantidadeSaida;
	}
}
