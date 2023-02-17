package Model;

import Controller.Admin_Controller;
import Controller.Database_Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class User extends Admin_Controller {
    private int id;
    private String benutzername;
    private int bedienernummer;
    public static User currentUser;

    public User(int id, String benutzername) {
        this.id = id;
        this.benutzername = benutzername;
    }

    public int getId() {
        return id;
    }

    public String getBenutzername() {
        return benutzername;
    }
    public int getBedienernummer() {
        return bedienernummer;
    }

}



