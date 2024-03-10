package org.poo.cb;

public class Factory {
    public ContInterfata creeazaCont(String tipValuta, double suma) {
        switch (tipValuta) {
            case "USD":
                return new USDCont(tipValuta, suma);
            case "EUR":
                return new EURCont(tipValuta, suma);
            case "GBP":
                return new GBPCont(tipValuta, suma);
            case "JPY":
                return new JPYCont(tipValuta, suma);
            case "CAD":
                return new CADCont(tipValuta, suma);
            default:
                System.out.println("Nu ați ales o valută din lista.");
        }
        return null;
    }

    
}
