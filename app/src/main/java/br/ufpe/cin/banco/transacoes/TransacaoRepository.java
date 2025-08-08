package br.ufpe.cin.banco.transacoes;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

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

    //usa o DAO para passar os dados de um transação para ROOM
    @WorkerThread
    public void inserir(Transacao t) {
        dao.adicionar(t);
    }


    //Busca a transação pelo tipo usando o objeto DAO
    public LiveData<List<Transacao>> buscarPorTipo(char tipo) {
        return dao.buscarPorTipo(tipo);
    }
    //Busca uma transção pelo numero da conta utilizando o objeto dao
    public LiveData<List<Transacao>> buscarPorConta(String numeroConta){
        return dao.buscarPorConta(numeroConta);
    }

    //busca uma transação pela data também usando o dao
    public LiveData<List<Transacao>> buscarPorData(String data){
        return dao.buscarPorData(data);
    }

    //busca uma transacao por data e por tipo
    public LiveData<List<Transacao>> buscarPorDataETipo(String data, char tipo) {
        return dao.buscarPorDataETipo(data, tipo);
    }

    //busca uma transacao por conta e por tipo
    public LiveData<List<Transacao>> buscarPorContaETipo(String conta, char tipo){
        return dao.buscarPorContaETipo(conta, tipo);
    }



}
