package com.nexdom.estoque.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimento_estoque")
public class MovimentoEstoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Produto produto;

	@Enumerated(EnumType.STRING)
	private TipoMovimentacao tipoMovimentacao;

	private BigDecimal valorVenda;

	private LocalDateTime dataVenda;

	private Integer quantidadeMovimentada;

	public MovimentoEstoque() {
	}

	public MovimentoEstoque(Produto produto, TipoMovimentacao tipoMovimentacao, BigDecimal valorVenda,
			LocalDateTime dataVenda, Integer quantidadeMovimentada) {
		this.produto = produto;
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
