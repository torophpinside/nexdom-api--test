package com.nexdom.estoque.dto;

import java.math.BigDecimal;

public class LucroProdutoDTO {

	private Long codigo;
	private String descricao;
	private Integer quantidadeTotalSaida;
	private BigDecimal totalLucro;

	public LucroProdutoDTO() {
	}

	public LucroProdutoDTO(Long codigo, String descricao, Integer quantidadeTotalSaida, BigDecimal totalLucro) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.quantidadeTotalSaida = quantidadeTotalSaida;
		this.totalLucro = totalLucro;
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

	public Integer getQuantidadeTotalSaida() {
		return quantidadeTotalSaida;
	}

	public void setQuantidadeTotalSaida(Integer quantidadeTotalSaida) {
		this.quantidadeTotalSaida = quantidadeTotalSaida;
	}

	public BigDecimal getTotalLucro() {
		return totalLucro;
	}

	public void setTotalLucro(BigDecimal totalLucro) {
		this.totalLucro = totalLucro;
	}
}
