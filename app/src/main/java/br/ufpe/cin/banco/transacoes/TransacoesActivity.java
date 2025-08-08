package br.ufpe.cin.banco.transacoes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpe.cin.banco.BancoViewModel;
import br.ufpe.cin.banco.R;

//Ver anotações TODO no código
public class TransacoesActivity extends AppCompatActivity {
    BancoViewModel bancoViewModel;
    public TransacaoViewModel transacaoViewModel;
    TransacaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transacoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bancoViewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        transacaoViewModel = new ViewModelProvider(this).get(TransacaoViewModel.class);
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoTransacao = findViewById(R.id.tipoTransacao);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);
        adapter = new TransacaoAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        btnPesquisar.setOnClickListener(
                v -> {
                    String oQueFoiDigitado = aPesquisar.getText().toString().trim();
                    //Filtro de busca para buscar uma transação
                    if (oQueFoiDigitado.isEmpty()) {
                        transacaoViewModel.getTransacoes().observe(this, transacaos -> adapter.submitList(transacaos));
                        return;
                    }


                    char tipoFiltro = 'T';
                    int idRadioTipo = tipoTransacao.getCheckedRadioButtonId();
                    if (idRadioTipo == R.id.peloTipoCredito) {
                        tipoFiltro = 'C';
                    } else if (idRadioTipo == R.id.peloTipoDebito) {
                        tipoFiltro = 'D';
                    }

                    int idRadioPesquisa = tipoPesquisa.getCheckedRadioButtonId();

                    LiveData<List<Transacao>> resultados;

                    //sessão de verificações  das transações
                    if (idRadioPesquisa == R.id.pelaData) {
                        if (tipoFiltro == 'T') {
                            resultados = transacaoViewModel.buscarPorData(oQueFoiDigitado);
                        } else {
                            resultados = transacaoViewModel.buscarPorDataETipo(oQueFoiDigitado, tipoFiltro);
                        }
                    } else if (idRadioPesquisa == R.id.peloNumeroConta) {
                        if (tipoFiltro == 'T') {
                            resultados = transacaoViewModel.buscarPorConta(oQueFoiDigitado);
                        } else {
                            resultados = transacaoViewModel.buscarPorContaETipo(oQueFoiDigitado, tipoFiltro);
                        }
                    } else {
                        resultados = transacaoViewModel.getTransacoes();
                    }

                    resultados.observe(this, transacaos -> adapter.submitList(transacaos));
                }

        );

        //Atualiza o recicleview com os resultados da busca
        transacaoViewModel.transacoes.observe(this, transacoes -> {
            adapter.submitList(transacoes);
        });
    }
}