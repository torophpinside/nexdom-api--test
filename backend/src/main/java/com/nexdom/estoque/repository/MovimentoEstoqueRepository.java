package com.nexdom.estoque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nexdom.estoque.model.MovimentoEstoque;

@Repository
public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {

	@Query("SELECT SUM(m.quantidadeMovimentada) FROM MovimentoEstoque m WHERE m.produto.id = :produtoId AND m.tipoMovimentacao = 'SAIDA'")
	Integer totalSaidaPorProduto(@Param("produtoId") Long produtoId);

	@Query("SELECT m FROM MovimentoEstoque m WHERE m.produto.id = :produtoId AND m.tipoMovimentacao = 'SAIDA'")
	List<MovimentoEstoque> saidasPorProduto(@Param("produtoId") Long produtoId);

}
