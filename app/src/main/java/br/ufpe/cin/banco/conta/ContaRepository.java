package br.ufpe.cin.banco.conta;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

//Ver anotações TODO no código
public class ContaRepository {
    private ContaDAO dao;
    private LiveData<List<Conta>> contas;

    public ContaRepository(ContaDAO dao) {
        this.dao = dao;
        this.contas = dao.contas();
    }

    //Retornar as contas para mostrar no recicle view
    public LiveData<List<Conta>> getContas() {
        return contas;
    }

    //insere uma conta no banco usando o Dao
    @WorkerThread
    public void inserir(Conta c) {
        dao.adicionar(c);
    }

    //atualiza uma conta no banco usando o Dao
    @WorkerThread
    public void atualizar(Conta c) {
       dao.atualizar(c);
    }

    //remoev uma conta no banco usando o Dao
    @WorkerThread
    public void remover(Conta c) {
        dao.remover(c);
    }

    //busca uma conta no banco usando o nome do cliente e também o dao
    @WorkerThread
    public List<Conta> buscarPeloNome(String nomeCliente) {
       return dao.buscarPorNome(nomeCliente);
    }

    //busca um cliente pelo CPF e também usa o dao para isso
    @WorkerThread
    public List<Conta> buscarPeloCPF(String cpfCliente) {
        return dao.buscarPorCPF(cpfCliente);
    }

    //busca o cliente pelo numero da conta
    @WorkerThread
    public Conta buscarPeloNumero(String numeroConta) {
        return dao.buscarPorNumero(numeroConta);
    }

    //utiliza o dao para pegar o saldo total do banco
    @WorkerThread
    public LiveData<Double> getSaldoTotal(){
        return dao.saldoTotal();
    }
}
