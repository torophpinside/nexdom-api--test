<template>
  <div>
    <h2>Lucro por Produto</h2>
    <div>
      <label>Produto:</label>
      <select v-model="codigo">
        <option v-for="p in produtos" :key="p.codigo" :value="p.codigo">
          {{ p.codigo }} - {{ p.descricao }}
        </option>
      </select>
      <button class="act" @click="consultar">Consultar</button>
    </div>
    <div class="err" v-if="error">{{ error }}</div>
    <div v-if="lucro" class="msg">
      <p><strong>Produto:</strong> {{ lucro.descricao }} (codigo {{ lucro.codigo }})</p>
      <p><strong>Quantidade total saida:</strong> {{ lucro.quantidadeTotalSaida }}</p>
      <p><strong>Total lucro:</strong> {{ lucro.totalLucro }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { produtoService } from '../services/api'

const codigo = ref('')
const produtos = ref([])
const lucro = ref(null)
const error = ref('')

const carregarProdutos = async () => {
  try {
    const res = await produtoService.listar()
    produtos.value = res.data
  } catch (e) {
    error.value = 'Erro ao carregar produtos'
  }
}

const consultar = async () => {
  error.value = ''; lucro.value = null
  if (!codigo.value) return
  try {
    const res = await produtoService.lucro(Number(codigo.value))
    lucro.value = res.data
  } catch (e) { error.value = 'Erro ao consultar lucro' }
}

onMounted(carregarProdutos)
</script>
