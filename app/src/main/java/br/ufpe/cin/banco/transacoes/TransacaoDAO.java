package br.ufpe.cin.banco.transacoes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//Ver anotações TODO no código
@Dao
public interface TransacaoDAO {

    @Insert
    void adicionar(Transacao t);

    //não deve ser possível editar ou remover uma transação

    @Query("SELECT * FROM transacoes ORDER BY dataTransacao DESC")
    LiveData<List<Transacao>> transacoes();

    //TODO incluir métodos para buscar transações pelo (1) número da conta, (2) pela data, filtrando pelo tipo da transação (crédito, débito, ou todas)

    @Query("SELECT * FROM transacoes WHERE tipoTransacao = :tipo")
    LiveData<List<Transacao>> buscarPorTipo(char tipo);

    @Query("SELECT * FROM transacoes WHERE numeroConta LIKE :conta")
    LiveData<List<Transacao>> buscarPorConta(String conta);

    @Query("SELECT * FROM transacoes WHERE dataTransacao = :data")
    LiveData<List<Transacao>> buscarPorData(String data);

    @Query("SELECT * FROM transacoes WHERE dataTransacao = :data AND tipoTransacao = :tipo")
    LiveData<List<Transacao>> buscarPorDataETipo(String data, char tipo);

    @Query("SELECT * FROM transacoes WHERE numeroConta LIKE :conta AND tipoTransacao = :tipo")
    LiveData<List<Transacao>> buscarPorContaETipo(String conta, char tipo);



}
