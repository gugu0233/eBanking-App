package org.poo.cb;

public class CADCont extends Cont implements ContInterfata{
    public CADCont(String tipValuta, double suma) {
        super(tipValuta, suma);
    }

    public void verificareCreareCont(){
        System.out.println("cont CAD creat");
    }

}
