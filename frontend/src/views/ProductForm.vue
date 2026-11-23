<template>
  <div>
    <h2>{{ edit ? 'Editar Produto' : 'Cadastrar Produto' }}</h2>
    <div class="err" v-if="error">{{ error }}</div>
    <form @submit.prevent="salvar">
      <div>
        <label>Descricao:</label><br />
        <input v-model="form.descricao" required />
      </div>
      <div>
        <label>Tipo:</label><br />
        <select v-model="form.tipoProduto" required>
          <option value="ELETRONICO">Eletronico</option>
          <option value="ELETRODOMESTICO">Eletrodomestico</option>
          <option value="MOVEL">Moveis</option>
        </select>
      </div>
      <div>
        <label>Valor no Fornecedor:</label><br />
        <input type="number" step="0.01" v-model="form.valorFornecedor" required />
      </div>
      <div>
        <label>Quantidade em Estoque:</label><br />
        <input type="number" v-model="form.quantidadeEstoque" required />
      </div>
      <button type="submit" class="act">Salvar</button>
      <button type="button" class="act" @click="cancelar">Cancelar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { produtoService } from '../services/api'

const props = defineProps(['edit'])
const emit = defineEmits(['salvo'])
const error = ref('')
const form = ref({ descricao: '', tipoProduto: 'ELETRONICO', valorFornecedor: '', quantidadeEstoque: '' })

const salvar = async () => {
  error.value = ''
  try {
    if (props.edit) {
      await produtoService.atualizar(props.edit.codigo, form.value)
    } else {
      await produtoService.criar(form.value)
    }
    emit('salvo')
  } catch (e) {
    error.value = e.response?.data?.message || 'Erro ao salvar'
  }
}

const cancelar = () => { emit('salvo') }
</script>
