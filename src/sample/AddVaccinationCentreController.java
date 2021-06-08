package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddVaccinationCentreController implements Initializable {
    private Connection conn;

    private DbHandler dbHandler;

    @FXML
    private TextField centreNameTextField;

    @FXML
    private TextField centreAddressTextField;

    @FXML
    private TextField centreDistrictTextField;

    @FXML
    private TextField centreStateTextField;

    @FXML
    private TextField centrePinCodeTextField;

    @FXML
    private TextField centreAdminUsernameTextField;

    @FXML
    private TextField centreAdminPasswordTextField;

    @FXML
    private CheckBox tAndcCheckBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
    }

    @FXML
    public void addCentreButtonClicked(ActionEvent event) throws SQLException {
        //check all text fields match constraints.
        String centreName = centreNameTextField.getText();
        String centreAddress = centreAddressTextField.getText();
        String centreState = centreAddressTextField.getText();
        String centreDistrict = centreDistrictTextField.getText();
        String centrePinCode = centrePinCodeTextField.getText();
        String centreAdminUsername = centreAdminUsernameTextField.getText();
        String centreAdminPassword = centreAdminPasswordTextField.getText();

        //add constraints from add new member(aadhaar card ---> username)
        if(centreNameTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter Centre Name!");
            alert.show();
            return;
        }
        if(centreAddressTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter Centre Address!");
            alert.show();
            return;
        }
        if (centreStateTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter Centre State!");
            alert.show();
            return;
        }
        if(centreDistrictTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter Centre District!");
            alert.show();
            return;
        }
        if(centrePinCodeTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter Centre Pin Code!");
            alert.show();
            return;
        }
        if(centreAdminUsernameTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter centre Admin Username!");
            alert.show();
            return;
        }
        if(centreAdminPasswordTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter Centre Admin Password!");
            alert.show();
            return;
        }
        if (centrePinCodeTextField.getText().length()!=6){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter correct 16 Digit Pin Code!");
            alert.show();
            return;
        }
        if (tAndcCheckBox.isSelected()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please accept the terms!");
            alert.show();
            return;
        }
        String query = "SELECT * FROM login_admin ;";
        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(query);

        while(set.next()){
            if(centreAdminUsernameTextField.getText().equalsIgnoreCase(set.getString("userid"))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please enter a UNIQUE UserID!");
                alert.show();
                return;
            }
        }


        query = String.format("INSERT INTO vaccineCentres (Hospital_name,Address,District,State,Pin_code) Values('%s','%s','%s','%s','%s')",
                centreName,centreAddress,centreDistrict,centreState,centrePinCode);
        conn = dbHandler.getConnection();
        Statement stmt =conn.createStatement();
        stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
        set = stmt.getGeneratedKeys();
        int newCentreID = 0;
        if (set.next()) {
            newCentreID = set.getInt(1);
        }
        String newAdminUsername = centreAdminUsernameTextField.getText();
        String newAdminPassword = centreAdminPasswordTextField.getText();
        //transaction
        try{
            conn = dbHandler.getConnection();
            query = String.format("INSERT INTO login_admin (userID,password,position,centreID) VALUES('%s','%s','local',%d)",newAdminUsername,newAdminPassword,newCentreID);
            conn.createStatement().executeUpdate(query);
        }catch(Exception ex){
            System.out.println("Transaction cancelled");
            query = String.format("DELETE FROM vaccineCentres where centreID = %d;",newCentreID);
            conn.createStatement().executeUpdate(query);
            ex.printStackTrace();
        }


    }
    @FXML
    public void faqButtonClicked(ActionEvent event) throws IOException {
        faqSceneController.returnToPage = "AddVaccinationCentre.fxml";
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("faqScene.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AdminActions.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }
}
