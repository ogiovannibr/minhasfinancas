package com.example.minhasfinancas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroGasto extends AppCompatActivity {
    private EditText editDescricao, editValor, editData;
    private Spinner spinnerCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_gasto);

        editDescricao = findViewById(R.id.editDescricao);
        editValor = findViewById(R.id.editValor);
        editData = findViewById(R.id.editData);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);


        String[] categorias = { "Tecnologia", "Despeas pessoais", "Investimentos", "Dívidas", "Alimentação", "Transporte", "Lazer", "Moradia", "Impostos", "Outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        spinnerCategoria.setAdapter(adapter);


        Button btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(v -> {
            try {
                String descricao = editDescricao.getText().toString();
                String valorStr = editValor.getText().toString();
                String data = editData.getText().toString();


                if (descricao.isEmpty() || valorStr.isEmpty() || data.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double valor = Double.parseDouble(valorStr);
                String categoria = spinnerCategoria.getSelectedItem().toString();

                Gasto gasto = new Gasto(descricao, valor, categoria, data);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("gasto", gasto);
                setResult(RESULT_OK, resultIntent);
                finish();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}