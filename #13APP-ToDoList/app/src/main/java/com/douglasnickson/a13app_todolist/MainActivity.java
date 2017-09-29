package com.douglasnickson.a13app_todolist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText textoTarefa;
    private Button botaoAdicionar;
    private ListView listadeTarefas;
    private SQLiteDatabase bancoDeDados;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            botaoAdicionar = (Button) findViewById(R.id.botaoAddId);
            textoTarefa = (EditText) findViewById(R.id.textoId);
            listadeTarefas = (ListView) findViewById(R.id.listViewId);

            bancoDeDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

            bancoDeDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");

            botaoAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textoDigitado = textoTarefa.getText().toString();
                    salvarTarefa(textoDigitado);
                }
            });

            listadeTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    removerTarefa(ids.get(i));
                    Toast.makeText(MainActivity.this, "Tarefa Removida com Sucesso!", Toast.LENGTH_SHORT).show();
                    recuperarTarefa();
                }
            });

            recuperarTarefa();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void salvarTarefa(String text){
        try {
            if(text.equals("")){
                Toast.makeText(MainActivity.this, "Digite uma Tarefa", Toast.LENGTH_SHORT).show();
            }else{
                bancoDeDados.execSQL("INSERT INTO tarefas (tarefa) VALUES ('" + text +"')");
                Toast.makeText(MainActivity.this, "Tarefa Salva com Sucesso!", Toast.LENGTH_SHORT).show();
                recuperarTarefa();
                textoTarefa.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void recuperarTarefa(){
        try {
            Cursor cursor = bancoDeDados.rawQuery("SELECT * FROM tarefas ORDER BY id DESC", null);

            int indexColunaId = cursor.getColumnIndex("id");
            int indexColunaTarefa = cursor.getColumnIndex("tarefa");

            itens = new ArrayList<>();
            ids = new ArrayList<>();
            adaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_2,
                    android.R.id.text2,
                    itens
                    );

            listadeTarefas.setAdapter(adaptador);

            cursor.moveToFirst();
            while(cursor != null){
                Log.i("Resultado -", "Tarefa: " + cursor.getString(indexColunaTarefa));
                itens.add(cursor.getString(indexColunaTarefa));
                ids.add(cursor.getInt(indexColunaId));

                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removerTarefa(int id){
        try {
            bancoDeDados.execSQL("DELETE FROM tarefas WHERE id = " + id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
