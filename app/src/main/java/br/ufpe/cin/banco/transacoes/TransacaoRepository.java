package br.ufpe.cin.banco.transacoes;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

//Ver anotações TODO no código
public class TransacaoRepository {
    private TransacaoDAO dao;
    private LiveData<List<Transacao>> transacoes;

    public TransacaoRepository(TransacaoDAO dao) {
        this.dao = dao;
        this.transacoes = dao.transacoes();
    }

    public LiveData<List<Transacao>> getTransacoes() {
        return this.transacoes;
    }

    @WorkerThread
    public void inserir(Transacao t) {
        dao.adicionar(t);
    }

    public LiveData<List<Transacao>> buscarPorTipo(char tipo) {
        return dao.buscarPorTipo(tipo);
    }
    public LiveData<List<Transacao>> buscarPorConta(String numeroConta){
        return dao.buscarPorConta(numeroConta);
    }
    public LiveData<List<Transacao>> buscarPorData(String data){
        return dao.buscarPorData(data);
    }

    public LiveData<List<Transacao>> buscarPorDataETipo(String data, char tipo) {
        return dao.buscarPorDataETipo(data, tipo);
    }

    public LiveData<List<Transacao>> buscarPorContaETipo(String conta, char tipo){
        return dao.buscarPorContaETipo(conta, tipo);
    }



}
