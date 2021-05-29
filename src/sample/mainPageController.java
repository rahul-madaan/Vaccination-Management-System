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
import java.util.regex.*;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class mainPageController implements Initializable {
    private DbHandler dbHandler;
    @FXML
    private RadioButton loginRadioButton;
    @FXML
    private RadioButton signupRadioButton;
    @FXML
    private Label loginSignupLabel;
    private ToggleGroup loginSignupToggleGroup;
    @FXML
    private Button loginSignupButton;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label passwordLabel;
    private Connection conn;
    @FXML
    private Label errorLabel;

    public static String activeUserPhoneNumber = "00";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        loginSignupToggleGroup = new ToggleGroup();
        loginRadioButton.setToggleGroup(loginSignupToggleGroup);
        signupRadioButton.setToggleGroup(loginSignupToggleGroup);
        signupRadioButton.setSelected(true);
    }


    public static boolean isValidMobileNumber(String s)
    {

        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // The number should be of 10 digits.

        // Creating a Pattern class object
        Pattern p = Pattern.compile("^\\d{10}$");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression for which
        // object of Matcher class is created
        Matcher m = p.matcher(s);

        // Returning bollean value
        return (m.matches());
    }


    private static int calculatePasswordStrength(String password){

        //total score of password
        int iPasswordScore = 0;

        if( password.length() < 8 )
            return 0;
        else if( password.length() >= 10 )
            iPasswordScore += 2;
        else
            iPasswordScore += 1;

        //if it contains one digit, add 2 to total score
        if( password.matches("(?=.*[0-9]).*") )
            iPasswordScore += 2;
        else
            System.out.println ("Try adding Digits to increase security ");

        //if it contains one lower case letter, add 2 to total score
        if( password.matches("(?=.*[a-z]).*") )
            iPasswordScore += 2;
        else
            System.out.println ("Try adding lower case letters to increase security ");

        //if it contains one upper case letter, add 2 to total score
        if( password.matches("(?=.*[A-Z]).*") )
            iPasswordScore += 2;
        else
            System.out.println ("Try adding upper case letters to increase security ");

        //if it contains one special character, add 2 to total score
        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
            iPasswordScore += 2;
        else
            System.out.println ("Try adding special characters to increase security ");

        return iPasswordScore;

    }



    public void loginSignupToggle() {
        if (signupRadioButton.isSelected()) {
            //what changes when signup radio button clicked
            //passwordLabel.setText("Set new password");
            //loginSignupLabel.setText("USER SIGN-UP");
            loginSignupButton.setText("REGISTER");
            passwordTextField.setPromptText("Enter new password");
        } else if (loginRadioButton.isSelected()) {
            //what changes when login radio button is clicked
            //passwordLabel.setText("Password");
            //passwordTextField.setPrefHeight(40);
            //passwordTextField.setPrefWidth(200);
            //loginSignupLabel.setText("USER LOGIN");
            passwordTextField.setPromptText("Enter password");
            loginSignupButton.setText("LOGIN");

        }
    }

    public void faqButtonClick(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("CFC_FAQ.fxml"));
        Scene faqScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(faqScene);
        window.show();
    }
    public void loginSignupButtonClickSuccess(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("allMembersScene.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void loginSignupButtonClick(ActionEvent event) throws SQLException, IOException {
        String enteredPhoneNumber = phoneNumberTextField.getText();
        String enteredPassword = passwordTextField.getText();

        //number_Check.Alert then return
        if (isValidMobileNumber(enteredPhoneNumber)) {


            //if sign up, then check if new password meets the requirements
            //check if number entered is of 10 digit only and do not contain any letters
            if (loginRadioButton.isSelected()) {
                String queryBoth = "SELECT * from login_users where Phonenumber ='" + enteredPhoneNumber + "' and Password = '" + enteredPassword + "' ;";
                conn = dbHandler.getConnection();
                ResultSet bothSet = conn.createStatement().executeQuery(queryBoth);

                String queryPhoneNumber = "SELECT * from login_users where Phonenumber ='" + enteredPhoneNumber + "';";
                ResultSet phoneNumberSet = conn.createStatement().executeQuery(queryPhoneNumber);
                if (phoneNumberSet.next() == false) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("You are not registered!");
                    errorLabel.setStyle(" -fx-background-color: #ff5c5c; -fx-text-fill: black");
                } else if (bothSet.next() == false) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Enter correct password!");
                    errorLabel.setStyle(" -fx-background-color: #ff5c5c; -fx-text-fill: black");
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Login Successful!");
                    errorLabel.setStyle(" -fx-background-color: green; -fx-text-fill: white");
                    mainPageController.activeUserPhoneNumber = enteredPhoneNumber;
                    loginSignupButtonClickSuccess(event);
                }
            } else if (signupRadioButton.isSelected()) {
                conn = dbHandler.getConnection();
                String queryPhoneNumber = "SELECT * from login_users where Phonenumber ='" + enteredPhoneNumber + "';";
                ResultSet phoneNumberSet = conn.createStatement().executeQuery(queryPhoneNumber);
                if (phoneNumberSet.next() == true) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("You are already registered!");
                    errorLabel.setStyle(" -fx-background-color: #ff5c5c; -fx-text-fill: black");
                } else {

                    //pass_check.Alert then return
                    if (calculatePasswordStrength(enteredPassword) > 6) {
                        String queryInsert = "INSERT INTO login_users (PhoneNumber, Password) VALUES ('" + enteredPhoneNumber + "','" + enteredPassword + "');";
                        conn = dbHandler.getConnection();
                        conn.createStatement().executeUpdate(queryInsert);
                        errorLabel.setVisible(true);
                        errorLabel.setStyle(" -fx-background-color: green; -fx-text-fill: white");
                        errorLabel.setText("Registered Successfully!");
                        mainPageController.activeUserPhoneNumber = enteredPhoneNumber;
                        loginSignupButtonClickSuccess(event);
                    } else {

                        loginRadioButton.setOnAction(event1 -> {
//
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Enter strong password");
                            alert.show();
                        });
//                    setGraphic(signupRadioButton);

                        System.out.println("Enter strong password ");
                    }

                }
            }
        }
        else
            System.out.println("Invalid Number");
    }


}
