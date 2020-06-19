package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button cancel, ok;
    Switch cachePokemon;
    SharedPreferences sPref;

    final String CACHE_POKEMON = "cache_pokemon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cancel = (Button)findViewById(R.id.cancelSettings);
        cancel.setOnClickListener(this);
        ok = (Button)findViewById(R.id.saveSettings);
        ok.setOnClickListener(this);
        cachePokemon = (Switch)findViewById(R.id.cacheAnyPokemon);
        sPref = getPreferences(MODE_PRIVATE);
        boolean cache = sPref.getBoolean(CACHE_POKEMON, false);
        cachePokemon.setChecked(cache);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.cancelSettings):
                this.finish();
                break;
            case (R.id.saveSettings):
                saveSettings();
                this.finish();
                break;
        }
    }

    private void saveSettings(){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(CACHE_POKEMON, cachePokemon.isChecked());
        ed.apply();
    }
}
