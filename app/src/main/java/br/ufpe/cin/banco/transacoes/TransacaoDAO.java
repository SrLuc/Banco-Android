package br.ufpe.cin.banco.transacoes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//Ver anotações TODO no código
@Dao
public interface TransacaoDAO {

    //adiciona uma nova transação no ROOM
    @Insert
    void adicionar(Transacao t);


    //faz o GET ALL em todas as transação ordenando pela data de maneira descendente
    @Query("SELECT * FROM transacoes ORDER BY dataTransacao DESC")
    LiveData<List<Transacao>> transacoes();


    //Dentro da Activity transação, faz a busca pelo tipo de transação, se é credito ou débito
    @Query("SELECT * FROM transacoes WHERE tipoTransacao = :tipo")
    LiveData<List<Transacao>> buscarPorTipo(char tipo);


    //Dentro da Activity transação, faz a busca pelo numeor da conta
    @Query("SELECT * FROM transacoes WHERE numeroConta LIKE :conta")
    LiveData<List<Transacao>> buscarPorConta(String conta);


    //Dentro da Activity transação, faz a busca a conta pela Data
    @Query("SELECT * FROM transacoes WHERE dataTransacao = :data ORDER BY dataTransacao DESC")
    LiveData<List<Transacao>> buscarPorData(String data);


    //Dentro da Activity transação, faz a busca pela DATA e pelo Tipo
    @Query("SELECT * FROM transacoes WHERE dataTransacao = :data AND tipoTransacao = :tipo")
    LiveData<List<Transacao>> buscarPorDataETipo(String data, char tipo);

    //Dentro da Activity transação, faz a busca pela conta e pelo tipo
    @Query("SELECT * FROM transacoes WHERE numeroConta LIKE :conta AND tipoTransacao = :tipo")
    LiveData<List<Transacao>> buscarPorContaETipo(String conta, char tipo);



}
