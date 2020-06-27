package com.example.myapplication.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.myapplication.DBHelper;
import com.example.myapplication.Models.Pokemon;
import com.example.myapplication.Models.SimplePokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class PokemonServices extends AsyncTask<String, Void, JSONObject> {
    private DBHelper dbHelper;

    public PokemonServices(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<SimplePokemon> getSimplePoke() throws ExecutionException, InterruptedException, JSONException {
        ArrayList<SimplePokemon> pokemons = new ArrayList<>();
        String allPoke = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=800";
        JSONObject js = this.execute(allPoke).get();
        JSONArray pokeJSArray = js.getJSONArray("results");
        for (int i = 0; i < pokeJSArray.length(); i++){
            JSONObject jsonObject = pokeJSArray.getJSONObject(i);
            pokemons.add(new SimplePokemon(jsonObject.getString("name"), jsonObject.getString("url")));
        }
        return pokemons;
    }

    public Pokemon getOnePoke(int id, boolean cache) throws ExecutionException, InterruptedException, JSONException{
        String name,abilitiesStr, categoriesStr, url,query, localImg, remoteImg;
        double height, weight;

        ArrayList<String> categories, abilities;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        query = "SELECT * " +
                "FROM allpokemons WHERE id =?;";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id)});

        if(cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
            height = cursor.getDouble(cursor.getColumnIndex("height"));
            weight = cursor.getDouble(cursor.getColumnIndex("weight"));
            abilitiesStr = cursor.getString(cursor.getColumnIndex("abilities"));
            categoriesStr = cursor.getString(cursor.getColumnIndex("categories"));
            abilities = new ArrayList<>(Arrays.asList(abilitiesStr.split(";")));
            categories = new ArrayList<>(Arrays.asList(categoriesStr.split(";")));
            localImg = cursor.getString(cursor.getColumnIndex("image"));
            remoteImg = "https://pokeres.bastionbot.org/images/pokemon/" +id+".png";
            if(!(new File(localImg)).exists()){
                new DownloadServices().execute("https://pokeres.bastionbot.org/images/pokemon/" +id+".png",+id +".png");
            }
            Log.println(Log.ERROR, "LOAD_POKEMON_INFO","On SQLite");
        }
        else {
            cursor.close();
            url = "https://pokeapi.co/api/v2/pokemon/" + id;
            JSONObject js = this.execute(url).get();
            name = js.getString("name");
            remoteImg = "https://pokeres.bastionbot.org/images/pokemon/" +id+".png";
            localImg = "";
            height = js.getDouble("height");
            weight = js.getDouble("weight");
            categories = new ArrayList<>();
            JSONArray catJS = js.getJSONArray("types");
            for (int i = 0; i < catJS.length(); i++){
                categories.add(catJS.getJSONObject(i).getJSONObject("type").getString("name"));
            }
            abilities = new ArrayList<>();
            JSONArray abilJS = js.getJSONArray("abilities");
            for (int i = 0; i < abilJS.length(); i++){
                abilities.add(abilJS.getJSONObject(i).getJSONObject("ability").getString("name"));
            }
            Log.println(Log.ERROR, "LOAD_POKEMON_INFO","On ethernet");
            Log.println(Log.ERROR, "CACHE", String.valueOf(cache));
            if(cache){
                new DownloadServices().execute("https://pokeres.bastionbot.org/images/pokemon/" +id+".png",+id +".png");
                localImg = Environment.getExternalStorageDirectory().getPath() + "/pokemons/"+id +".png";
                savePokemon(id,name,height,weight, localImg,abilities,categories, db);
            }
        }
        return new Pokemon(name, localImg, remoteImg, height, weight, categories,abilities);
    }

    private void savePokemon(int id, String name, Double height, Double weight, String image,
                             ArrayList<String> abilities, ArrayList<String> categories, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("height", height);
        cv.put("weight", weight);
        cv.put("image", image);
        cv.put("abilities", getStringOfList(abilities));
        cv.put("categories", getStringOfList(categories));
        db.insert("allpokemons", null, cv);
        Log.println(Log.ERROR,"SAVE","OK_SAVE");
    }
    private String getStringOfList(ArrayList<String> list){
        StringBuilder sb = new StringBuilder();
        for (String str: list) {
            sb.append(str);
            sb.append(';');
        }
        sb.delete(sb.length() - 1, sb.length() - 1);
        return sb.toString();
    }

    private String readAll(Reader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int currentChar;
        while ((currentChar = bufferedReader.read()) != -1) {
            stringBuilder.append((char) currentChar);
        }

        return stringBuilder.toString();
    }

    private JSONObject readJsonFromUrl(String url) {
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
        return readJsonFromUrl(arg[0]);
    }

    @Override
    protected void onPostExecute(JSONObject json) {
    }

}
