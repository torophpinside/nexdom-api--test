<template>
  <div>
    <h2>Movimentar Estoque</h2>
    <div class="err" v-if="error">{{ error }}</div>
    <div class="msg" v-if="message">{{ message }}</div>
    <form @submit.prevent="registrar">
      <div>
        <label>Produto:</label><br />
        <select v-model="form.produtoId" required>
          <option v-for="p in produtos" :key="p.codigo" :value="p.codigo">
            {{ p.codigo }} - {{ p.descricao }}
          </option>
        </select>
      </div>
      <div>
        <label>Tipo:</label><br />
        <select v-model="form.tipoMovimentacao" required>
          <option value="ENTRADA">Entrada</option>
          <option value="SAIDA">Saida</option>
        </select>
      </div>
      <div v-if="form.tipoMovimentacao === 'SAIDA'">
        <label>Valor de Venda:</label><br />
        <input type="number" step="0.01" v-model="form.valorVenda" required />
      </div>
      <div>
        <label>Quantidade Movimentada:</label><br />
        <input type="number" v-model="form.quantidadeMovimentada" required />
      </div>
      <button type="submit" class="act">Registrar</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { movimentoService, produtoService } from '../services/api'

const error = ref('')
const message = ref('')
const produtos = ref([])
const form = ref({ produtoId: '', tipoMovimentacao: 'ENTRADA', valorVenda: '', quantidadeMovimentada: '' })

const carregarProdutos = async () => {
  try {
    const res = await produtoService.listar()
    produtos.value = res.data
  } catch (e) {
    error.value = 'Erro ao carregar produtos'
  }
}

const registrar = async () => {
  error.value = ''; message.value = ''
  try {
    await movimentoService.registrar(form.value)
    message.value = 'Movimento registrado com sucesso'
    form.value.quantidadeMovimentada = ''
  } catch (e) {
    error.value = e.response?.data?.message || 'Erro ao registrar movimento'
  }
}

onMounted(carregarProdutos)
</script>
