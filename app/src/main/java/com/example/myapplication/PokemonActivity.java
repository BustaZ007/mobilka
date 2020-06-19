package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Models.Pokemon;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class PokemonActivity extends AppCompatActivity implements View.OnClickListener {

    TextView pokemonName, pokemonHeight, pokemonWeight;
    LinearLayout pokemonAbilities, pokemonCategories;
    ImageView pokemonImage, back;
    ImageLoader imageLoader  = ImageLoader.getInstance();
    LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Intent intent = getIntent();
        Pokemon pokemon = (Pokemon)intent.getSerializableExtra("pokemon");
        pokemonImage = findViewById(R.id.pokemonImage);
        pokemonName = findViewById(R.id.pokemonName);
        pokemonHeight = findViewById(R.id.pokemonHeight);
        pokemonWeight = findViewById(R.id.pokemonWeight);
        pokemonAbilities = findViewById(R.id.listOfAbilities);
        pokemonCategories = findViewById(R.id.listOfCategories);
        back = findViewById(R.id.backOnAll);
        back.setOnClickListener(this);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        assert pokemon != null;
        setPokemonImage(pokemon.getImg());
        pokemonName.setText(pokemon.getName());
        pokemonHeight.setText("Height: " + pokemon.getHeight());
        pokemonWeight.setText("Weight: " + pokemon.getWeight());
        setListViewHeightBasedOnChildren(pokemonAbilities,pokemon.getAbilities());
        setListViewHeightBasedOnChildren(pokemonCategories, pokemon.getCategories());
    }

    private void setPokemonImage(String uri){
        imageLoader.displayImage(uri, pokemonImage);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
    public void setListViewHeightBasedOnChildren(LinearLayout listView, ArrayList<String> data) {
        for (int i=0; i<data.size(); i++) {
            View vi = inflater.inflate(R.layout.abilities_item, null);
            ((Button)vi.findViewById(R.id.item)).setText(data.get(i));
            listView.addView(vi);
//            ##cb7f07 - back
//            #ac678f
//            #487549
//            #00a900
//            #9a4832

        }
    }
}
