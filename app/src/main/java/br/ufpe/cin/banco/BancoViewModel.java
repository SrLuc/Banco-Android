package br.ufpe.cin.banco;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.ufpe.cin.banco.conta.Conta;
import br.ufpe.cin.banco.conta.ContaRepository;
import android.util.Log;

import br.ufpe.cin.banco.transacoes.Transacao;
import br.ufpe.cin.banco.transacoes.TransacaoRepository;

public class BancoViewModel extends AndroidViewModel {
    ContaRepository contaRepository;
    private TransacaoRepository transacaoRepository;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;
    MutableLiveData<List<Conta>> _contasFiltradas = new MutableLiveData<>();
    public LiveData<List<Conta>> contasFiltradas = _contasFiltradas;


    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.contaRepository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.transacaoRepository = new TransacaoRepository(BancoDB.getDB(application).transacaoDAO());
    }

    //Função para transferir dinheiro de uma conta para a outra
    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        new Thread(() -> {
            Conta origem = contaRepository.buscarPeloNumero(numeroContaOrigem);
            Conta destino = contaRepository.buscarPeloNumero(numeroContaDestino);

            if (origem != null && destino != null && origem.saldo >= valor) {
                origem.debitar(valor);
                destino.creditar(valor);

                contaRepository.atualizar(origem);
                contaRepository.atualizar(destino);

                String dataAtual = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // Transação de débito - origem
                Transacao tDebito = new Transacao(0, 'D', numeroContaOrigem, valor, dataAtual);
                transacaoRepository.inserir(tDebito);

                // Transação de crédito - destino
                Transacao tCredito = new Transacao(0, 'C', numeroContaDestino, valor, dataAtual);
                transacaoRepository.inserir(tCredito);

                Log.d("BancoViewModel", "Transferência registrada com transações");
            }
        }).start();
    }


    //Função para creditar dinheiro em um conta criada
    void creditar(String numeroConta, double valor) {
        new Thread(() -> {
            Conta conta = contaRepository.buscarPeloNumero(numeroConta);
            if (conta != null) {
                conta.creditar(valor);
                contaRepository.atualizar(conta);

                String dataAtual = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Transacao t = new Transacao(0, 'C', numeroConta, valor, dataAtual);
                transacaoRepository.inserir(t);

                Log.d("BancoViewModel", "Crédito registrado na transação");
            }
        }).start();
    }


    //Função para debitar dinheiro de uma conta
    void debitar(String numeroConta, double valor) {
        new Thread(() -> {
            Conta conta = contaRepository.buscarPeloNumero(numeroConta);
            if (conta != null && conta.saldo >= valor) {
                conta.debitar(valor);
                contaRepository.atualizar(conta);

                String dataAtual = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Transacao t = new Transacao(0, 'D', numeroConta, valor, dataAtual);
                transacaoRepository.inserir(t);

                Log.d("BancoViewModel", "Débito registrado na transação");
            }
        }).start();
    }


    //Função para buscar a conta pelo nome do cliente
    void buscarContasPeloNome(String nomeCliente) {
        new Thread(() -> {
            List<Conta> contas = contaRepository.buscarPeloNome(nomeCliente);
            _contasFiltradas.postValue(contas);
        }).start();
    }

    //Função para buscar a conta pelo CPF
    void buscarContasPeloCPF(String cpfCliente) {
        new Thread(() -> {
            List<Conta> contas = contaRepository.buscarPeloCPF(cpfCliente);
            _contasFiltradas.postValue(contas);
        }).start();
    }

    //Função para buscar conta pelo número da conta
    void buscarContaPeloNumero(String numeroConta) {
        //TODO implementar busca pelo número da Conta
        new Thread(() -> {
            Conta conta = contaRepository.buscarPeloNumero(numeroConta);
            _contaAtual.postValue(conta);
        }).start();
    }

    //Atualiza os dados de uma conta no banco de dados.
    public void atualizarConta(Conta conta) {
        new Thread(() -> {
            contaRepository.atualizar(conta);
        }).start();
    }


    //Função get para pegar o saldo do repository e mostrar na UI o saldo total do Banco
    public LiveData<Double> getSaldo(){
        return contaRepository.getSaldoTotal();
    }

}
