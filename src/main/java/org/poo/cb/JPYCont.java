package org.poo.cb;

public class JPYCont extends Cont implements ContInterfata{
    public JPYCont(String tipValuta, double suma) {
        super(tipValuta, suma);
    }


    public void verificareCreareCont(){
        System.out.println("cont JPY creat");
    }

}
