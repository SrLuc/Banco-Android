package br.ufpe.cin.banco.transacoes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;

import br.ufpe.cin.banco.R;
import br.ufpe.cin.banco.conta.Conta;
import br.ufpe.cin.banco.conta.EditarContaActivity;

//Ver anotações TODO no código
public class TransacaoViewHolder extends RecyclerView.ViewHolder {
    TextView valorTransacao = null;
    TextView numeroConta = null;
    TextView dataTransacao = null;
    Context context = null;

    public TransacaoViewHolder(@NonNull View linha) {
        super(linha);
        this.valorTransacao = linha.findViewById(R.id.valorTransacao);
        this.numeroConta = linha.findViewById(R.id.numeroContaTransacao);
        this.dataTransacao = linha.findViewById(R.id.dataTransacao);
        this.context = linha.getContext();
    }

    void bindTo(Transacao t) {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance();
        String valorFormatado = formatoMoeda.format(t.valorTransacao);

        if(t.tipoTransacao == 'D'){
            valorTransacao.setText("-" + valorFormatado);
            valorTransacao.setTextColor(context.getResources().getColor(R.color.debito));
        }
        else if (t.tipoTransacao == 'C') {
            valorTransacao.setText("+" + valorFormatado);
            valorTransacao.setTextColor(context.getResources().getColor(R.color.credito));
        }
        else {
            valorTransacao.setText(valorFormatado);
            valorTransacao.setTextColor(context.getResources().getColor(R.color.transacao_padrao));
        }

        this.numeroConta.setText(t.numeroConta);
        this.dataTransacao.setText(t.dataTransacao.toString());
    }


}
