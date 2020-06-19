package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Models.SimplePokemon;

import java.util.ArrayList;

public class SimpleListAdapter extends BaseAdapter {

    ArrayList<String> abilities;
    LayoutInflater lInflater;
    Context context;
    public SimpleListAdapter(Context context, ArrayList<String> abilities){
        this.context = context;
        this.abilities = abilities;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return abilities.size();
    }

    @Override
    public String getItem(int position) {
        return abilities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.abilities_item, parent, false);
        }
        if (getCount() == 0){
            return view;
        }
        String item = getItem(position);
        ((TextView) view.findViewById(R.id.item)).setText(item);
        return view;
    }
}
