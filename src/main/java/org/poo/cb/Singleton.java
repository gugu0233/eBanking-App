package org.poo.cb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Singleton {
    public static Singleton instanta;
    private Map<String, Utilizator> utilizatori; // lista de useri

    private Singleton() {
        utilizatori = new HashMap<>();
    }

    public static Singleton Instanta() {
        if (instanta == null)
            instanta = new Singleton();
        return instanta;
    }

    public void adaugaUtilizator(Utilizator u) {
        if (!utilizatori.containsKey(u.getEmail()))
            utilizatori.put(u.getEmail(), u);
        else
            System.out.println("user with " + u.getEmail() + " already exists");
    }

    public List<Utilizator> getAllUsers() {
        return new ArrayList<>(utilizatori.values());
    }

    public void addFriend(Utilizator user, Utilizator friend, String userEmail, String friendEmail) {
        if (user != null && friend != null) {
            if (userExists(user.getEmail()) == true && userExists(friend.getEmail()) == true) {
                if (user.isFriend(friend) == true)
                    System.out.println("User with " + friend.getEmail() + " is already a friend");
                else {
                    user.adaugaPrieten(friend);
                    friend.adaugaPrieten(user);
                }
            }
        } else if (user == null)
            System.out.println("User with " + userEmail + " doesn’t exist");
        else if (friend == null)
            System.out.println("User with " + friendEmail + " doesn’t exist");
    }

    public Utilizator getUserByEmail(String email) {
        for (Utilizator u : utilizatori.values()) {
            if (u.getEmail().equals(email))
                return u;
        }
        return null;
    }

    public void afisarePrieteni(String userEmail) {
        List<Utilizator> prieteni = getUserByEmail(userEmail).getFriends();
        System.out.println("Lista user1 prieteni:");
        for (Utilizator u : prieteni)
            System.out.println(u.getEmail());
    }

    public void listUser(String userEmail) {
        Utilizator user = getUserByEmail(userEmail);
        if (user != null) {
            System.out.print("{");
            System.out.print("\"email\":\"" + user.getEmail() + "\",");
            System.out.print("\"firstname\":\"" + user.getPrenume() + "\",");
            System.out.print("\"lastname\":\"" + user.getNume() + "\",");
            System.out.print("\"address\":\"" + user.getAdresa() + "\",");
            System.out.print("\"friends\":[");

            List<Utilizator> friends = user.getFriends();
            if (friends != null && !friends.isEmpty()) {
                for (Utilizator friend : friends) {
                    System.out.print("\"" + friend.getEmail() + "\"");
                }
            }
            System.out.print("]");
            System.out.print("}");
            System.out.println();
        } else {
            System.out.println("User with " + userEmail + " doesn't exist");
        }
    }

    public boolean userExists(String userEmail) {
        return getUserByEmail(userEmail) != null;
    }

    public void listPortofolio(String userEmail) {
        Utilizator user = getUserByEmail(userEmail);
        if (user != null) {
            System.out.print("{\"stocks\":[");
            List<Actiuni> actiuni = user.getActiuni();
            if (actiuni != null && !actiuni.isEmpty()) {
                for (Actiuni actiune : actiuni) {
                    System.out.print("{\"stockName\":\"" + actiune.getNume() + "\",\"amount\":" + actiune.getSuma() + "}");
                    if (actiuni.indexOf(actiune) < actiuni.size() - 1) {
                        System.out.print(",");
                    }
                }
            }
            System.out.print("]");
            System.out.print(",");

            System.out.print("\"accounts\":[");
            List<Cont> conturi = user.getConturi();
            if (conturi != null && !conturi.isEmpty()) {
                for (Cont cont : conturi) {
                    System.out.print("{\"currencyName\":\"" + cont.getValuta() + "\",\"amount\":\"" + String.format("%.2f", cont.getSuma()) + "\"}");
                    if (conturi.indexOf(cont) < conturi.size() - 1) {
                        System.out.print(",");
                    }
                }
            }
            System.out.print("]");
            System.out.print("}");
            System.out.println();
        } else {
            System.out.println("cont not found");
        }
    }
    public void stergeUtilizatori() {
        utilizatori.clear();
    }

    public void buyPremium(String email){
        Utilizator user = getUserByEmail(email);
        if(user==null)
            System.out.println("User with " +email + " doesn't exist");
        else if(user.gasireCont("USD", email).getSuma()<100)
            System.out.println("Insufficient amount in account for buying premium option");
        else {
            user.gasireCont("USD", email).setSuma(user.gasireCont("USD", email).getSuma()-100);
            user.setStatusTrue();
            //System.out.println("successful");
        }
    }
}
