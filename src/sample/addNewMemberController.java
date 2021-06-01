package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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

    private ToggleGroup maleFemaleToggleGroup;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        femaleRadoButton.setUserData("Female");
        maleRadioButton.setUserData("Male");
        femaleRadoButton.setToggleGroup(maleFemaleToggleGroup);
        maleRadioButton.setToggleGroup(maleFemaleToggleGroup);
    }

    public void addMemberButtonClicked() throws SQLException {
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

    }
}

