package org.poo.cb;

public class GBPCont extends Cont implements ContInterfata{
    public GBPCont(String tipValuta, double suma) {
        super(tipValuta, suma);
    }

    public void verificareCreareCont(){
        System.out.println("cont GBP creat");
    }

}
