package br.ufpe.cin.banco;

import android.os.Build;
import android.os.Bundle;
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
import br.ufpe.cin.banco.transacoes.Transacao;
import br.ufpe.cin.banco.transacoes.TransacaoViewModel;

import java.time.LocalDate;

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

        btnOperacao.setOnClickListener(v -> {
            String numOrigem = numeroContaOrigem.getText().toString().trim();
            String numDestino = numeroContaDestino.getText().toString().trim();
            String valorTexto = valorOperacao.getText().toString().trim();

            if (numOrigem.isEmpty() || numDestino.isEmpty() || valorTexto.isEmpty()) {
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

            // Busca as contas de forma síncrona via repository
            new Thread(() -> {
                Conta contaOrigem = viewModel.contaRepository.buscarPeloNumero(numOrigem);
                Conta contaDestino = viewModel.contaRepository.buscarPeloNumero(numDestino);

                if (contaOrigem == null) {
                    runOnUiThread(() -> Toast.makeText(this, "Conta de origem não encontrada!", Toast.LENGTH_SHORT).show());
                    return;
                }

                if (contaDestino == null) {
                    runOnUiThread(() -> Toast.makeText(this, "Conta de destino não encontrada!", Toast.LENGTH_SHORT).show());
                    return;
                }

                if (valor > contaOrigem.saldo) {
                    runOnUiThread(() -> Toast.makeText(this, "Saldo insuficiente!", Toast.LENGTH_SHORT).show());
                    return;
                }

                // Executa a transferência
                contaOrigem.transferir(contaDestino, valor);
                viewModel.atualizarConta(contaOrigem);
                viewModel.atualizarConta(contaDestino);

                String dataHoje = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        LocalDate.now().toString() : "";

                // Cria transações de débito e crédito
                transacaoViewModel.inserir(new Transacao(0, 'D', numOrigem, valor, dataHoje));
                transacaoViewModel.inserir(new Transacao(0, 'C', numDestino, valor, dataHoje));

                runOnUiThread(() -> {
                    Toast.makeText(this, "Transferência realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
