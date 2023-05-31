package com.example.voiture;

public class Client {
    private String idc;
    private String nom;
    private String prenom;
    private int age;
    private String sexe;
    private String ville;
    public Client(){}
    public Client(String a,String b,String c,int d,String e,String f){
        idc=a;nom=b;prenom=c;age=d;sexe=e;ville=f;
    }
    public String getIdc(){
        return idc;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public String getSexe() {
        return sexe;
    }

    public String getVille() {
        return ville;
    }
}
