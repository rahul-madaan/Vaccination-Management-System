package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
        femaleRadoButton.setUserData("Female");
        maleRadioButton.setUserData("Male");
    }


    public void addMemberButtonClicked(javafx.event.ActionEvent event) throws SQLException, IOException {
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
        String query = "INSERT INTO  members (PhoneNumber, name, AadhaarNumber, DOB, Gender)" +
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

