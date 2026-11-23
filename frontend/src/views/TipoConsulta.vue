<template>
  <div>
    <h2>Consulta por Tipo</h2>
    <div>
      <label>Tipo:</label>
      <select v-model="tipo">
        <option value="ELETRONICO">Eletronico</option>
        <option value="ELETRODOMESTICO">Eletrodomestico</option>
        <option value="MOVEL">Moveis</option>
      </select>
      <button class="act" @click="consultar">Consultar</button>
    </div>
    <div class="err" v-if="error">{{ error }}</div>
    <table v-if="resumo.length">
      <thead>
        <tr>
          <th>Tipo</th><th>Descricao</th><th>Quantidade Disponivel</th><th>Quantidade Saida</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="r in resumo" :key="r.tipoProduto">
          <td>{{ r.tipoProduto }}</td>
          <td>{{ r.descricao }}</td>
          <td>{{ r.quantidadeDisponivel }}</td>
          <td>{{ r.quantidadeSaida }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { produtoService } from '../services/api'

const tipo = ref('ELETRONICO')
const resumo = ref([])
const error = ref('')

const consultar = async () => {
  error.value = ''
  try {
    const res = await produtoService.porTipo(tipo.value)
    resumo.value = res.data.map(p => ({
      tipoProduto: p.tipoProduto,
      descricao: p.descricao,
      quantidadeDisponivel: p.quantidadeDisponivel,
      quantidadeSaida: p.quantidadeSaida
    }))
  } catch (e) { error.value = 'Erro ao consultar' }
}
</script>
