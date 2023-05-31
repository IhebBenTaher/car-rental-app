package com.example.voiture;

public class Voiture {
    private String im;
    private String modele;
    private String color;
    private int prix;
    public Voiture(){}
    public Voiture(String a,String b,String c,int d){
        im=a;modele=b;color=c;prix=d;
    }
    public String getIm(){
        return im;
    }
    public String getModele() {
        return modele;
    }
    public String getColor() {
        return color;
    }
    public int getPrix() {
        return prix;
    }
}
