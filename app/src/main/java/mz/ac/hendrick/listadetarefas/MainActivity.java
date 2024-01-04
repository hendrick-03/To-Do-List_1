package mz.ac.hendrick.listadetarefas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public EditText id_tarefas;
    public RadioButton rb1, rb2,rb3;
    public Spinner spinner1;
    private String[] categorias = {"Trabalho", "Estudo", "Pessoal", "Outros"};
    public CheckBox checkBox1, checkBox2;
    public View date;
    public Button bt1, bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_tarefas = findViewById(R.id.id_tarefa);
        rb1 = findViewById(R.id.alta_rb);
        rb2 = findViewById(R.id.media_rb);
        rb3 = findViewById(R.id.baixa_rb);

        spinner1 = findViewById(R.id.categoria_id);
        checkBox1 = findViewById(R.id.andamento_checkbox);
        checkBox2 = findViewById(R.id.concluida_checkbox);


        bt1 = findViewById(R.id.gravar_btn);
        bt2 = findViewById(R.id.listar_btn);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);


        final EditText editTextDate = findViewById(R.id.date_id);
        editTextDate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMAAAA";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : Math.min(mon, 12);
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : Math.min(year, 2100);
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        // with leap years - otherwise, date e.g. 29/02/2012
                        // would be automatically corrected to 28/02/2012

                        day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editTextDate.setText(current);
                    editTextDate.setSelection(Math.min(sel, current.length()));
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obter todos os valores inseridos
                String tarefa = id_tarefas.getText().toString();
                String prioridade = getPrioridadeSelecionada();
                String categoria = spinner1.getSelectedItem().toString();
                boolean emAndamento = checkBox1.isChecked();
                boolean concluida = checkBox2.isChecked();
                String data = editTextDate.getText().toString(); // Obter a data do campo de texto de data



                // Verificar se todos os campos estão preenchidos
                if (!tarefa.isEmpty() && !prioridade.isEmpty() && !categoria.isEmpty() && !data.isEmpty()) {
                    DBHelper dbHelper = new DBHelper(MainActivity.this);
                    dbHelper.salvarTarefa(tarefa,  prioridade, categoria, emAndamento, concluida, data);

                    Toast.makeText(MainActivity.this, "Informações gravadas com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    // Se algum campo estiver vazio, exibir Toast de aviso
                    Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

    private String getPrioridadeSelecionada(){
        if(rb1.isChecked()){
            return "alta";
        }else if(rb2.isChecked()){
            return "Media";

        }else if(rb3.isChecked()){
            return "Baixa";

        }else{
            return "";
        }
    }




}