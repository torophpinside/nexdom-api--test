package com.nexdom.estoque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nexdom.estoque.model.Produto;
import com.nexdom.estoque.model.TipoProduto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByTipoProduto(TipoProduto tipoProduto);

	@Query("SELECT p FROM Produto p WHERE p.tipoProduto = :tipo")
	List<Produto> findByTipo(@Param("tipo") TipoProduto tipo);

	boolean existsByDescricao(String descricao);
}
