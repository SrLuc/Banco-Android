package br.ufpe.cin.banco.conta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.banco.R;

//Ver anotações TODO no código
public class EditarContaActivity extends AppCompatActivity {

    public static final String KEY_NUMERO_CONTA = "numeroDaConta";
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
        campoNumero.setEnabled(false);

        Intent i = getIntent();
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);
        viewModel.buscarPeloNumero(numeroConta);

        viewModel.contaAtual.observe(this, conta -> {
            if (conta != null){
                campoNome.setText(conta.nomeCliente);
                campoNumero.setText(conta.numero);
                campoCPF.setText(conta.cpfCliente);
                campoSaldo.setText(String.valueOf(conta.saldo));
            }
        });

        btnAtualizar.setText("Editar");
        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    //Verificações para editar uma conta depois de clicar no botão editar apos a conta ser criada
                    if(nomeCliente.isEmpty() || nomeCliente.length() <5){
                        campoNome.setError("Nome deve ter pelo menos 5 caracteres");
                        campoNome.requestFocus();
                        return;
                    }

                    if(cpfCliente.isEmpty()){
                        campoCPF.setError("CPF é obrigatório!");
                        campoCPF.requestFocus();
                        return;
                    }

                    double saldo;
                    try {
                        saldo = Double.parseDouble(saldoConta);
                    }catch (NumberFormatException e){
                        campoSaldo.setError("Saldo INVÁLIDO");
                        campoSaldo.requestFocus();
                        return;
                    }

                    Conta contaAtualizada = new Conta(numeroConta, saldo, nomeCliente, cpfCliente);
                    viewModel.atualizar(contaAtualizada);

                    finish();



                }
        );

        //botão que tem a função de remover uma conta dentro do EditarContaActivity
        btnRemover.setOnClickListener(v -> {
            Conta contaAtual = viewModel.contaAtual.getValue();
            if (contaAtual != null) {
                viewModel.remover(contaAtual);
                Toast.makeText(this, "Conta removida com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}