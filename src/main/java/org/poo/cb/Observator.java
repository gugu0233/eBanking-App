package org.poo.cb;

public class Observator {
    Utilizator utilizator;
    public Observator(Utilizator utilizator){
        this.utilizator=utilizator;
        this.utilizator.ataseazaObservator(this);
    }
    public void actualizeaza(){
        //System.out.println("Cont adaugat");
        utilizator.adaugaContLaPortofoliu(utilizator.Stare(), utilizator.getEmail());
    }
}
