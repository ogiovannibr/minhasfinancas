package com.example.minhasfinancas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listViewGastos;
    private Button btnAdicionarGasto, btnResumoCategoria;
    private List<Gasto> listaGastos = new ArrayList<>();
    private ArrayAdapter<Gasto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewGastos = findViewById(R.id.listViewGastos);
        btnAdicionarGasto = findViewById(R.id.btnAdicionarGasto);
        btnResumoCategoria = findViewById(R.id.btnResumoCategoria);


        adapter = new ArrayAdapter<Gasto>(this, android.R.layout.simple_list_item_1, listaGastos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                Gasto gasto = getItem(position);
                textView.setText(gasto.getDescricao() + " - R$" + gasto.getValor() + " (" + gasto.getCategoria() + ")");
                return view;
            }
        };
        listViewGastos.setAdapter(adapter);


        btnAdicionarGasto.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroGastoActivity.class);
            startActivityForResult(intent, 1);
        });

        btnResumoCategoria.setOnClickListener(v -> {
            if (listaGastos.isEmpty()) {
                Toast.makeText(MainActivity.this, "Nenhum gasto cadastrado!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, ResumoCategoriaActivity.class);
                intent.putExtra("listaGastos", new ArrayList<>(listaGastos));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                Gasto gasto = (Gasto) data.getSerializableExtra("gasto");
                listaGastos.add(gasto);
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Toast.makeText(this, "Erro ao adicionar gasto", Toast.LENGTH_SHORT).show();
            }
        }
    }
}