package com.example.myapplication.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.Models.Pokemon;
import com.example.myapplication.Models.SimplePokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PokemonServices extends AsyncTask<String, Void, JSONObject> {

    public ArrayList<Pokemon> getAllPoke() throws ExecutionException, InterruptedException, JSONException {
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        String allPoke = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=964";
        JSONObject js = this.execute(allPoke).get();

        JSONArray pokeJSArray = js.getJSONArray("results");
        for (int i = 0; i < pokeJSArray.length(); i++){
            String url = pokeJSArray.getJSONObject(i).getString("url");
//            pokemons.add(getOnePoke(url));
        }
        return pokemons;
    }

    public ArrayList<SimplePokemon> getSimplePoke() throws ExecutionException, InterruptedException, JSONException {
        ArrayList<SimplePokemon> pokemons = new ArrayList<>();
        String allPoke = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=964";
        JSONObject js = this.execute(allPoke).get();
        JSONArray pokeJSArray = js.getJSONArray("results");
        for (int i = 0; i < pokeJSArray.length(); i++){
            JSONObject jsonObject = pokeJSArray.getJSONObject(i);
            pokemons.add(new SimplePokemon(jsonObject.getString("name"), jsonObject.getString("url")));
        }
        return pokemons;
    }

    public Pokemon getOnePoke(int id) throws ExecutionException, InterruptedException, JSONException {
        String url = "https://pokeapi.co/api/v2/pokemon/" + id;
        JSONObject js = this.execute(url).get();
        String name = js.getString("name");
        String img = "https://pokeres.bastionbot.org/images/pokemon/" +id+".png";
        Double height = js.getDouble("height");
        Double weight = js.getDouble("weight");
        ArrayList<String> categories = new ArrayList<>();
        JSONArray catJS = js.getJSONArray("types");
        for (int i = 0; i < catJS.length(); i++){
            categories.add(catJS.getJSONObject(i).getJSONObject("type").getString("name"));
        }
        ArrayList<String> abilities = new ArrayList<>();
        JSONArray abilJS = js.getJSONArray("abilities");
        for (int i = 0; i < abilJS.length(); i++){
            Log.println(Log.ERROR, "ABILITY",abilJS.getJSONObject(i).toString());
            abilities.add(abilJS.getJSONObject(i).getJSONObject("ability").getString("name"));
        }
        return new Pokemon(name, img, height, weight, categories,abilities);
    }

    protected static String readAll(Reader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int currentChar;
        while ((currentChar = bufferedReader.read()) != -1) {
            stringBuilder.append((char) currentChar);
        }

        return stringBuilder.toString();
    }

    public static JSONObject readJsonFromUrl(String url) {
        JSONObject json = null;

        try (InputStream inputStream = new URL(url).openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String jsonText = readAll(bufferedReader);
            json = new JSONObject(jsonText);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    @Override
    protected JSONObject doInBackground(String... arg) {
        JSONObject res = readJsonFromUrl(arg[0]);
        return res;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
