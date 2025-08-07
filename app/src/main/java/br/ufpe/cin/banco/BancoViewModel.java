package br.ufpe.cin.banco;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.banco.conta.Conta;
import br.ufpe.cin.banco.conta.ContaRepository;
import android.util.Log;
import br.ufpe.cin.banco.transacoes.TransacaoRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository contaRepository;
    private TransacaoRepository transacaoRepository;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;
    private MutableLiveData<List<Conta>> _contasFiltradas = new MutableLiveData<>();
    public LiveData<List<Conta>> contasFiltradas = _contasFiltradas;


    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.contaRepository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.transacaoRepository = new TransacaoRepository(BancoDB.getDB(application).transacaoDAO());
    }

    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        //TODO implementar transferência entre contas (lembrar de salvar no BD os objetos Conta modificados)
        new Thread(() -> {
            Conta origem = contaRepository.buscarPeloNumero(numeroContaOrigem);
            Conta destino = contaRepository.buscarPeloNumero(numeroContaDestino);

            if (origem != null && destino != null && origem.saldo >= valor) {
                origem.debitar(valor);
                destino.creditar(valor);

                contaRepository.atualizar(origem);
                contaRepository.atualizar(destino);

                Log.d("BancoViewModel", "Transferência feita: " + valor + " de " + numeroContaOrigem + " para " + numeroContaDestino);

            }
        }).start();
    }

    void creditar(String numeroConta, double valor) {
        //TODO implementar creditar em conta (lembrar de salvar no BD o objeto Conta modificado)
        new Thread(() -> {
            Conta conta = contaRepository.buscarPeloNumero(numeroConta);
            if (conta != null) {
                conta.creditar(valor);
                contaRepository.atualizar(conta);
                Log.d("BancoViewModel", "Crédito feito: " + valor + " na conta " + numeroConta);
            }
        }).start();
    }

    void debitar(String numeroConta, double valor) {
        //TODO implementar debitar em conta (lembrar de salvar no BD o objeto Conta modificado)
        new Thread(() -> {
            Conta conta = contaRepository.buscarPeloNumero(numeroConta);
            if (conta != null && conta.saldo >= valor) {
                conta.debitar(valor);
                contaRepository.atualizar(conta);
                Log.d("BancoViewModel", "Débito feito: " + valor + " na conta " + numeroConta);
            }
        }).start();
    }

    void buscarContasPeloNome(String nomeCliente) {
        //TODO implementar busca pelo nome do Cliente
        new Thread(() -> {
            List<Conta> contas = contaRepository.buscarPeloNome(nomeCliente);
            _contasFiltradas.postValue(contas);
        }).start();
    }

    void buscarContasPeloCPF(String cpfCliente) {
        //TODO implementar busca pelo CPF do Cliente
        new Thread(() -> {
            List<Conta> contas = contaRepository.buscarPeloCPF(cpfCliente);
            _contasFiltradas.postValue(contas);
        }).start();
    }

    void buscarContaPeloNumero(String numeroConta) {
        //TODO implementar busca pelo número da Conta
        new Thread(() -> {
            Conta conta = contaRepository.buscarPeloNumero(numeroConta);
            _contaAtual.postValue(conta);
        }).start();
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

    public LiveData<Double> getSaldo(){
        return contaRepository.getSaldoTotal();
    }

}
