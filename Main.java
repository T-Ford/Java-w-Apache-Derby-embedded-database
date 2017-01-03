/**
 * Written by Tyler J Ford for CS 455 @ RIC
 * This program uses JDBC to connect to a database called 'music' located within the 
 * project folder. A minimal menu is created to navigate through the database to do
 * a few different queries without having to type any SQL
 */

import java.util.*;

public class Main
{
    public static void main(String[] args){
        Scanner kbd = new Scanner(System.in);
        DBase db = new DBase(null, null);
        String cmd;

        if (!db.isopen()) {
            System.out.printf("Could not connect to database.%n");
            System.exit(1);
        }
        cmd = showMenu(kbd);
        while (!cmd.equals("7")) {
            if (cmd.equals("1")) db.showAll();
            if (cmd.equals("2")) db.findGPlayer(kbd);
            if (cmd.equals("3")) db.findBPlayer(kbd);
            if (cmd.equals("4")) db.showBM(kbd);
            if (cmd.equals("5")) db.updateBand(kbd);
            if(cmd.equals("6")) db.addmusician(kbd);
            cmd = showMenu(kbd);
        }
        db.close();
    }
    
    //Display the menu and read the next commands
    public static String showMenu(Scanner kbd){
        System.out.printf("%n");
        System.out.printf("1: Show All %n");
        System.out.printf("2: Find guitar player by name %n");
        System.out.printf("3: Find bass player by name %n");
        System.out.printf("4: Display a band and it's members by band name %n");
        System.out.printf("5: Update musician current band %n");
        System.out.printf("6: Insert new musician %n");
        System.out.printf("7: To exit %n");
        System.out.printf("%n");
        System.out.printf("Please enter a number to which you would like to do: ");
        return kbd.next();
    }
}
