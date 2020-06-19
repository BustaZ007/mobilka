package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.Pokemon;
import com.example.myapplication.Models.SimplePokemon;
import com.example.myapplication.Services.PokemonServices;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AllPokemons extends AppCompatActivity implements View.OnClickListener {

    ArrayList<SimplePokemon> pokemons = new ArrayList<>();
    PokeAdapter pokeAdapter;
    PokemonServices services = new PokemonServices();
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pokemons);

        back = (Button)findViewById(R.id.backOfAllPoke);
        back.setOnClickListener(this);
        try {
            pokemons = services.getSimplePoke();
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        pokeAdapter = new PokeAdapter(this, pokemons);
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(pokeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.backOfAllPoke):
                this.finish();
                break;
        }

    }

    public void showInfo(View v) throws Exception {
        Log.println(Log.ERROR, "ID", String.valueOf(Integer.parseInt(((TextView)((View)v.getParent()).findViewById(R.id.idTextPokemon)).getText().toString().substring(1))));
        Intent intent = new Intent(this, PokemonActivity.class);
        int id = Integer.parseInt(((TextView)((View)v.getParent()).findViewById(R.id.idTextPokemon)).getText().toString().substring(1));
        Pokemon pk = new PokemonServices().getOnePoke(id);
        intent.putExtra("pokemon",pk);
        startActivity(intent);
    }
}
