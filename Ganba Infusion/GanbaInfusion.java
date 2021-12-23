package hw2;

import java.util.Random;


public class GanbaInfusion {
    private static final int DEFAULT_CHANCE = 50;

    ProfessorDoublyLinkedList profs;
    private int studentAs;
    
    GanbaInfusion(){
        profs = new ProfessorDoublyLinkedList();
        studentAs = 5;
    }
    
    GanbaInfusion(Professor[] prebuilt){
        profs = new ProfessorDoublyLinkedList();
        for (Professor prof : prebuilt) {
            profs.append(prof);
        }
        studentAs = 5;
    }

    
    
    /**
     * Returns a reference to a professor in the list with the name "name" and the rarity level "r". 
     * If there is none that exists, return Null
     * 
     * @param name the name to search for
     * @param r the rarity level to search for
     * 
     * @return the Professor found, or null if not found
     */
    public Professor getProf(String name, int r) {
        return profs.findProf(name, r);
    }
    
    
    
    /**
     * Returns the size of the list containing all the professors. This should be a total count of professors.
     * 
     * @return The count of professors
     */
    public int getSize() {
        return profs.size();
    }
    
    
    
    /**
     * Returns the amount of studentA, our currency.
     * 
     * @return the amount of studentAs
     */
    public int getStudentA() {
        return studentAs;
    }
    
    
    
    
    /**
     * Sells a professor and gives you money equal to the rarity. This function should check if this function is in our list, 
     * and if they are then remove the professor from our list and sell. 
     * If the professor is not in our list, do nothing. 
     * 
     * @param toSell
     * 
     * @return The money received for selling.
     */
    public int sell(Professor toSell) {
        int price = 0;
        if (toSell != null) {
            price = toSell.getRarity();
            studentAs += price;
            profs.remove(toSell);
        }
        return price;
    }

    
    
    
    /** Returns our Professor list. 
     *     Items printed out should be formatted:
     *  Name, Rarity; Name, Rarity; with commas separating the Name from the Rarity, and semicolons separating different entities
     * 
     * @return a String containing the names and rarities of all Professors in the ProfLinkedList
     */
    public String toString() {
        String profList = "";
        for (int i = 0; i < profs.size(); i++) {
            Professor prof = profs.getAt(i);
            profList += prof.getName() + " " + prof.getRarity();
        }
        return profList;
    }
    
    
    
    
    /** Adds a new professor to our list. Costs 5 studentA. The rarity breakdown is as follows:
     * 
     * 60% to pull a rarity 3 Edmiston
     * 30% to pull a rarity 4 BJ
     * 10% to pull a rarity 5 Forney
     * 
     * @return a reference to the Professor that was pulled and added to the list. Returns null if player does not have enough studentAs.
     */
    public Professor pull() {
        if (studentAs < 5) {
            System.out.println("You do not have enough student A's!");
            return null;
        }
        // A string to hold the name of whom got pulled
        String whoGotPulled = "";
        
        // Generate a random number 1-100
        Random rand = new Random();
        int val = rand.nextInt(100); // Generates random value 1 in 100
        
        // Determines whom got pulled
        if (val <= 60) {
            whoGotPulled = "Edmiston";    
            
        } else if (val <= 90) { // This is between 60-90
            whoGotPulled = "BJ";
        } else {
            whoGotPulled = "Forney";
        }
            
        Professor prof = new Professor(whoGotPulled);
        studentAs -= 5;
        profs.append(prof);
        return prof;
    }
    
    /** If both professors are of the same type, fuse them to "increase rarity". If they are different types, do nothing.
     * 
     *  The base professor's rarity increases by 1 stage if successful.
     * 
     * @param base The professor that we are "ranking up".
     * @param fodder The professor instance we are deleting rank another card up.
     * 
     * @return The base professor after changes are made.
     */
    public Professor fusion(Professor base, Professor fodder) {
        if (base.toString().equals(fodder.toString())) {
            int fodderIndex = profs.indexOf(fodder.getName(), fodder.getRarity());
            if (fodderIndex == -1) {
                System.out.println("You do not have that Professor!");
                return base;
            }
            profs.remove(fodder);
            int baseIndex = profs.indexOf(base.getName(), base.getRarity());
            if (baseIndex == -1) {
                System.out.println("You do not have that Professor!");
                return base;
            }
            base.rarity = base.rarity + 1;
        }
        return base;
    }
    
    /** Uses all professors in the Professor list to help students study. Each student that you successfully help awards 1 studentA.
     *  
     *  When this method is called, you will aid students in ascending rarity order, until you can no longer help a student.
     *  The students start at rarity 1, and after each successful aid, the rarity of the next student increases by one from the previous.
     *  
     *  The default chance to successfully help a student is 50%.
     *  For each rarity higher a professor is than the student, add 10% to the help chance. For example, a rarity 4 professor helping a rarity 2 student would have 70% chance (50% base + 20% for difference in rarity) to help the student earn an A.
     *  For each rarity lower a professor is than the student, subtract 10% from the help chance. For example, a rarity 3 professor helping a rarity 4 student would have a 40% chance (50% base -10% for difference in rarity) to help the student earn an A.
     *  
     *  Once an instance of a Professor, that particular instance can no longer help students this try. However, the rest of the professors can still assist.
     *  After the "study session" is over, all Professors get a break and are ready to try again the next time aidStudy is called.
     *  
     * @return Returns the highest rarity student that was successfully helped.
     */
    public int aidStudy() {
        Random rand = new Random();
        int highest = 0;
		int num;
        for (highest = 0; highest < profs.size(); highest++) {
            num = rand.nextInt(100); // Generates a random number between 1-100
            Professor prof = profs.getAt(highest);
            int extraRarity = prof.getRarity() - highest;
            int chance = DEFAULT_CHANCE + (extraRarity * 10);
            if (num <= chance) {
                studentAs++;
            } else {
                System.out.println("Study session over!");
                return highest;
            }
        }
        System.out.println("Out of Professors!");
        return highest;
    }
    
    
}
