package com.example.voiture;

import java.time.LocalDate;

public class Location {
    private String id;
    private String idc;
    private String idm;
    private LocalDate dd;
    private LocalDate df;
    public Location(){}
    public Location(String a,String b,String c,LocalDate d,LocalDate e){
        id=a;idc=b;idm=c;dd=d;df=e;
    }
    public LocalDate getDd() {
        return dd;
    }
    public LocalDate getDf() {
        return df;
    }
    public String getIdm() {
        return idm;
    }
    public String getId() {
        return id;
    }
    public String getIdc() {
        return idc;
    }
}
