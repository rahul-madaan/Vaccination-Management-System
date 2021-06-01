package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.sql.Connection;
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

    public void addMemberButtonClicked(){
        String name = nameTextField.getText();
        String aadhaarNumber = aadhaarNumberTextField.getText();
        Date dateOfBirth = java.sql.Date.valueOf(dateOfBirthDatePicker.getValue());
        int gender;
        if(maleRadioButton.isSelected()){
            gender = 1;
        }else{
            gender = 0;
        }
        String query = "SELECT";
    }
}

