package org.poo.cb;

public class EURCont extends Cont implements ContInterfata{
    public EURCont(String tipValuta, double suma) {
        super(tipValuta, suma);
    }

    public void verificareCreareCont(){
        System.out.println("cont EUR creat");
    }

}
