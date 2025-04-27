package com.example.minhasfinancas;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumoCategoriaActivity extends AppCompatActivity {
    private TextView txtResumo;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_categoria);

        txtResumo = findViewById(R.id.txtResumo);
        progressBar = findViewById(R.id.progressBar);


        List<Gasto> listaGastos = (List<Gasto>) getIntent().getSerializableExtra("listaGastos");


        calcularResumo(listaGastos);
    }

    private void calcularResumo(List<Gasto> listaGastos) {
        progressBar.setVisibility(View.VISIBLE);

        new Thread(() -> {
            try {

                Thread.sleep(2000);


                Map<String, Double> gastosPorCategoria = new HashMap<>();
                double gastoTotal = 0;
                String categoriaMaiorGasto = "";
                double maiorGasto = 0;

                for (Gasto gasto : listaGastos) {
                    String categoria = gasto.getCategoria();
                    double valor = gasto.getValor();


                    gastosPorCategoria.put(categoria, gastosPorCategoria.getOrDefault(categoria, 0.0) + valor);


                    gastoTotal += valor;


                    if (valor > maiorGasto) {
                        maiorGasto = valor;
                        categoriaMaiorGasto = categoria;
                    }
                }


                StringBuilder resumo = new StringBuilder();
                resumo.append("Resumo por Categoria:\n\n");
                for (Map.Entry<String, Double> entry : gastosPorCategoria.entrySet()) {
                    resumo.append(entry.getKey()).append(": R$ ").append(entry.getValue()).append("\n");
                }
                resumo.append("\nTotal do Mês: R$ ").append(gastoTotal).append("\n");
                resumo.append("Maior Gasto: ").append(categoriaMaiorGasto).append(" (R$ ").append(maiorGasto).append(")");


                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    txtResumo.setText(resumo.toString());
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}