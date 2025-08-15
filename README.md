# Projeto Banco - Aplicativo de Gerenciamento de Contas

### Visão Geral do Projeto:

Este projeto é um aplicativo Android para gerenciamento de contas bancárias, utilizando arquitetura MVVM com Room para persistência local, LiveData para atualização reativa da UI, e ViewModel para separar lógica de negócios da interface.

## Passos do roteiro concluídos
Aqui listo os passos do roteiro do professor que foram implementados neste projeto, conforme numeração original:

1. RecyclerView na ContasActivity integrado com LiveData<List<Conta>> do ContaViewModel, exibindo as contas atualizadas.

2. ContaViewHolder modificado para atualizar a imagem do item conforme saldo.

3. Botão de remover conta implementado no ContaViewHolder.

4. Validação completa na AdicionarContaActivity que verifica campos obrigatórios e formatação correta do saldo antes de criar e salvar a conta.

5. Métodos @Update e @Delete implementados no ContaDAO para atualização e remoção de contas.

6. Queries no ContaDAO para buscar conta por número, nome do cliente e CPF.

7. ContaRepository e ContaViewModel implementados para operações de atualização, remoção e buscas, respeitando execução em thread de background com @WorkerThread.

8. EditarContaActivity busca dados da conta via número recebido, valida formulário, atualiza ou remove a conta usando o ContaViewModel.

9. BancoViewModel com métodos para transferir, creditar e debitar, integrando com repositórios e validando dados antes da operação.

10. Validação nas Activities DebitarActivity, CreditarActivity e TransferirActivity para verificar existência das contas e valores positivos antes das operações.

11. Implementação na PesquisarActivity para buscas pelo tipo selecionado (número, nome, CPF), atualizando o RecyclerView com resultados em tempo real.

12. Na MainActivity, exibição do valor total armazenado no banco, calculado como soma de todos os saldos (considerando possíveis saldos negativos).

13. Modificação no TransacaoViewHolder para exibir valores em vermelho no caso de transações de débito.

14. Métodos no TransacaoDAO, TransacaoRepository e TransacaoViewModel para buscar transações por número da conta, data e tipo (crédito, débito, todas).

15. TransacoesActivity implementada para filtrar e exibir transações conforme seleção do usuário, iniciando com todas as transações listadas.

## Estrutura do projeto
**Camada UI:** ContasActivity, EditarContaActivity, AdicionarContaActivity, PesquisarActivity, TransacoesActivity e seus adapters/viewholders.

**ViewModel:** ContaViewModel, BancoViewModel, TransacaoViewModel.

**Repositórios:** ContaRepository, TransacaoRepository.

**DAO:** ContaDAO, TransacaoDAO.

**Banco:** Classe BancoDB com instância do Room.

## Arquitetura do App & Logica da Interação entre os componentes

| Estrutura do App                                 | Lógica de Interação |
|--------------------------------------------------|---------------------|
| <img src="assets/appStructure.png" width="450"/> | <img src="assets/logical.png" width="300"/> |



## Decisões de implementação
Arquitetura MVVM: Mantive o padrão para separar responsabilidades.

Uso de Room: O Room foi a escolha para persistência local.

Threads separadas: Todas operações de banco foram feitas em threads de background para evitar bloqueio da interface, usando @WorkerThread.

Passagem de parâmetros via Intent: Para manter a navegação fluida e o compartilhamento de dados entre telas, utilizei Intent.putExtra para o número da conta.

Atualização reativa da UI: O LiveData foi usado para observar as listas de contas e transações, fazendo com que o RecyclerView seja atualizado automaticamente sem necessidade de refresh manual.

Imagens condicionais: Usei lógica simples no ContaViewHolder para alterar a imagem do item de acordo com o saldo.

Filtros de busca na camada DAO: Preferi fazer as filtragens direto nas queries SQL para otimizar performance e reduzir processamento na camada UI.

Reuso e organização do código: Centralizei o acesso a dados no Repository, garantindo um ponto único para operações, facilitando manutenção e possíveis melhorias futuras.

Estilo UI : Utilizei as Cores do CIn para ficar com a interface semelhante a o estilo Acadêmico do Centro de Informática.

