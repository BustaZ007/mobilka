package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.Pokemon;
import com.example.myapplication.Models.SimplePokemon;
import com.example.myapplication.Services.PokemonServices;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AllPokemons extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ArrayList<SimplePokemon> pokemons = new ArrayList<>();
    PokeAdapter pokeAdapter;
    ImageView back,reload;
    LinearLayout problem;
    ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pokemons);
        back = findViewById(R.id.backOfAllPoke);
        back.setOnClickListener(this);
        reload = findViewById(R.id.reload);
        reload.setOnClickListener(this);
        problem = findViewById(R.id.problem);
        lvMain = findViewById(R.id.lvMain);
        setPokemonsList();
    }

    private void setPokemonsList(){
        try {
            PokemonServices services = new PokemonServices();
            pokemons = services.getSimplePoke();
            pokeAdapter = new PokeAdapter(this, pokemons);
            lvMain.setAdapter(pokeAdapter);
            lvMain.setVisibility(View.VISIBLE);
            problem.setVisibility(View.GONE);
            lvMain.setOnItemClickListener(this);
        } catch (ExecutionException | InterruptedException |NullPointerException| JSONException e) {
            lvMain.setVisibility(View.GONE);
            problem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.backOfAllPoke):
                this.finish();
                break;
            case (R.id.reload):
                setPokemonsList();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PokemonActivity.class);
        Pokemon pk;
        try {
            pk = new PokemonServices().getOnePoke((int)id+ 1);
            intent.putExtra("pokemon",pk);
            startActivity(intent);
        } catch (ExecutionException | InterruptedException |NullPointerException| JSONException e) {
            lvMain.setVisibility(View.GONE);
            problem.setVisibility(View.VISIBLE);
        }
    }
}
