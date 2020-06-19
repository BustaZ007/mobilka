package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    Button allCategories, allPokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView settings = (ImageView) findViewById(R.id.settingsButton);
        settings.setOnClickListener(this);
        ImageView quit = (ImageView) findViewById(R.id.quitButton);
        quit.setOnClickListener(this);

        allPokemons = (Button)findViewById(R.id.allPokemons);
        allPokemons.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent settings = new Intent(this, SettingsActivity.class);
        Intent pokemons = new Intent(this, AllPokemons.class);
        switch (v.getId()){
            case (R.id.settingsButton):
                startActivity(settings);
                break;
            case (R.id.quitButton):
                this.finish();
                break;
            case (R.id.allPokemons):
                startActivity(pokemons);
                break;
        }
    }
}
