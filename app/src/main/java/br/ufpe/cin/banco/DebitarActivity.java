package br.ufpe.cin.banco;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.banco.conta.Conta;
import br.ufpe.cin.banco.transacoes.TransacaoViewModel;

//Ver anotações TODO no código
public class DebitarActivity extends AppCompatActivity {
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
        TextView labelContaDestino = findViewById(R.id.labelContaDestino);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        labelContaDestino.setVisibility(View.GONE);
        numeroContaDestino.setVisibility(View.GONE);

        valorOperacao.setHint(valorOperacao.getHint() + " debitado");
        tipoOperacao.setText("DEBITAR");
        btnOperacao.setText("Debitar");

        //Debitar dinheiro da Conta
        btnOperacao.setOnClickListener(v -> {
            String numOrigem = numeroContaOrigem.getText().toString().trim();
            String valorTexto = valorOperacao.getText().toString().trim();

            if (numOrigem.isEmpty() || valorTexto.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(valorTexto);
                if (valor <= 0) {
                    Toast.makeText(this, "Valor deve ser positivo!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verifica saldo em background
            new Thread(() -> {
                Conta conta = viewModel.contaRepository.buscarPeloNumero(numOrigem);

                if (conta == null) {
                    runOnUiThread(() -> Toast.makeText(this, "Conta não encontrada!", Toast.LENGTH_SHORT).show());
                    return;
                }

                if (valor > conta.saldo) {
                    runOnUiThread(() -> Toast.makeText(this, "Valor Maior do que o que existe na conta", Toast.LENGTH_SHORT).show());
                    return;
                }

                // Debita se estiver tudo certo
                viewModel.debitar(numOrigem, valor);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Débito realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });

    }
}