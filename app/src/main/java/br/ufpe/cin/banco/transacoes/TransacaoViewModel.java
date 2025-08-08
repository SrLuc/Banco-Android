package br.ufpe.cin.banco.transacoes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleController;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.banco.BancoDB;
import br.ufpe.cin.banco.conta.Conta;

//Ver anotações TODO no código
public class TransacaoViewModel extends AndroidViewModel {

    private TransacaoRepository repository;
    public LiveData<List<Transacao>> transacoes;

    public TransacaoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TransacaoRepository(BancoDB.getDB(application).transacaoDAO());
        this.transacoes = repository.getTransacoes();
    }

    //Atualiza a UI apos inserir uma nova conta
    public void inserir(Transacao t) {
        new Thread(() -> repository.inserir(t)).start();
    }

    //retorna todas as transações para a UI usando o view model
    public LiveData<List<Transacao>> getTransacoes(){
        return transacoes;
    }

    //busca as transações pela data para atualizar a UI pelo view model
    public LiveData<List<Transacao>> buscarPorData(String data){
        return repository.buscarPorData(data);
    }

    public LiveData<List<Transacao>> buscarPorDataETipo(String data, char tipo) {
        return repository.buscarPorDataETipo(data, tipo);
    }

    public LiveData<List<Transacao>> buscarPorConta(String numeroConta){
        return repository.buscarPorConta(numeroConta);
    }

    public LiveData<List<Transacao>> buscarPorContaETipo(String conta, char tipo){
        return repository.buscarPorContaETipo(conta,tipo);
    }

}
