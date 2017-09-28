package com.douglasnickson.signos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listaSignos;
    private String[] signos = {
            "Áries", "Touro", "Gêmeos", "Câncer", "Leão", "Virgem",
            "Libra", "Escorpião", "Sargitário", "Capricórnio", "Aquário","Peixes"
    };

    private String[] perfis = {
            "O ariano é uma pessoa cheia de energia e entusiasmo. Pioneiro e aventureiro, lhe encantam as metas, a liberdade e as idéias novas.",
            "Um Touro costuma ser prático, decidido e ter uma grande força de vontade. Os touro são pessoas estáveis e conservadores, e seguirão de forma leal um líder no que têm confiança. Encanta-lhes a paz e tranqüilidade e são muito respeitosos com as leis e as regras. Respeitam os valores materiais e evitam as dívidas.",
            "Gêmeos é o signo dos irmãos idênticos e, como tal, seu caráter é duplo, bastante complexo e contraditório. Por um lado é versátil, mas pelo outro pode não ser sincero.",
            "O caráter de um canceriano é o menos claro de todos os signos do zodíaco. Um canceriano pode ser de tímido e aborrecido a brilhante e famoso. Os cancerianos são conservadores e adoram a segurança e o calor do lar.",
            "Leão é o signo mais dominador do zodíaco. Também é criativo e extrovertido. São os reis entre os humanos, assim como os leões são os reis no reino animal. Têm ambição, força, valentia, independência e total segurança em suas capacidades. Não costumam ter dúvidas sobre o que fazer.",
            "Os virgens costumam ser observadores, e pacientes. Podem parecer as vezes frios, e de fato lhes custa fazer grandes amigos.",
            "Libra está entre os signos mais civilizados do zodíaco. Têm encanto, elegância, bom gosto, são amáveis e pacíficos.",
            "Escorpião é um signo intenso, com uma energia emocional única em todo o zodíaco. Ainda que possam parecer calmos, os escorpianos têm uma agressividade e magnetismo escondidos internamente.",
            "Sagitário é um dos signos mais positivos do zodíaco. São versáteis e lhes encanta a aventura e o desconhecido.",
            "Capricórnio é um dos signos do zodíaco mais estáveis, seguros e calmos. São trabalhadores, responsáveis, práticos e dispostos a persistir o quanto for necessário para conquistar seu objetivo.",
            "Os aquarianos têm uma personalidade forte e atraente. Há dois tipos de aquarianos: um é tímido, sensível, e paciente. ",
            "Os piscianos têm uma personalidade calma, paciente e amável. São sensíveis aos sentimentos dos outros e respondem com simpatia e tato ao sofrimento dos demais."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaSignos = (ListView) findViewById(R.id.lista);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                signos
        );
        listaSignos.setAdapter(adaptador);
        listaSignos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int codigoPosicao = i;
                Toast.makeText(getApplicationContext(), perfis[codigoPosicao], Toast.LENGTH_LONG).show();
            }
        });
    }
}
