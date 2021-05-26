package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class mainPageController implements Initializable {
    private DbHandler dbHandler;
    @FXML
    private RadioButton loginRadioButton;
    @FXML private RadioButton signupRadioButton;
    @FXML private Label loginSignupLabel;
    private ToggleGroup loginSignupToggleGroup;
    @FXML private Button loginSignupButton;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label passwordLabel;
    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        loginSignupToggleGroup = new ToggleGroup();
        loginRadioButton.setToggleGroup(loginSignupToggleGroup);
        signupRadioButton.setToggleGroup(loginSignupToggleGroup);
        loginRadioButton.setSelected(true);
    }

    public void loginSignupToggle(){
        if(signupRadioButton.isSelected()){
            //what changes when signup radio button clicked
            passwordLabel.setText("Set new password");
            loginSignupLabel.setText("USER SIGN-UP");
            loginSignupButton.setText("SIGN-UP");
            passwordTextField.setPromptText("Enter new password");
        }
        else if(loginRadioButton.isSelected()){
            //what changes when login radio button is clicked
            passwordLabel.setText("Password");
            passwordTextField.setPrefHeight(40);
            passwordTextField.setPrefWidth(200);
            loginSignupLabel.setText("USER LOGIN");
            passwordTextField.setPromptText("Enter password");
            loginSignupButton.setText("LOGIN");

        }
    }
    @FXML
    public void loginSignupButtonClick() throws SQLException {
        String enteredPhoneNumber = phoneNumberTextField.getText();
        String enteredPassword = passwordTextField.getText();
        //if sign up, then check if new password meets the requirements
        //check if number entered is of 10 digit only and do not contain any letters
        String query = "SELECT * from login_users where Phonenumber ='"+ enteredPhoneNumber + "' and Password = '" + enteredPassword + "' ;";
        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(query);
        while (set.next()) {
            System.out.println(set.getString("phonenumber"));
            System.out.println(set.getString("password"));
        }
    }

}
