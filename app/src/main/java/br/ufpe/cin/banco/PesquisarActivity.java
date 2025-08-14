package br.ufpe.cin.banco;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

import br.ufpe.cin.banco.conta.Conta;
import br.ufpe.cin.banco.conta.ContaAdapter;

public class PesquisarActivity extends AppCompatActivity {

    BancoViewModel viewModel;
    ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pesquisar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);

        // Configura RecyclerView
        adapter = new ContaAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        // Observa a lista filtrada de contas (para todas as pesquisas)
        viewModel.contasFiltradas.observe(this, contas -> {
            if (contas == null || contas.isEmpty()) {
                adapter.submitList(Collections.emptyList());
                Toast.makeText(this, "Nenhum resultado encontrado", Toast.LENGTH_SHORT).show();
            } else {
                adapter.submitList(contas);
            }
        });

        // Botão pesquisar
        btnPesquisar.setOnClickListener(v -> {
            String termo = aPesquisar.getText().toString().trim();
            if (termo.isEmpty()) {
                Toast.makeText(this, "Digite algum termo para pesquisar", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = tipoPesquisa.getCheckedRadioButtonId();
            if (selectedId == R.id.peloNomeCliente) {
                viewModel.buscarContasPeloNome(termo);
            } else if (selectedId == R.id.peloCPFcliente) {
                viewModel.buscarContasPeloCPF(termo);
            } else if (selectedId == R.id.peloNumeroConta) {
                // Busca por número da conta convertendo o resultado para lista
                new Thread(() -> {
                    Conta conta = viewModel.contaRepository.buscarPeloNumero(termo);
                    if (conta != null) {
                        viewModel._contasFiltradas.postValue(Collections.singletonList(conta));
                    } else {
                        viewModel._contasFiltradas.postValue(Collections.emptyList());
                    }
                }).start();
            }
        });
    }
}
