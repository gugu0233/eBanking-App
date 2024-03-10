package org.poo.cb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Utilizator {
    private String email;
    private String prenume;
    private String nume;
    private String adresa;
    private Portofoliu portofoliu;
    private List<Utilizator> prieteni;
    private boolean status ;


    private Cont cont;
    public Observator observator;
    public Cont Stare(){
        return cont;
    }
    public void seteazaStare(Cont cont){
        this.cont=cont;
        notificaObservator();
    }
    public void notificaObservator(){
        observator.actualizeaza();
    }
    public void ataseazaObservator(Observator observator){
        this.observator=observator;
    }

    public Utilizator(String email, String prenume, String nume, String adresa) {
        this.email = email;
        this.prenume = prenume;
        this.nume = nume;
        this.adresa = adresa;
        this.portofoliu = new Portofoliu();
        this.prieteni = new ArrayList<>();
        Singleton.Instanta().adaugaUtilizator(this);
        this.status=false;
    }
    public boolean getStatus(){
        return status;
    }

    public void setStatusTrue(){
        status=true;
    }
    public String getEmail() {
        return email;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public List<Utilizator> getFriends() {
        return prieteni;
    }

    public String toString() {
        return getEmail() + getPrenume() + getNume() + getAdresa();
    }

    public void adaugaPrieten(Utilizator prieten) {
        if (!prieteni.contains(prieten))
            prieteni.add(prieten);
    }

    public boolean isFriend(Utilizator potentialFriend) {
        return prieteni.contains(potentialFriend);
    }

    public void adaugaContLaPortofoliu(Cont cont,String email) {
        Utilizator user=Singleton.Instanta().getUserByEmail(email);
        if(user.gasireCont(((Cont)cont).getValuta(),email)!=null)
            System.out.println("Account in currency " +((Cont)cont).getValuta() + " already exists for user");
        else if (portofoliu != null && portofoliu.conturi != null) {
            portofoliu.conturi.add(cont);
            //System.out.println("Cont adăugat la portofoliul utilizatorului " + getEmail());
        } else {
            System.out.println("Eroare: Portofoliul sau lista de conturi nu sunt inițializate.");
        }
    }
    public static double getLastValueFromFile(String filePath, String stockName) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[0].equals(stockName)) {
                    // Stock found, return the last value
                    return Double.parseDouble(parts[parts.length - 1]);
                }
            }
            return -1.0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1.0;
        }
    }
    public void adaugaActiune(Actiuni actiune,Cont cont,int suma, String email, double descazut,String filename) {
        Utilizator utilizator = Singleton.Instanta().getUserByEmail(email);
        if (portofoliu != null && portofoliu.actiuni != null) {
            if(cont.getSuma()<suma*descazut)
                System.out.println("Insufficient amount in account for buying stock");
            else{
                portofoliu.actiuni.add(actiune);
                for (Cont cont1 : utilizator.getConturi()) {
                    if (cont1.getValuta().equals("USD")) {
                        double sumaCurenta = cont.getSuma();
                        if(utilizator.status==false)
                            cont.setSuma(sumaCurenta -suma*descazut);
                        else if(utilizator.status==true && utilizator.gasireActiuneRecomandata(actiune.getNume(),filename)!=null)
                            cont.setSuma(sumaCurenta -(suma*descazut)/0.05);
                        return;
                    }
                }
            }
            //System.out.println("Cont adăugat la portofoliul utilizatorului " + getEmail());
        } else {
            System.out.println("Eroare: Portofoliul sau lista de act nu sunt inițializate.");
        }
    }

    public List<Cont> getConturi() {
        if (portofoliu != null && portofoliu.conturi != null) {
            return portofoliu.conturi;
        } else {
            System.out.println("Eroare: Portofoliul sau lista de conturi nu sunt inițializate.");
            return null;
        }
    }

    public Cont gasireCont(String valuta, String email) {
        for (Cont cont : portofoliu.conturi) {
            if (cont.getValuta().equals(valuta)) {
                return cont;
            }
        }
        return null;
    }
    public String gasireActiuneRecomandata(String nume,String filename) {
        SMACalculator s = new SMACalculator();
        for (String actiune : s.getStocksToBuy(filename)) {
            if (actiune.equals(nume)) {
                return actiune;
            }
        }
        return null;
    }
    public List<Actiuni> getActiuni() {
        if (portofoliu != null && portofoliu.actiuni != null) {
            return portofoliu.actiuni;
        } else {
            System.out.println("Eroare: Portofoliul sau lista de actiuni nu sunt inițializate.");
            return null; // Sau puteți returna o listă goală sau arunca o excepție, în funcție de
                         // necesități
        }
    }
    public Actiuni gasireActiune(String nume, String email) {
        for (Actiuni actiune : portofoliu.actiuni) {
            if (actiune.getNume().equals(nume)) {
                return actiune;
            }
        }
        return null;
    }
    public void adaugaBaniInCont(String emailUtilizator, String valuta, double suma) {
        Utilizator utilizator = Singleton.Instanta().getUserByEmail(emailUtilizator);

        if (utilizator != null) {
            // Găsim contul în portofoliu
            for (Cont cont : utilizator.getConturi()) {
                if (cont.getValuta().equals(valuta)) {
                    double sumaCurenta = cont.getSuma();
                    cont.setSuma(sumaCurenta + suma);
                    // System.out.println("S-au adăugat " + suma + " " + valuta + " în contul
                    // utilizatorului "+ utilizator.getEmail());
                    return;
                }
            }
            System.out.println("Eroare: Contul pentru valuta " + valuta + " nu a fost găsit.");
        } else {
            System.out.println("Eroare: Utilizatorul cu adresa de email " + emailUtilizator + " nu există.");
        }
    }

    public void exchangeMoney(String emailUtilizator, String cont1, String cont2, double suma) {
        Utilizator utilizator = Singleton.Instanta().getUserByEmail(emailUtilizator);
        CitireCSV obj = new CitireCSV();
        double rata = obj.getRata(cont2, cont1);
        if (utilizator != null) {
            if (gasireCont(cont1, emailUtilizator).getSuma() < suma * rata) {
                System.out.println("Insufficient amount in account " + cont1 + " for exchange");
            } else {
                utilizator.adaugaBaniInCont(emailUtilizator, cont2, suma);
                for (Cont cont : utilizator.getConturi()) {
                    if (cont.getValuta().equals(cont1)) {
                        double sumaCurenta = cont.getSuma();
                        if (suma * rata > 0.5 * gasireCont(cont1, emailUtilizator).getSuma()) {
                            if(utilizator.getStatus()==false)
                                cont.setSuma(sumaCurenta - (suma * rata) - (0.01 * suma * rata));
                            else
                                cont.setSuma(sumaCurenta - (suma * rata));
                            //System.out.println("noua suma dupa com este :" + cont.getSuma());
                        } else {
                            cont.setSuma(sumaCurenta - suma * rata);
                            //System.out.println("noua suma este :" + cont.getSuma());
                        }
                        return;
                    }
                }
            }
        } else {
            System.out.println("Eroare: Utilizatorul cu adresa de email " + emailUtilizator + " nu există.");
        }
    }
    public void transferMoney(String user1,String user2, String cont1,  double suma) {
        Utilizator utilizator1 = Singleton.Instanta().getUserByEmail(user1);
        Utilizator utilizator2 = Singleton.Instanta().getUserByEmail(user2);
        if (utilizator1 != null && utilizator2!=null) {
            if (!utilizator1.isFriend(utilizator2)) {
                System.out.println("You are not allowed to transfer money to " + user2);
            } else if(gasireCont(cont1, user1).getSuma() < suma){

                System.out.println("Insufficient amount in account " + cont1 + " for transfer");
            } else{
                utilizator1.adaugaBaniInCont(user2, cont1, suma);
                for (Cont cont : utilizator1.getConturi()) {
                    if (cont.getValuta().equals(cont1)) {
                        double sumaCurenta = cont.getSuma();
                            cont.setSuma(sumaCurenta - suma );
                        return;
                    }
                }
            }
        }else{
            System.out.println("eroare");
        }
    }
}
