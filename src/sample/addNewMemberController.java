package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.ResourceBundle;

public class addNewMemberController implements Initializable {
    private Connection conn;
    private DbHandler dbHandler;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField aadhaarNumberTextField;

    @FXML
    private DatePicker dateOfBirthDatePicker;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadoButton;

    @FXML
    private ToggleGroup maleFemaleToggleGroup;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
    }


    public void addMemberButtonClicked(javafx.event.ActionEvent event) throws SQLException, IOException {
        if (nameTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter Name!");
            alert.show();
            return;
        }
        if (nameTextField.getText().length()<3){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please Enter full Name!");
            alert.show();
            return;
        }
        if (aadhaarNumberTextField.getText().length()!=12){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter correct 12 Digit Aadhaar Number!");
            alert.show();
            return;
        }

        String query = "SELECT * FROM members ;";
        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(query);

        while(set.next()){
            if(aadhaarNumberTextField.getText().equalsIgnoreCase(set.getString("aadhaarNumber"))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please enter a UNIQUE 12 digit Aadhaar Number!");
                alert.show();
                return;
            }
        }

        if (dateOfBirthDatePicker.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select DOB!");
            alert.show();
            return;
        }
        if (!maleRadioButton.isSelected() && !femaleRadoButton.isSelected()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select gender!");
            alert.show();
            return;
        }

        String name = nameTextField.getText();
        String aadhaarNumber = aadhaarNumberTextField.getText();
        Date dateOfBirth = java.sql.Date.valueOf(dateOfBirthDatePicker.getValue());
        String phoneNumber = mainPageController.activeUserPhoneNumber;
        int gender;
        if(maleRadioButton.isSelected()){
            gender = 1;
        }else{
            gender = 0;
        }
        query = "INSERT INTO  members (PhoneNumber, name, AadhaarNumber, DOB, Gender)" +
                " VALUES('"+phoneNumber +"', '"+ name +"', '"+aadhaarNumber+"', '"+ dateOfBirth +"', '"+gender+"');";

        conn = dbHandler.getConnection();
        conn.createStatement().executeUpdate(query);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();

        //((Node) allMembersController.event.getSource()).getScene().getWindow().setWidth(((Node) allMembersController.event.getSource()).getScene().getWidth() + 0.001);

        Parent scene2Parent = FXMLLoader.load(getClass().getResource("allMembersScene.fxml"));
        Scene findCentreScene = new Scene(scene2Parent);
        Stage window1 = (Stage) ((Node) allMembersController.event.getSource()).getScene().getWindow();
        window1.setScene(findCentreScene);
        window1.show();
    }
}

