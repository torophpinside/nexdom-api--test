<template>
  <div>
    <h2>Produtos</h2>
    <div class="msg" v-if="message">{{ message }}</div>
    <div class="err" v-if="error">{{ error }}</div>
    <table>
      <thead>
        <tr>
          <th>Codigo</th><th>Descricao</th><th>Tipo</th><th>Valor Fornecedor</th><th>Estoque</th><th>Acoes</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in produtos" :key="p.codigo">
          <td>{{ p.codigo }}</td>
          <td>{{ p.descricao }}</td>
          <td>{{ p.tipoProduto }}</td>
          <td>{{ p.valorFornecedor }}</td>
          <td>{{ p.quantidadeEstoque }}</td>
          <td>
            <button class="act" @click="$emit('editar', p)">Editar</button>
            <button class="act" @click="verLucro(p.codigo)">Lucro</button>
            <button class="act" @click="excluir(p.codigo)">Excluir</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { produtoService } from '../services/api'

const produtos = ref([])
const message = ref('')
const error = ref('')

const carregar = async () => {
  try {
    const res = await produtoService.listar()
    produtos.value = res.data
  } catch (e) { error.value = 'Erro ao listar produtos' }
}

const excluir = async (id) => {
  if (!confirm('Excluir este produto?')) return
  try {
    await produtoService.deletar(id)
    message.value = 'Produto excluido'
    error.value = ''
    await carregar()
  } catch (e) { error.value = 'Erro ao excluir' }
}

const verLucro = async (id) => {
  try {
    const res = await produtoService.lucro(id)
    const l = res.data
    alert(`Produto: ${l.descricao}\nTotal saida: ${l.quantidadeTotalSaida}\nTotal lucro: ${l.totalLucro}`)
  } catch (e) { error.value = 'Erro ao consultar lucro' }
}

onMounted(carregar)
</script>
