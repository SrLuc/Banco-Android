package br.ufpe.cin.banco.conta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.banco.BancoDB;
import br.ufpe.cin.banco.TransferirActivity;

//Ver métodos anotados com TODO
public class ContaViewModel extends AndroidViewModel {

    private ContaRepository repository;
    public LiveData<List<Conta>> contas;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;

    public ContaViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = repository.getContas();
    }

    //Passa os dados da conta para o repository
    public void inserir(Conta c) {
        new Thread(() -> repository.inserir(c)).start();
    }

    //Atualiza os dados da conta para o repository
    void atualizar(Conta c) {
        new Thread(() -> repository.atualizar(c)).start();
    }

    //Remove os dados da conta para o repository
    void remover(Conta c) {
        new Thread(() -> repository.remover(c)).start();
    }


    void buscarPeloNumero(String numeroConta) {
        new Thread(() -> {
            Conta conta = repository.buscarPeloNumero(numeroConta);
            _contaAtual.postValue(conta);
        }).start();
    }

    //Esses dados passados para o repository vão interagir com o DAO que é o objeto que representa os dados do ROOM



    public LiveData<Double> getSaldoTotal(){
        return repository.getSaldoTotal();
    }
}
