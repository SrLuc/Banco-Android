package br.ufpe.cin.banco.conta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.banco.R;

public class AdicionarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_conta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String numeroConta = campoNumero.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    //Sessão de verificações para criar uma nova conta
                    if (nomeCliente.isEmpty() || nomeCliente.length() < 5) {
                        campoNome.setError("Nome deve ter pelo menos 5 caracteres");
                        campoNome.requestFocus();
                        return;
                    }

                    if (cpfCliente.isEmpty()) {
                        campoCPF.setError("CPF é obrigatório");
                        campoCPF.requestFocus();
                        return;
                    }

                    if (numeroConta.isEmpty()) {
                        campoNumero.setError("Número da conta é obrigatório");
                        campoNumero.requestFocus();
                        return;
                    }

                    if (saldoConta.isEmpty()) {
                        campoSaldo.setError("Saldo é obrigatório");
                        campoSaldo.requestFocus();
                        return;
                    }

                    double saldo;
                    try {
                        saldo = Double.parseDouble(saldoConta);
                    } catch (NumberFormatException e) {
                        campoSaldo.setError("Saldo inválido");
                        campoSaldo.requestFocus();
                        return;
                    }

                    //Cria o objeto nova Conta
                    Conta c = new Conta(numeroConta, saldo, nomeCliente, cpfCliente);

                    //Passe esse objeto para o ViewModel
                    viewModel.inserir(c);

                    finish();

                }
        );

    }
}