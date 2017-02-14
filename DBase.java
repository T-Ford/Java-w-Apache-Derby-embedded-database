/**
 * Created by Tyler J Ford
 * This class is where Java connects to the created database and is able to perform the function on 
 * it which create queries that do the search for you
 */

import java.sql.*;
import java.util.*;

public class DBase {
    private Connection conn;
    private boolean isopen;

    // Attempt to connect to the JavaDB workers database.
    public DBase(String uname, String pword) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection(
                "jdbc:derby:music", uname, pword);
            conn.setAutoCommit(false);
        } catch (Exception e) {conn = null;}
        isopen = (conn != null);
    }

    // Test whether the database is open.
    public boolean isopen() {return isopen;}

    // Close the database connection.
    public void close() {
        if (!isopen) return;
        try {conn.close();}
        catch (Exception e) {}
        isopen = false;
        conn = null;
    }

    //to display all musicians in the database
    public void showAll(){
        PreparedStatement stmt = null;
        ResultSet rset = null;
        ResultSetMetaData meta = null;
        String sql, label, first, last, currentband;
        int ncols, col, id;

        // Return if the database is closed.
        if (!isopen) return;

        try {
            // Create a PreparedStatement for the query.
            sql = "SELECT Musician.Id, Musician.Fname, Musician.Lname, Musician.CurrentBand "
                + "FROM Musician";
            stmt = conn.prepareStatement(sql);

            // Execute the query and obtain the result set.
            rset = stmt.executeQuery();

            // Use the metadata to print the column headings.
            meta = rset.getMetaData();
            ncols = meta.getColumnCount();
            
            System.out.printf("%n");
            System.out.printf(" Id   First Name    Last Name     CurrentBand%n");
            // Process each row in a loop.
            while (rset.next()) {
                id = rset.getInt(1);
                first = rset.getString(2);
                last = rset.getString(3);
                currentband = rset.getString(4);
                System.out.printf("%4d  %-12s  %-12s  %-12s%n",id, first, last, currentband);
            }
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());
            try {stmt.close();}
            catch (Exception err) {}
            try {conn.rollback();}
            catch (Exception err) {}
        }
    }

    //this finds a guitar player by first and last name
    public void findGPlayer(Scanner kbd){
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql, last, first, favGuitar;
        int id;

        // Return if the database is closed.
        if (!isopen) return;

        try {
            // Create a PreparedStatement for the query.
            sql = "SELECT Musician.Id, Musician.Fname, Musician.Lname, Guitarplayer.FavoriteGuitar " +
            "FROM Musician " +
            "INNER JOIN Guitarplayer ON Guitarplayer.Id = Musician.Id " +
            "WHERE Musician.Lname = ? AND Musician.Fname = ? ";
            stmt = conn.prepareStatement(sql);

            // Read the parameters from the user.
            System.out.printf("First Name? ");
            first = kbd.next();
            System.out.printf("Last Name? ");
            last = kbd.next();

            // Set the parameters in the statement.
            stmt.setString(1, last);
            stmt.setString(2, first);

            // Execute the query and obtain the result set.
            rset = stmt.executeQuery();

            // Print the matching musicians
            System.out.printf("%n");
            System.out.printf(" Id   First Name    Last Name     FavoriteGuitar%n");
            while (rset.next()) {
                id = rset.getInt(1);
                first = rset.getString(2);
                last = rset.getString(3);
                favGuitar = rset.getString(4);
                System.out.printf("%4d  %-12s  %-12s  %-12s%n", id, first, last, favGuitar);
            }
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());
            try {stmt.close();}
            catch (Exception err) {}
            try {conn.rollback();}
            catch (Exception err) {}
        }
    }

    //this finds a bass player by first and last name
    public void findBPlayer(Scanner kbd){
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql, last, first, favBass;
        int id;

        // Return if the database is closed.
        if (!isopen) return;

        try {
            // Create a PreparedStatement for the query.
            sql = "SELECT Musician.Id, Musician.Fname, Musician.Lname, BassPlayer.FavoriteBass " +
            "FROM Musician " +
            "INNER JOIN BassPlayer ON BassPlayer.Id = Musician.Id " +
            "WHERE Musician.Lname = ? AND Musician.Fname = ? ";
            stmt = conn.prepareStatement(sql);

            // Read the parameters from the user.
            System.out.printf("First Name? ");
            first = kbd.next();
            System.out.printf("Last Name? ");
            last = kbd.next();

            // Set the parameters in the statement.
            stmt.setString(1, last);
            stmt.setString(2, first);

            // Execute the query and obtain the result set.
            rset = stmt.executeQuery();

            // Print the matching musicians
            System.out.printf("%n");
            System.out.printf(" Id   First Name    Last Name     FavoriteBass%n");
            while (rset.next()) {
                id = rset.getInt(1);
                first = rset.getString(2);
                last = rset.getString(3);
                favBass = rset.getString(4);
                System.out.printf("%4d  %-12s  %-12s  %-12s%n", id, first, last, favBass);
            }

            stmt.close();
            conn.commit();
        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());
            try {stmt.close();}
            catch (Exception err) {}
            try {conn.rollback();}
            catch (Exception err) {}
        }
    }

    //displays a band and the members that are in it
    public void showBM(Scanner kbd){
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql, bName, first, last;

        // Return if the database is closed.
        if (!isopen) return;

        try {
            // Create a PreparedStatement for the query.
            sql = "SELECT Bandlist.Name, Musician.Fname, Musician.Lname " +
            "FROM BandList " + 
            "INNER JOIN Musician ON Musician.CurrentBand = Bandlist.Name " +
            "WHERE Bandlist.name = ? ";
            stmt = conn.prepareStatement(sql);

            // Read the parameters from the user.
            System.out.printf("Band Name? ");
            kbd.nextLine();
            bName = kbd.nextLine();

            // Set the parameters in the statement.
            stmt.setString(1, bName);

            // Execute the query and obtain the result set.
            rset = stmt.executeQuery();

            // Print the matching musicians
            System.out.printf("%n");
            System.out.printf("BandName      First Name    Last Name%n");
            while (rset.next()) {
                bName = rset.getString(1);
                first = rset.getString(2);
                last = rset.getString(3);
                System.out.printf("%-12s  %-12s  %-12s%n", bName, first, last);
            }

            stmt.close();
            conn.commit();
        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());
            try {stmt.close();}
            catch (Exception err) {}
            try {conn.rollback();}
            catch (Exception err) {}
        }
    }

    //this updates a specific musicians' current band declare by the user
    public void updateBand(Scanner kbd){
        PreparedStatement stmt = null;
        String sql, newBand, updateB;
        int id;

        //Return of the database is closed
        if(!isopen) return;

        try{
            //create a PreparedStatement for the update
            sql = "UPDATE Musician SET " +
            " Musician.CurrentBand = ? " +
            "WHERE  Musician.Id = ?";
            stmt = conn.prepareStatement(sql);

            //Read the parameters from the user to update a row by id number
            System.out.printf("Musician Id? ");
            id = kbd.nextInt();
            kbd.nextLine();
            System.out.printf("New band? ");
            newBand = kbd.nextLine();
            kbd.nextLine();

            //Set the parameters in the statement
            stmt.setString(1, newBand);
            stmt.setInt(2, id);

            //Execute the update
            stmt.executeUpdate();
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());
            try {stmt.close();}
            catch (Exception err) {}
            try {conn.rollback();}
            catch (Exception err) {}
        }
    }

    public void addmusician(Scanner kbd){
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql, fname, lname, cband;
        int id = -1;

        if(!isopen) return;

        try{
            //Read in musician's name and current band for creation of a new row
            System.out.print("First name? ");
            fname = kbd.next();
            System.out.print("Last name? ");
            lname = kbd.next();
            System.out.print("Current band? ");
            cband = kbd.next();

            //Reads all the id numbers and generates a new id number
            sql  = "SELECT Musician.Id FROM Musician";
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            while(rset.next()){
                id = rset.getInt(1);
            }
            id++;
            //Create a preparestatement for the update
            sql = "INSERT INTO Musician (Id, Fname, Lname, CurrentBand) "
                + "VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, fname);
            stmt.setString(3, lname);
            stmt.setString(4, cband);

            //execute the update and retrieve the generated key
            stmt.executeUpdate();
            if(rset.next()) {id = rset.getInt(1);}
            System.out.printf("%n");
            //Display the musician was added or not
            if(id >= 0){
                System.out.printf("Musician %d was created for %s %s.%n", 
                id, fname, lname);
            } else{
                System.out.printf("The musician was not added to the records.%n");
            }
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());
            try {stmt.close();}
            catch (Exception err) {}
            try {conn.rollback();}
            catch (Exception err) {}
        }
    }
}
