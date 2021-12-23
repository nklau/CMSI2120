package hw2;

public class Professor {
    
    int rarity;
    String name;
    
    
    Professor(){    
        rarity = 0;
        name = "none";
    }
    
    Professor(String n){
        if(n.equals("Edmiston")) {
            rarity = 3;
        } else if(n.equals("BJ")){
            rarity = 4;
        } else if(n.equals("Forney")) {
            rarity = 5;
        } else {
            throw new IllegalArgumentException();
        }
        
        name = n;
    }
    
    Professor(int rare, String n){
        rarity = rare;
        name = n;
    }
    
    public String getName() {
        return name;
    }
    
    public int getRarity() {
        return rarity;
    }

    public String toString() {
        return name + " " + rarity;
    }
}


