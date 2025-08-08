package br.ufpe.cin.banco.conta;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Ver anotações TODO no código
@Dao
public interface ContaDAO {

    //Adiciona uma nova conta
    @Insert(entity = Conta.class, onConflict = OnConflictStrategy.REPLACE)
    void adicionar(Conta c);

    //Atualiza uma Conta
    @Update
    void atualizar(Conta c);

    // Deleta uma conta
    @Delete
    void remover(Conta c);

    // Busca todas as contas (LiveData)
    @Query("SELECT * FROM contas ORDER BY numero ASC")
    LiveData<List<Conta>> contas();

    //Lista todas as contas
    @Query("SELECT * FROM contas ORDER BY numero ASC")
    List<Conta> todasContas();

    // Busca pelo número da conta (chave primária)
    @Query("SELECT * FROM contas WHERE numero = :numeroConta LIMIT 1")
    Conta buscarPorNumero(String numeroConta);

    //Busca por nome do cliente
    @Query("SELECT * FROM contas WHERE nomeCliente LIKE '%' || :nomeCliente || '%' ")
    List<Conta> buscarPorNome(String nomeCliente);

    //Busca o cliente pelo CPF
    @Query("SELECT * FROM contas WHERE cpfCliente LIKE '%' || :cpfCliente || '%'")
    List<Conta> buscarPorCPF(String cpfCliente);

    //Faz a cálculo de todo o saldo do banco usando a notação SUM do SQL
    @Query("SELECT SUM(saldo) FROM contas")
    LiveData<Double> saldoTotal();


}