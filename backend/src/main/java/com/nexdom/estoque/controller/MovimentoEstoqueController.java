package com.nexdom.estoque.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexdom.estoque.dto.MovimentoEstoqueRequestDTO;
import com.nexdom.estoque.dto.MovimentoEstoqueResponseDTO;
import com.nexdom.estoque.service.MovimentoEstoqueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movimentos")
public class MovimentoEstoqueController {

	private final MovimentoEstoqueService movimentoService;

	public MovimentoEstoqueController(MovimentoEstoqueService movimentoService) {
		this.movimentoService = movimentoService;
	}

	@PostMapping
	public ResponseEntity<MovimentoEstoqueResponseDTO> registrar(@Valid @RequestBody MovimentoEstoqueRequestDTO dto) {
		return ResponseEntity.ok(movimentoService.registrarMovimento(dto));
	}

	@GetMapping
	public ResponseEntity<List<MovimentoEstoqueResponseDTO>> listarTodos() {
		return ResponseEntity.ok(movimentoService.listarTodos());
	}
}
