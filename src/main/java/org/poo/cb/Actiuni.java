package org.poo.cb;

import java.util.ArrayList;
import java.util.List;

public class Actiuni {
    private String numeCompanie;
    private int suma;

    public Actiuni(String numeCompanie,int suma) {
        this.numeCompanie = numeCompanie;
        this.suma=suma;
    }

    public String getNume(){
        return numeCompanie;
    }
    public int getSuma(){
        return suma;
    }
    public String toString() {
        return "Nume: " + getNume() + " valoare: " + getSuma();
    }
}

