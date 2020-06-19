package com.example.myapplication;

import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.Models.SimplePokemon;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class PokeAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<SimplePokemon> pokemons;
    ImageLoader imageLoader  = ImageLoader.getInstance();
    ArrayList<Integer> missPoke = new ArrayList<>();

    PokeAdapter(Context context, ArrayList<SimplePokemon> pokemons) {
        ctx = context;
        this.pokemons = pokemons;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public int getCount() {
        return pokemons.size();
    }

    @Override
    public Object getItem(int position) {
        return pokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.poke_item, parent, false);
        }
        if (missPoke.contains(new Integer(position))){
            return view;
        }
        SimplePokemon p = getPokemon(position);
        while (p.getName().length() > 15){
            missPoke.add(new Integer(position));
            position += 1;
            p = getPokemon(position);
        }
        ((TextView) view.findViewById(R.id.minNameOfPokemon)).setText(p.getName().toUpperCase());
        String text =  "#" + String.valueOf(position + 1001).substring(1);
        ((TextView) view.findViewById(R.id.idTextPokemon)).setText(text);
        ImageView img = ((ImageView) view.findViewById(R.id.ivImage));
        if(!p.getImg().equals("")){
            Log.println(Log.ERROR,"IMG",p.getName() + " " + p.getImg());
            imageLoader.displayImage("assets://" +(position + 1)+".png", img);
        }
        return view;
    }

    SimplePokemon getPokemon(int position) {
        return ((SimplePokemon) getItem(position));
    }
}