package br.ufpe.cin.banco.conta;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import br.ufpe.cin.banco.R;

//Ver anotações TODO no código
public class ContaViewHolder  extends RecyclerView.ViewHolder {
    TextView nomeCliente = null;
    TextView infoConta = null;
    ImageView icone = null;
    ImageView btnEdit = null;
    ImageView btnDelete = null;
    Context context = null;

    public ContaViewHolder(@NonNull View linha) {
        super(linha);
        this.nomeCliente = linha.findViewById(R.id.nomeCliente);
        this.infoConta = linha.findViewById(R.id.infoConta);
        this.icone = linha.findViewById(R.id.icone);
        this.btnEdit = linha.findViewById(R.id.btn_edit);
        this.btnDelete = linha.findViewById(R.id.btn_delete);
        this.context = linha.getContext();
    }

    void bindTo(Conta c) {
        this.nomeCliente.setText(c.nomeCliente);
        this.infoConta.setText(c.numero + " | " + "Saldo atual: R$" + NumberFormat.getCurrencyInstance().format(c.saldo));

        //Atualiza a imagem de acordo com o valor do saldo
        if(c.saldo < 0){
            this.icone.setImageResource(R.drawable.delete);
        }else{
            this.icone.setImageResource(R.drawable.ok);
        }

        this.btnEdit.setOnClickListener(
                v -> {
                    Toast.makeText(
                            this.context,
                            "clicou no botão de editar conta",
                            Toast.LENGTH_SHORT
                    ).show();
                    Intent i = new Intent(
                            this.context,
                            EditarContaActivity.class
                    );
                    //Passa número da conta pelo Intent usando o Put Extra
                    i.putExtra(EditarContaActivity.KEY_NUMERO_CONTA,c.numero);
                    this.context.startActivity(i);
                }
        );
        this.btnDelete.setOnClickListener(
                v -> {
                    Toast.makeText(
                            this.context,
                            "clicou no botão de deletar conta",
                            Toast.LENGTH_SHORT
                    ).show();
                    //Remova a conta ao clicar
                    if (this.context instanceof ContasActivity) {
                        ContasActivity activity = (ContasActivity) this.context;
                        activity.removerConta(c);
                    } else {
                        Toast.makeText(this.context, "Erro: contexto inválido para remoção", Toast.LENGTH_SHORT).show();
                    }

                }
        );
    }

}
