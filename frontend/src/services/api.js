import axios from 'axios'

const API = axios.create({ baseURL: 'http://localhost:8081' })

export const produtoService = {
  listar() { return API.get('/produtos') },
  buscar(id) { return API.get(`/produtos/${id}`) },
  criar(data) { return API.post('/produtos', data) },
  atualizar(id, data) { return API.put(`/produtos/${id}`, data) },
  deletar(id) { return API.delete(`/produtos/${id}`) },
  porTipo(tipo) { return API.get(`/produtos/tipo/${tipo}`) },
  lucro(id) { return API.get(`/produtos/${id}/lucro`) }
}

export const movimentoService = {
  listar() { return API.get('/movimentos') },
  registrar(data) { return API.post('/movimentos', data) }
}
