package Model;

import Controller.Database_Controller;
import Model.Drinks;
import Model.Speisen;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Context {
    public static User currentUser;
    public static boolean user = false;
    public static Drinks currentDrink;
    public static Speisen currentSpeise;
    public static User fakeuser = new User(0, "fake");
    public static Drinks fakedrink = new Drinks(0, "fake", "fake");
    public static Speisen fakespeise = new Speisen(0,"fake","fake");
    public static Drinks selectDrink = fakedrink;
    public static ArrayList<Drinks> drinksList = new ArrayList<>();



    public static void updateCurrentUser() {

        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

        String request = "SELECT * from Mitarbeiter WHERE id = " + currentUser.getId();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(request);


            while (queryResult.next()) {
                currentUser = new User(queryResult.getInt("id"), queryResult.getString("Benutzername"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    //TODO
    public static void UpdateDrink() {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

        String request = "SELECT * from Speisekarte WHERE id = " + currentDrink.getID();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(request);


            while (queryResult.next()) {
                currentDrink = new Drinks(queryResult.getInt("id"), queryResult.getString("name"), queryResult.getString("preis"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }


}