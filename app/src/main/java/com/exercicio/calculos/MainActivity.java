package com.exercicio.calculos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private final ArrayList<Integer> listValueInt = new ArrayList<>();
    private final ArrayList<String> listValueString = new ArrayList<>();

    private EditText editNumero;
    private Button add;
    private int qNumeros = 0, nInseridos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        editNumero = findViewById(R.id.editNumero);
        add = findViewById(R.id.add);


        add.setOnClickListener(view -> {

            if (editNumero.getText().toString().length() <= 3 && !editNumero.getText().toString().contains("-")){

                if(qNumeros == 0){

                    qNumeros = verifiNull(editNumero.getText().toString());
                    editNumero.setText("");
                    editNumero.setHint("Informe o número " + (nInseridos + 1));

                }else if (qNumeros > nInseridos){
                    addNumeroList();
                }else {
                    resultado();
                }

            }else {
                Toast.makeText(MainActivity.this, "Nao aceitamos negativos", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Somente numeros menores que 1000 ", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void addNumeroList() {

        listValueInt.add(verifiNull(editNumero.getText().toString()));
        listValueString.add("Número informado (" + (nInseridos + 1) + ") :" + editNumero.getText().toString());
        editNumero.setText("");
        startlist();
        nInseridos++;

        if (nInseridos == qNumeros){
            add.setText("resultado");
            editNumero.setHint("");
        }else {
            editNumero.setHint("Informe o número " + (nInseridos + 1));
            add.setText( String.valueOf(qNumeros - nInseridos));
        }
    }

    private int verifiNull(String editNumero){

        if (editNumero.isEmpty()){
         editNumero = "0";
        }
        return Integer.parseInt(editNumero);
    }

    private void startlist() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listValueString);

        list.setAdapter(adapter);

    }

    private void resultado() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);


        TextView sequencia = bottomSheetDialog.findViewById(R.id.sequencia_txt);
        TextView media = bottomSheetDialog.findViewById(R.id.media_txt);
        TextView soma = bottomSheetDialog.findViewById(R.id.soma_txt);
        TextView moda = bottomSheetDialog.findViewById(R.id.moda_txt);
        TextView maior = bottomSheetDialog.findViewById(R.id.maior_txt);
        TextView menor = bottomSheetDialog.findViewById(R.id.menor_txt);

        //colocata bottomSheetDialog em tela cheia
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);

        sequencia.setText(listValueInt.toString().replaceAll("\\[|\\]", "").replaceAll(", "," | "));
        media.setText(String.valueOf(calculoMedia()));
        soma.setText(String.valueOf(calculoSoma()));
        moda.setText(String.valueOf(calcularModa()));
        maior.setText(String.valueOf(Collections.max(listValueInt)));
        menor.setText(String.valueOf(Collections.min(listValueInt)));

        calcularModa();
        bottomSheetDialog.show();

    }

    private double calculoMedia() {

        double TotalSum = 0;
        int n = listValueInt.size();
        for (int i=0; i<n;i++){
            TotalSum = TotalSum+ listValueInt.get(i);
        }

        return TotalSum/n;

    }

    private int calculoSoma() {

        int TotalSum = 0;
        for (int i=0; i< listValueInt.size(); i++){
            TotalSum = TotalSum + listValueInt.get(i);
        }
        return TotalSum;

    }

    public int calcularModa() {

        Map<Integer, Integer> frequenciaNumeros = new HashMap<>();

        int maiorFrequencia = 0;
        int n = 0;

        for (int numero : listValueInt) {
            // Verificar se o número já está na lista
            Integer quantidade = frequenciaNumeros.get(numero);
            // Lista de números
            if (quantidade == null) {
                quantidade = 1;
            }  else {
                quantidade += 1;
            }
            frequenciaNumeros.put(numero, quantidade);

            if (maiorFrequencia < quantidade) {
                maiorFrequencia = quantidade;
            }
        }

        for (int numeroChave : frequenciaNumeros.keySet()) {
            int quantidade = frequenciaNumeros.get(numeroChave);
            if (maiorFrequencia == quantidade) {
                n = numeroChave;
            }
        }
        return n;

    }

}