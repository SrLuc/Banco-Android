package br.ufpe.cin.banco;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.banco.transacoes.Transacao;
import br.ufpe.cin.banco.transacoes.TransacaoViewModel;

//Ver anotações TODO no código
public class TransferirActivity extends AppCompatActivity {

    BancoViewModel viewModel;
    TransacaoViewModel transacaoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operacoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        transacaoViewModel = new ViewModelProvider(this).get(TransacaoViewModel.class);

        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        EditText numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        valorOperacao.setHint(valorOperacao.getHint() + " transferido");
        tipoOperacao.setText("TRANSFERIR");
        btnOperacao.setText("Transferir");


        btnOperacao.setOnClickListener(
                v -> {

                    String numOrigem = numeroContaOrigem.getText().toString().trim();
                    String numDestino = numeroContaDestino.getText().toString().trim();
                    String valorTexto = valorOperacao.getText().toString().trim();

                    //Verificação para a transferencia de dinheiro de uma conta para outras
                    if (numOrigem.isEmpty() || numDestino.isEmpty() || valorTexto.isEmpty()) {
                        return;
                    }

                    double valor = Double.parseDouble(valorTexto);

                    viewModel.transferir(numOrigem, numDestino, valor);
                    String dataHoje = java.time.LocalDate.now().toString();

                    //Transação de Debito
                    transacaoViewModel.inserir(
                            new Transacao(0, 'D', numOrigem, valor, dataHoje)
                    );

                    //Transação de Crédito
                    transacaoViewModel.inserir(
                            new Transacao(0, 'C', numDestino, valor, dataHoje)
                    );

                    finish();

                }
        );

    }
}