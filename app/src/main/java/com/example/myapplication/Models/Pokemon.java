package com.example.myapplication.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable {
    private String name;
    private String img;
    private double height;
    private double weight;
    private ArrayList<String> categories;
    private ArrayList<String> abilities;

    public Pokemon(String name, String img, double height, double weight, ArrayList<String> categories, ArrayList<String> abilities){
        this.name = name.substring(0,1).toUpperCase() + name.substring(1);
        this.img = img;
        this.height = height;
        this.weight = weight;
        this.categories = categories;
        this.abilities = abilities;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public ArrayList<String> getAbilities() {
        return abilities;
    }
}
