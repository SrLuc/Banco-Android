package br.ufpe.cin.banco;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;

import br.ufpe.cin.banco.transacoes.Transacao;
import br.ufpe.cin.banco.transacoes.TransacaoViewModel;

//Ver anotações TODO no código
public class CreditarActivity extends AppCompatActivity {
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
        valorOperacao.setHint(valorOperacao.getHint() + " creditado");
        tipoOperacao.setText("CREDITAR");
        btnOperacao.setText("Creditar");

        btnOperacao.setOnClickListener(v -> {
            String numOrigem = numeroContaOrigem.getText().toString().trim();
            String valorStr = valorOperacao.getText().toString().trim();

            //Sessão de verificações para creditar dinheiro em uma conta
            if (numOrigem.isEmpty() || valorStr.isEmpty()) {
                tipoOperacao.setText("Preencha todos os campos!");
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(valorStr);
                if (valor <= 0) {
                    tipoOperacao.setText("Valor deve ser positivo!");
                    return;
                }

            } catch (NumberFormatException e) {
                tipoOperacao.setText("Valor inválido!");
                return;
            }

            // Crédito na conta
            viewModel.creditar(numOrigem, valor);

            // Criar e salvar a transação
            String dataAgora = null; // ou use um formatador
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dataAgora = LocalDate.now().toString();
            }
            Transacao t = new Transacao(0, 'C', numOrigem, valor, dataAgora);
            transacaoViewModel.inserir(t);

            finish();
        });

    }
}