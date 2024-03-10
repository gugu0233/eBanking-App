package org.poo.cb;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Port;

public class Portofoliu {
    public List<Cont> conturi;
    public List<Actiuni> actiuni;

    Portofoliu(){
        this.conturi=new ArrayList<>();
        this.actiuni=new ArrayList<>();
    }

    public Cont getConturi(){
        for(Cont cont: conturi)
            return cont;
        return null;
    }
    public Actiuni getActiuni(){
        for(Actiuni actiune : actiuni)
            return actiune;
        return null;
    }

    
}
