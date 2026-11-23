<template>
  <div>
    <h2>Movimentos</h2>
    <div class="err" v-if="error">{{ error }}</div>
    <table v-if="movimentos.length">
      <thead>
        <tr>
          <th>ID</th><th>Produto</th><th>Tipo</th><th>Valor Venda</th><th>Data</th><th>Quantidade</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="m in movimentos" :key="m.id">
          <td>{{ m.id }}</td>
          <td>{{ m.produtoDescricao }}</td>
          <td>{{ m.tipoMovimentacao }}</td>
          <td>{{ m.valorVenda }}</td>
          <td>{{ m.dataVenda }}</td>
          <td>{{ m.quantidadeMovimentada }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { movimentoService } from '../services/api'

const movimentos = ref([])
const error = ref('')

const carregar = async () => {
  try {
    const res = await movimentoService.listar()
    movimentos.value = res.data
  } catch (e) { error.value = 'Erro ao listar movimentos' }
}

onMounted(carregar)
</script>
