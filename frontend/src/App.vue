<template>
  <div class="app">
    <header>
      <h1>Controle de Estoque</h1>
      <nav>
        <button @click="ir('produtos')">Produtos</button>
        <button @click="ir('cadastro')">Cadastrar Produto</button>
        <button @click="ir('movimento')">Movimentar Estoque</button>
        <button @click="ir('tipo')">Consulta por Tipo</button>
        <button @click="ir('lucro')">Lucro por Produto</button>
        <button @click="ir('movimentos')">Movimentos</button>
      </nav>
    </header>
    <main>
      <product-list :key="listKey" v-if="currentView === 'produtos'" @editar="editar" />
      <product-form v-else-if="currentView === 'cadastro'" :edit="editProduto" @salvo="aoSalvar" />
      <movimento-form v-else-if="currentView === 'movimento'" />
      <tipo-consulta v-else-if="currentView === 'tipo'" />
      <lucro-consulta v-else-if="currentView === 'lucro'" />
      <movimento-list v-else-if="currentView === 'movimentos'" />
      <div v-else>Selecione uma opcao no menu.</div>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ProductList from './views/ProductList.vue'
import ProductForm from './views/ProductForm.vue'
import MovimentoForm from './views/MovimentoForm.vue'
import TipoConsulta from './views/TipoConsulta.vue'
import LucroConsulta from './views/LucroConsulta.vue'
import MovimentoList from './views/MovimentoList.vue'

const currentView = ref('produtos')
const listKey = ref(0)
const editProduto = ref(null)

const ir = (view) => { currentView.value = view; editProduto.value = null }
const editar = (p) => { currentView.value = 'cadastro'; editProduto.value = p }
const aoSalvar = () => { listKey.value += 1; ir('produtos') }
</script>

<style>
.app { font-family: system-ui, sans-serif; max-width: 1000px; margin: 0 auto; padding: 16px; }
header { display: flex; align-items: center; gap: 16px; margin-bottom: 24px; }
nav button { padding: 8px 14px; cursor: pointer; border: 1px solid #ccc; background: #fff; border-radius: 6px; }
nav button:hover { background: #f5f5f5; }
main { display: flex; flex-direction: column; gap: 16px; }
table { border-collapse: collapse; width: 100%; }
th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
th { background: #f8f8f8; }
div > div { margin-bottom: 10px; }
label { display: block; margin-bottom: 4px; font-weight: 600; }
input, select { padding: 6px; width: 260px; box-sizing: border-box; }
button.act { padding: 6px 12px; cursor: pointer; border: 1px solid #ccc; background: #fff; border-radius: 4px; }
button.act:hover { background: #f5f5f5; }
.msg { margin: 8px 0; padding: 8px; background: #e7f3fe; border: 1px solid #b3e0ff; border-radius: 4px; }
.err { background: #fdecea; border: 1px solid #f5a6a6; }
</style>
