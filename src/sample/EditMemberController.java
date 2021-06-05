package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditMemberController implements Initializable {
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
        try {
            fillPersonalDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void fillPersonalDetails() throws SQLException {
        String query = "SELECT * FROM members WHERE refID = '"+ allMembersController.selectedMemberForEdit.getRefID() +"' ;";
        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(query);
        while (set.next()){
            nameTextField.setText(set.getString("name"));
            aadhaarNumberTextField.setText(set.getString("aadhaarNumber"));
            dateOfBirthDatePicker.setValue(LocalDate.parse(set.getString("DOB")));
            if(set.getBoolean("gender")==true){
                maleRadioButton.setSelected(true);
            }else if(set.getBoolean("gender")==false){
                femaleRadoButton.setSelected(true);
            }
        }
    }

    public void submitChangesButtonClick(ActionEvent event) throws IOException, SQLException {

        String newName = nameTextField.getText();
        String newAadhaarNumber = aadhaarNumberTextField.getText();
        String newDOB = dateOfBirthDatePicker.getValue().toString();

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

        int newGender=0;
        if(maleRadioButton.isSelected()){
            newGender =1;
        }else if(femaleRadoButton.isSelected()){
            newGender=0;
        }



        query = "UPDATE members SET name = '"+ newName +"' , aadhaarnumber = '"+ newAadhaarNumber +"' , DOB = '"+ newDOB +"' , gender = "+ newGender +" WHERE refID = '"+ allMembersController.selectedMemberForEdit.getRefID() +"' ;";
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
