package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Running Main");
        }else{
            String numeFisier="src/main/resources/"+args[2];
            try (BufferedReader br = new BufferedReader(new FileReader(numeFisier))) {
                String linie;
                int numarLinie = 1;
                Factory f = new Factory();
                while ((linie = br.readLine()) != null) {
                    String[] cuvinte = linie.split("\\s+");
                    if (cuvinte.length >= 5 && cuvinte[0].equals("CREATE") && cuvinte[1].equals("USER")) {
                        Utilizator user = new Utilizator(cuvinte[2], cuvinte[3], cuvinte[4], cuvinte[5]+" "+cuvinte[6]+" "+cuvinte[7]+" "+cuvinte[8]+" "+cuvinte[9]+" "+cuvinte[10]);
                    } else if (cuvinte.length == 3 && cuvinte[0].equals("LIST") && cuvinte[1].equals("USER")) {
                        Singleton.Instanta().listUser(cuvinte[2]);
                    } else if (cuvinte.length == 4 && cuvinte[0].equals("ADD") && cuvinte[1].equals("FRIEND")){
                        Utilizator user1=Singleton.Instanta().getUserByEmail(cuvinte[2]);
                        Utilizator user2=Singleton.Instanta().getUserByEmail(cuvinte[3]);
                        Singleton.Instanta().addFriend(user1, user2,cuvinte[2],cuvinte[3]);
                    } else if (cuvinte.length == 4 && cuvinte[0].equals("ADD") && cuvinte[1].equals("ACCOUNT")){
                        ContInterfata cont = f.creeazaCont(cuvinte[3], 0);
                        //cont.verificareCreareCont();
                        Utilizator user=Singleton.Instanta().getUserByEmail(cuvinte[2]);
                        Observator observator=new Observator(user);
                        user.adaugaContLaPortofoliu((Cont)cont,cuvinte[2]);
                    } else if (cuvinte.length == 5 && cuvinte[0].equals("ADD") && cuvinte[1].equals("MONEY")){
                        Utilizator user=Singleton.Instanta().getUserByEmail(cuvinte[2]);
                        double suma=Double.parseDouble(cuvinte[4]);
                        user.adaugaBaniInCont(cuvinte[2], cuvinte[3], suma);
                    } else if (cuvinte.length == 3 && cuvinte[0].equals("LIST") && cuvinte[1].equals("PORTFOLIO")){
                        Singleton.Instanta().listPortofolio(cuvinte[2]);
                    }else if (cuvinte.length == 6 && cuvinte[0].equals("EXCHANGE") && cuvinte[1].equals("MONEY")){
                        Utilizator user=Singleton.Instanta().getUserByEmail(cuvinte[2]);
                        double suma=Double.parseDouble(cuvinte[5]);
                        user.exchangeMoney(cuvinte[2], cuvinte[3], cuvinte[4], suma);
                    } else if (cuvinte.length == 6 && cuvinte[0].equals("TRANSFER") && cuvinte[1].equals("MONEY")){
                        Utilizator user=Singleton.Instanta().getUserByEmail(cuvinte[2]);
                        double suma=Double.parseDouble(cuvinte[5]);
                        user.transferMoney(cuvinte[2],cuvinte[3],cuvinte[4],suma);
                    }else if (cuvinte.length == 5 && cuvinte[0].equals("BUY") && cuvinte[1].equals("STOCKS")){
                        Utilizator user=Singleton.Instanta().getUserByEmail(cuvinte[2]);
                        Cont cont = user.gasireCont("USD",cuvinte[2]);
                        int suma=Integer.parseInt(cuvinte[4]);
                        Actiuni actiune= new Actiuni(cuvinte[3], suma);
                        user.adaugaActiune(actiune,cont,suma,cuvinte[2],user.getLastValueFromFile("src/main/resources/"+args[1], actiune.getNume()),"src/main/resources/"+args[1]);
                    }else if (cuvinte.length == 2 && cuvinte[0].equals("RECOMMEND") && cuvinte[1].equals("STOCKS")){
                        SMACalculator s=new SMACalculator();
                        s.afisare("src/main/resources/"+args[1]);
                    }else if (cuvinte.length == 3 && cuvinte[0].equals("BUY") && cuvinte[1].equals("PREMIUM")){
                        Singleton.Instanta().buyPremium(cuvinte[2]);
                    }else {
                        System.out.println("Comanda invalidă sau insuficiente argumente.");
                    }
                    numarLinie++;
                }
                Singleton.Instanta().stergeUtilizatori();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*Factory f= new Factory();
        ContInterfata cont = f.creeazaCont("USD",10000);
        //ContInterfata cont1 = f.creeazaCont("USD",10000);
        Utilizator user=new Utilizator("ana", "aa","bb","hjk");
        //System.out.println(user.gasireCont(((Cont)cont).getValuta(), "ana"));
        user.adaugaContLaPortofoliu((Cont)cont,"ana");
        //System.out.println(user.gasireCont(((Cont)cont).getValuta(), "ana"));
        //user.adaugaContLaPortofoliu((Cont)cont1,"ana");

        System.out.println(user.getStatus());//false
        Singleton.Instanta().buyPremium("ana");
        Singleton.Instanta().listPortofolio("ana");
        System.out.println(user.getStatus());//true
        Utilizator user=new Utilizator("ana", "aa","bb","hjk");
        SMACalculator s=new SMACalculator();
        String file="src/main/resources/test5/stockValues.csv";
        s.afisare("src/main/resources/test5/stockValues.csv");
        System.out.println(s.getStocksToBuy(file));
        System.out.println(user.gasireActiuneRecomandata("aa"));
            

        Factory f = new Factory();
        ContInterfata cont1 = f.creeazaCont("USD", 841);
        cont1.verificareCreareCont();
        ContInterfata cont2 = f.creeazaCont("EUR", 0);
        cont2.verificareCreareCont();
        // System.out.println("Suma pentru cont1: " + ((Cont) cont1).getSuma());
        // System.out.println("Valuta pentru cont1: " + ((Cont) cont1).getValuta());
        Utilizator user = new Utilizator("ana", "aha", "ajm", "abkj");
        user.adaugaContLaPortofoliu((Cont) cont1);
        user.adaugaContLaPortofoliu((Cont) cont2);
        List<Cont> conturiUtilizator = user.getConturi();
        if (conturiUtilizator != null) {
            System.out.println("Conturile utilizatorului " + user.getEmail() + ":");
            for (Cont cont : conturiUtilizator) {
                System.out.println("Valută: " + cont.getValuta() + ", Suma: " + cont.getSuma());
            }
        }
        user.adaugaBaniInCont("ana", "USD", 999);
        conturiUtilizator = user.getConturi();
        if (conturiUtilizator != null) {
            System.out.println("Conturile utilizatorului " + user.getEmail() + ":");
            for (Cont cont : conturiUtilizator) {
                System.out.println("Valută: " + cont.getValuta() + ", Suma: " + cont.getSuma());
            }
        }
        CitireCSV o=new CitireCSV();
        //System.out.println(o.getRata("EUR", "USD"));
       user.exchangeMoney("ana", "USD", "EUR", 900);
       conturiUtilizator = user.getConturi();
       if (conturiUtilizator != null) {
           System.out.println("Conturile utilizatorului " + user.getEmail() + ":");
           for (Cont cont : conturiUtilizator) {
               System.out.println("Valută: " + cont.getValuta() + ", Suma: " + cont.getSuma());
           }
       }
       Singleton.Instanta().listPortofolio("ana");*/
    }
}