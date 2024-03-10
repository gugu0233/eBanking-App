package org.poo.cb;

public class USDCont extends Cont implements ContInterfata{
    public USDCont(String tipValuta, double suma) {
        super(tipValuta, suma);
    }

    public void verificareCreareCont(){
        System.out.println("cont usd creat");
    }

    
}
