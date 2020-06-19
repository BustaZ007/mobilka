package com.example.myapplication.Models;

public class SimplePokemon {
    private String name;
    private String img;

    public SimplePokemon(String name, String img){
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img){
        this.img = img;
    }
}
