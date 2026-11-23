package com.nexdom.estoque.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexdom.estoque.dto.LucroProdutoDTO;
import com.nexdom.estoque.dto.ProdutoRequestDTO;
import com.nexdom.estoque.dto.ProdutoResponseDTO;
import com.nexdom.estoque.dto.ProdutoTipoResumoDTO;
import com.nexdom.estoque.model.TipoProduto;
import com.nexdom.estoque.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
		return ResponseEntity.ok(produtoService.listarTodos());
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long codigo) {
		return ResponseEntity.ok(produtoService.buscarPorId(codigo));
	}

	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
		return ResponseEntity.ok(produtoService.criar(dto));
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long codigo,
			@Valid @RequestBody ProdutoRequestDTO dto) {
		return ResponseEntity.ok(produtoService.atualizar(codigo, dto));
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
		produtoService.deletar(codigo);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<ProdutoTipoResumoDTO>> listarPorTipo(@PathVariable TipoProduto tipo) {
		return ResponseEntity.ok(produtoService.listarPorTipo(tipo));
	}

	@GetMapping("/{codigo}/lucro")
	public ResponseEntity<LucroProdutoDTO> lucro(@PathVariable Long codigo) {
		return ResponseEntity.ok(produtoService.lucroPorProduto(codigo));
	}
}
