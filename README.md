# Projeto Banco - Aplicativo de Gerenciamento de Contas

## Visão Geral

Este projeto é um aplicativo Android para gerenciamento de contas bancárias, utilizando arquitetura MVVM com Room para persistência local, LiveData para atualização reativa da UI, e ViewModel para separar lógica de negócios da interface.

---

## Estrutura do Projeto

### 1. Camada de Apresentação (UI)

- **Classes:** `ContasActivity`, `EditarContaActivity`, `ContaAdapter`, `ContaViewHolder`
- **Responsabilidades:**
   - Exibir listas de contas em um RecyclerView.
   - Permitir inserção, edição e remoção de contas.
   - Navegar entre telas usando Intents.
   - Observar dados via LiveData para atualizar a interface automaticamente.
- **Interação:**
   - Usa o `ContaViewModel` para obter dados e executar comandos.

---

### 2. Camada ViewModel

- **Classe:** `ContaViewModel`
- **Responsabilidades:**
   - Atua como intermediário entre a UI e o repositório.
   - Expor LiveData para observação pela UI.
   - Garantir que operações no banco sejam executadas em threads separadas.
- **Interação:**
   - Usa o `ContaRepository` para acessar dados.

---

### 3. Camada de Repositório

- **Classe:** `ContaRepository`
- **Responsabilidades:**
   - Centralizar acesso a dados.
   - Executar operações de banco em background.
   - Fornecer interface para CRUD e buscas.
- **Interação:**
   - Usa `ContaDAO` para executar consultas e comandos no banco.

---

### 4. Camada DAO

- **Interface:** `ContaDAO`
- **Responsabilidades:**
   - Definir operações SQL (inserir, atualizar, deletar, consultar).
   - Fornecer consultas específicas (por número, CPF, nome).
- **Interação:**
   - Usada pelo `ContaRepository`.

---

### 5. Banco de Dados

- **Classe:** `BancoDB`
- **Responsabilidades:**
   - Representar a instância do banco SQLite via Room.
   - Fornecer DAO para as camadas superiores.

---

## Fluxo de Dados - Exemplo de Edição de Conta

1. Usuário clica em editar conta na lista (`ContasActivity`).
2. `ContaViewHolder` inicia `EditarContaActivity` passando o número da conta.
3. `EditarContaActivity` pede dados ao `ContaViewModel`.
4. `ContaViewModel` usa o `ContaRepository` para buscar a conta no banco.
5. `ContaRepository` obtém os dados via `ContaDAO`.
6. Os dados retornam via LiveData para `EditarContaActivity`.
7. Usuário edita os dados e confirma.
8. `EditarContaActivity` chama `ContaViewModel.atualizar()`.
9. `ContaViewModel` manda atualizar no banco via `ContaRepository`.
10. A atualização é feita em background, e a lista atualiza automaticamente.

---

## Considerações Técnicas

- Arquitetura MVVM para separação de responsabilidades.
- Uso de LiveData e ViewModel para UI responsiva e sem travamentos.
- Room para persistência local eficiente e segura.
- Operações no banco sempre em threads separadas para evitar travar a UI.
- Repositório como camada única de acesso aos dados para facilitar manutenção.

---

## Como Rodar

1. Importe o projeto no Android Studio.
2. Compile e rode no emulador ou dispositivo físico.
3. Utilize a interface para adicionar, editar, buscar e remover contas.

---

## Contato

Para dúvidas ou sugestões, abra uma issue ou contate o desenvolvedor.

---

