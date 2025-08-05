package br.ufpe.cin.banco;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import br.ufpe.cin.banco.conta.ContaRepository;
import br.ufpe.cin.banco.transacoes.TransacaoRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository contaRepository;
    private TransacaoRepository transacaoRepository;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.contaRepository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.transacaoRepository = new TransacaoRepository(BancoDB.getDB(application).transacaoDAO());
    }

    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        //TODO implementar transferência entre contas (lembrar de salvar no BD os objetos Conta modificados)
    }

    void creditar(String numeroConta, double valor) {
        //TODO implementar creditar em conta (lembrar de salvar no BD o objeto Conta modificado)
    }

    void debitar(String numeroConta, double valor) {
        //TODO implementar debitar em conta (lembrar de salvar no BD o objeto Conta modificado)
    }

    void buscarContasPeloNome(String nomeCliente) {
        //TODO implementar busca pelo nome do Cliente
    }

    void buscarContasPeloCPF(String cpfCliente) {
        //TODO implementar busca pelo CPF do Cliente
    }

    void buscarContaPeloNumero(String numeroConta) {
        //TODO implementar busca pelo número da Conta
    }

    void buscarTransacoesPeloNumero(String numeroConta) {
        //TODO implementar
    }

    void buscarTransacoesPeloTipo(String tipoTransacao) {
        //TODO implementar
    }

    void buscarTransacoesPelaData(String dataTransacao) {
        //TODO implementar
    }

}
