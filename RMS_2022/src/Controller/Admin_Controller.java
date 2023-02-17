package Controller;


import Model.Context;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin_Controller implements Initializable {

    @FXML
    private TextField BedienernameTextfield;

    @FXML
    private TextField BedienernummerTextfield;
    @FXML
    private Label messageLabel;
    @FXML
    private TableColumn<User, String> nameTblCol;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Admin_Controller> kellnerTabelle;
    @FXML
    private Button logoutButton;
    @FXML
    private Button registerButton;
    ObservableList<Admin_Controller> nameList = FXCollections.observableArrayList();
    ObservableList<Admin_Controller> items = FXCollections.observableArrayList();
    ArrayList<User> users = new ArrayList<>();
    public String getNameFromTable() {
        System.out.println("Check");
        kellnerTabelle.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        kellnerTabelle.getSelectionModel().setCellSelectionEnabled(true);
        TablePosition tablePosition = kellnerTabelle.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        User user = (User) kellnerTabelle.getItems().get(row);
        TableColumn tableColumn = tablePosition.getTableColumn();
        String data = (String) tableColumn.getCellObservableValue(user).getValue();
        System.out.println(data);


        return data;
    }

    public int getInt() {
        int selectedIndex = kellnerTabelle.getSelectionModel().getSelectedIndex();
        //   selectDrink = findDrinkWithId(TabelDate.getItems().get(selectedIndex).getInt());
        // System.out.println("Name: " + selectDrink.getName());
        //System.out.println(selectedIndex);
        //System.out.println("ID: " + selectDrink.getID());
        //System.out.println("Preis: " + selectDrink.getPreis());
        return selectedIndex;
    }
    @FXML
    void Logout(ActionEvent event) throws IOException {
       /* Stage Tstage = (Stage) logoutButton.getScene().getWindow();
        Tstage.close();
        Stage Login = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(rms.class.getResource("/View/Login.fxml"));
        //   Stage Login = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 500);
        //Stage Login = (Stage) Login_Button.getScene().getWindow();
        Login.setTitle("Login");
        Login.setScene(scene);
        Login.initModality(Modality.WINDOW_MODAL);
        Login.show();
                     */
        System.out.println(getInt());
    }


    //TODO doppelte Bedienernummer abfangen
    @FXML
    void RegisterBediener(ActionEvent event) throws SQLException {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        String newName = BedienernameTextfield.getText( );
        String newNummer = BedienernummerTextfield.getText();
        String insertFields = "INSERT INTO Mitarbeiter(Benutzername, Bedienernummer) VALUES ('";
        String insertValues = newName + "','" + newNummer + "','" + "')";
        String insertToRegister= insertFields + insertValues;

        if (!checkIfUserExists()) {
           // System.out.println(checkIfUserExists());
            //System.out.println("User = " + Context.user);
            if( Context.user == true) {
               insertToRegister = "INSERT INTO Mitarbeiter(Benutzername, Bedienernummer) VALUES('" + newName + "' ,'" + newNummer + "')";
              //  insertToRegisterQuery = "INSERT INTO Mitarbeiter(ID,Benutzername,Bedienernummer) VALUES('" + 23 + "','"  + newName + "' ,'" + newNummer + "')";

            }
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertToRegister);
               messageLabel.setText("New Account is successfully created");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       Database_Controller.closeConnection();
    }
    public boolean checkIfUserExists() {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

        String name = BedienernameTextfield.getText();

        //TODO
        // String nummer = BedienernummerTextfield.getText();

        String verifyLogin = "SELECT count(1) from Mitarbeiter WHERE Benutzername = '" + name + "' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);


            while (queryResult.next()) {

                if (queryResult.getInt(1) == 1) {
                    messageLabel.setText("Username existiert bereits");

                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        //TODO besser anpassen fliegt vlt um die Ohren
        Context.user = true;
        return false;
    }
    public void getList() {
        nameList.clear();
            for (User user : users) {
       //         nameList.add(user.getBenutzername()+ " " + user.getBedienernummer());
            }


        kellnerTabelle.setItems(items);

    }

    @FXML
    void deleteBediener(ActionEvent event) throws SQLException {
        System.out.println(getInt());
        String name = getNameFromTable();
        String query = " ";
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        int index = kellnerTabelle.getSelectionModel().getSelectedIndex();

        int databaseId = users.get(index).getId();

        query = "DELETE FROM `Mitarbeiter` WHERE Benutzername =  '" + name + "' ";
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            messageLabel.setText("New Account is successfully created");

        } catch (Exception e) {
            e.printStackTrace();
        }
      // Database_Controller.closeConnection();
    }

    @FXML
    void getBedienername(MouseEvent event) {

    }

    @FXML
    void getBedienernummer(MouseEvent event) {

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(checkIfUserExists());
        System.out.println("User = " + Context.user);
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();



        String sql = "SELECT * FROM Mitarbeiter";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet QueryOutput = statement.executeQuery(sql);
            while (QueryOutput.next()) {

                int userID = QueryOutput.getInt("ID");
                String userName = QueryOutput.getString("Benutzername");

                nameList.add(new User(userID,userName));

                nameTblCol.setCellValueFactory(new PropertyValueFactory<>("benutzername"));

                kellnerTabelle.setItems(nameList);

            }

        } catch (SQLException e) {

            Logger.getLogger(Admin_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            Database_Controller.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
