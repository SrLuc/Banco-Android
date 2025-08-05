# Projeto Android

## Banco

A aplicação Banco está parcialmente implementada no momento. Existem vários métodos e trechos de códigos com anotações lembrando que faltam implementar alguns detalhes da aplicação.

1. Na classe `ContasActivity`, já há um `RecyclerView` que usa um `Adapter` (`ContaAdapter`) para mostrar uma lista de contas, mas os dados ainda não estão sendo recuperados do banco de dados. Dica: use o atributo `contas` (com tipo `LiveData<List<Conta>>`) de `ContaViewModel` para fazer isto;

2. Na classe `ContaViewHolder`, a imagem que é mostrada no `RecyclerView` com a listagem de contas não é alterada caso o saldo esteja negativo. Sempre está sendo usada a imagem padrão definida em `res/layout/linha_conta.xml`. Na função `bindTo`, inclua o código que checa o saldo da conta e atualiza a imagem para `res/drawable/delete.png` em caso de saldo negativo, e usa `res/drawable/ok.png`, caso o saldo esteja positivo. 

3. Na classe `ContaViewHolder`, ajuste o código do listener do botão de remover conta para que a funcionalidade seja implementada, usando o respectivo método no `ViewModel`;

4. Na classe `ContaViewHolder`, o `Intent` criado para enviar o usuário para a tela `EditarContaActivity` não inclui o número da conta, informação essencial para recuperar os dados da conta na tela a ser aberta. Adicione o número da conta como um `extra` do `Intent`, para que a tela de edição saiba qual conta carregar;

5. Na classe `AdicionarContaActivity`, inclua a funcionalidade de validar as informações digitadas no formulário antes de criar um objeto `Conta`.  Por exemplo, nenhum campo deve estar em branco, o saldo deve ser um número válido, assim por diante. Exiba uma mensagem de erro ao usuário se a validação falhar. Se a validação for bem-sucedida, use o método do `ContaViewModel` para armazenar o objeto no banco de dados;

6. Na classe `ContaDAO`:
    - Implemente os métodos para atualizar (`@Update`) e remover (`@Delete`) uma `Conta`.
    - Crie três novas queries (`@Query`) para buscar contas:
        * Uma que retorne um objeto `Conta` buscando pelo seu número (chave primária).
        * Uma que retorne uma `List<Conta>` buscando pelo nome do cliente.
        * Uma que retorne uma `List<Conta>` buscando pelo CPF do cliente.

7. Na classe `ContaRepository`, implemente a lógica dos métodos para atualizar e remover contas no banco de dados, além dos métodos para buscar pelo número da conta, pelo nome do cliente e pelo CPF do cliente. Estes métodos devem usar os métodos criados na classe `ContaDAO` no passo anterior. Lembre-se que estas operações devem rodar em uma thread de background, portanto use a anotação correspondente (`@WorkerThread`);

8. Na classe `ContaViewModel`, inclua métodos para atualizar e remover contas no banco de dados, além de um método para buscar pelo número da conta. Estes métodos devem usar os métodos correspondentes criados na classe `ContaRepository` no passo anterior;

9. Na classe `EditarContaActivity`, recupere o número da conta por meio do `extra` passado pelo Intent. Use o `ContaViewModel` para buscar os dados dessa conta e preencher os campos do formulário.

10. Na classe `EditarContaActivity`, assim como na tela que adiciona contas, inclua a funcionalidade de validar as informações digitadas (ex.: nenhum campo em branco, saldo é um número) antes de atualizar a `Conta` no banco de dados. Lembre de usar o método correspondente de `ContaViewModel` para armazenar o objeto atualizado no banco de dados;

11. Na classe `EditarContaActivity`, implemente a lógica do botão de remover, que usa `ContaViewModel` para remover o objeto do banco de dados;

12. Na classe `BancoViewModel`, inclua métodos para realizar as operações de transferir, creditar, e debitar, bem como métodos para buscar pelo número da conta, pelo nome do cliente e pelo CPF do cliente. Estes métodos devem usar os métodos de `ContaRepository` criados em passos anteriores;

13. Nas classes `DebitarActivity`, `CreditarActivity`, e `TransferirActivity`, implementar validação dos números das contas e do valor da operação, antes de efetuar a operação correspondente à tela. Verifique se as contas existem e se o valor é positivo. Crie um objeto `Transacao` e use o `TransacaoViewModel` para salvá-lo no banco de dados após cada operação bem-sucedida;

14. Na classe `PesquisarActivity`, implementar a lógica para realizar a busca no banco de dados de acordo com o tipo de busca escolhido pelo usuário (ver `RadioGroup` com id `tipoPesquisa`);

15. Na classe `PesquisarActivity`, ao realizar uma busca, atualizar o `RecyclerView` com os resultados na medida que encontrar algo;

16. Na classe `MainActivity`, mostrar o valor total de dinheiro armazenado no banco na tela principal. Este valor deve ser a soma de todos os saldos das contas armazenadas no banco de dados. Atenção para a possibilidade das contas terem saldo negativo;

17. Na classe `TransacaoViewHolder`, o valor da transação está sempre sendo exibido em azul. Altere o código para que o valor da transação seja exibido em vermelho, no caso de transações de débito.

18. Na classe `TransacaoDAO`, inclua métodos para buscar transações pelo (1) número da conta, (2) pela data, filtrando pelo tipo da transação (crédito, débito, ou todas);

19. Na classe `TransacaoRepository`, implemente o corpo dos métodos para buscar transações pelo (1) número da conta, (2) pela data, filtrando pelo tipo da transação (crédito, débito, ou todas). Estes métodos devem usar os métodos criados na classe `TransacaoDAO` no passo anterior;

20. Na classe `TransacaoViewModel`, inclua métodos para realizar as buscas, usando os métodos criados na classe `TransacaoRepository` no passo anterior;

21. Na classe `TransacoesActivity`, implementar o código que faz busca no banco de dados de acordo com o tipo de busca escolhido pelo usuário (ver `RadioGroup` `tipoPesquisa`) e exibe (atualiza) a lista na tela. O `RecyclerView` inicialmente deve mostrar todas as transações registradas.

## Daqui em diante - Opcional

1. Implemente a funcionalidade de gerenciamento de Clientes. Ajuste o banco de dados para refletir o relacionamento onde uma `Conta` pertence a um `Cliente`, e um `Cliente` pode ter várias contas. Ao adicionar uma conta, o usuário selecionaria um cliente já existente, não sendo possível adicionar uma conta com um cliente que não existe no Banco.

2. Faça a extração das strings de todo o aplicativo e traduza a aplicação para outra língua;

3. Fazer melhorias gerais de UI na aplicação.

## ATENÇÃO

- Entregue um arquivo `.zip` com a pasta do projeto, após executar a opção "_Clean Project_" na IDE, para diminuir o tamanho do arquivo;
- Escreva um arquivo `README.md` (ou um Google Docs) explicando quais passos do roteiro você completou (vide numeração acima) e detalhando as decisões de implementação que você tomou;
- Adicione comentários claros no código explicando a função de cada novo método que você criou;
- Se não for implementar a parte Opcional 1, não vai precisar mexer em nada que tem no pacote `br.ufpe.cin.residencia.banco.cliente`;
- Grave um vídeo curto da sua aplicação em funcionamento, demonstrando as funcionalidades que você implementou.