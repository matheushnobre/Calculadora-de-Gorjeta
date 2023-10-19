package com.appsdomatheus.calculadoradegorjeta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText pegadorPreco;
    private SeekBar porcentagemGorjeta;
    private TextView mostradorPorcentagemGorjeta;
    private TextView mostradorValorGorjeta;
    private TextView mostradorValorTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pegadorPreco = findViewById(R.id.et_pegadorPreco);
        porcentagemGorjeta = findViewById(R.id.seekBar);
        mostradorPorcentagemGorjeta = findViewById(R.id.tv_mostradorPorcentagem);

        mostradorValorGorjeta = findViewById(R.id.tv_mostradorGorjeta);
        mostradorValorTotal = findViewById(R.id.tv_mostradorTotal);

        //verificando para saber se o seekbar é clicado
        porcentagemGorjeta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //quando o progresso for alterado
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mostradorPorcentagemGorjeta.setText(progress+"%");
                if(!pegadorPreco.getText().toString().equalsIgnoreCase("")){
                    mostradorValorGorjeta.setText("R$"+formatar(calcularGorjeta()));
                    mostradorValorTotal.setText("R$"+formatar(calcularTotal()));
                } else{
                    mostradorValorGorjeta.setText("R$X.XX");
                    mostradorValorTotal.setText("R$X.XX");
                }
            }

            //quando o progresso for clicado
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(pegadorPreco.getText().toString().equalsIgnoreCase("")){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setTitle("Ops...");
                    alerta.setMessage("Preencha o valor da conta do cliente.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alerta.show();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pegadorPreco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pegadorPreco.getText().toString().equalsIgnoreCase("")){
                    mostradorValorGorjeta.setText("R$"+formatar(calcularGorjeta()));
                    mostradorValorTotal.setText("R$"+formatar(calcularTotal()));
                } else{
                    mostradorValorGorjeta.setText("R$X.XX");
                    mostradorValorTotal.setText("R$X.XX");
                }
            }
        });
    }

    private double calcularGorjeta(){
        return Double.parseDouble(pegadorPreco.getText().toString()) * porcentagemGorjeta.getProgress() / 100;
    }

    private double calcularTotal(){
        return Double.parseDouble(pegadorPreco.getText().toString()) + calcularGorjeta();
    }

    private String formatar(double valor){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(valor);
    }

}