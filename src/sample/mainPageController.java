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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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

    public static String activeUserPhoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        loginSignupToggleGroup = new ToggleGroup();
        loginRadioButton.setToggleGroup(loginSignupToggleGroup);
        signupRadioButton.setToggleGroup(loginSignupToggleGroup);
        signupRadioButton.setSelected(true);
        try {
            insertColumn4DaysAhead();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("faqScene.fxml"));
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
    public void adminLoginButtonClick(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void loginSignupButtonClick(ActionEvent event) throws SQLException, IOException, InterruptedException {
        String enteredPhoneNumber = phoneNumberTextField.getText();
        String enteredPassword = passwordTextField.getText();

        //number_Check.Alert then return



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


                String queryInsert = "INSERT INTO login_users (PhoneNumber, Password) VALUES ('" + enteredPhoneNumber + "','" + enteredPassword + "');";
                conn = dbHandler.getConnection();
                conn.createStatement().executeUpdate(queryInsert);
                errorLabel.setVisible(true);
                errorLabel.setStyle(" -fx-background-color: green; -fx-text-fill: white");
                errorLabel.setText("Registered Successfully!");
            }
        }
    }

    public void insertColumn4DaysAhead() throws SQLException {
        String query = "DESCRIBE slots";
        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(query);

        java.util.Date date=new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        String date1 = c.getTime().toString();
        String date1temp = date1.substring(4,7);
        date1 = date1.substring(8,10);
        date1 = date1temp + date1;
        String date1S1 = date1 + "s1";
        String date1S2 = date1 + "s2";
        String date1S3 = date1 + "s3";
        String date1S4 = date1 + "s4";


        c.add(Calendar.DATE, 1);
        String date2 = c.getTime().toString();
        String date2temp = date2.substring(4,7);
        date2 = date2.substring(8,10);
        date2 = date2temp + date2;
        String date2S1 = date2 + "s1";
        String date2S2 = date2 + "s2";
        String date2S3 = date2 + "s3";
        String date2S4 = date2 + "s4";

        c.add(Calendar.DATE, 1);
        String date3 = c.getTime().toString();
        String date3temp = date3.substring(4,7);
        date3 = date3.substring(8,10);
        date3 = date3temp + date3;
        String date3S1 = date3 + "s1";
        String date3S2 = date3 + "s2";
        String date3S3 = date3 + "s3";
        String date3S4 = date3 + "s4";

        c.add(Calendar.DATE, 1);
        String date4 = c.getTime().toString();
        String date4temp = date4.substring(4,7);
        date4 = date4.substring(8,10);
        date4 = date4temp + date4;
        String date4S1 = date4 + "s1";
        String date4S2 = date4 + "s2";
        String date4S3 = date4 + "s3";
        String date4S4 = date4 + "s4";

        boolean day1 = false;
        boolean day2 = false;
        boolean day3 = false;
        boolean day4 = false;

        while(set.next()){
            if(set.getString("Field").equalsIgnoreCase(date1))
                day1=true;
            if(set.getString("Field").equalsIgnoreCase(date2))
                day2=true;
            if(set.getString("Field").equalsIgnoreCase(date3))
                day3=true;
            if(set.getString("Field").equalsIgnoreCase(date4))
                day4=true;
        }



        String queryS1;
        String queryS2;
        String queryS3;
        String queryS4;

        if(day1 == false){
            query = "alter table slots add "+ date1 +" int default 0 not null;";
            queryS1 = "alter table slots add "+ date1S1 +" int default 0 not null;";
            queryS2 = "alter table slots add "+ date1S2 +" int default 0 not null;";
            queryS3 = "alter table slots add "+ date1S3 +" int default 0 not null;";
            queryS4 = "alter table slots add "+ date1S4 +" int default 0 not null;";

            conn = dbHandler.getConnection();
            conn.createStatement().executeUpdate(query);
            conn.createStatement().executeUpdate(queryS1);
            conn.createStatement().executeUpdate(queryS2);
            conn.createStatement().executeUpdate(queryS3);
            conn.createStatement().executeUpdate(queryS4);

        }
        if(day2 == false){
            query = "alter table slots add "+ date2 +" int default 0 not null;";
            queryS1 = "alter table slots add "+ date2S1 +" int default 0 not null;";
            queryS2 = "alter table slots add "+ date2S2 +" int default 0 not null;";
            queryS3 = "alter table slots add "+ date2S3 +" int default 0 not null;";
            queryS4 = "alter table slots add "+ date2S4 +" int default 0 not null;";

            conn = dbHandler.getConnection();
            conn.createStatement().executeUpdate(query);
            conn.createStatement().executeUpdate(queryS1);
            conn.createStatement().executeUpdate(queryS2);
            conn.createStatement().executeUpdate(queryS3);
            conn.createStatement().executeUpdate(queryS4);
        }
        if(day3 == false){
            query = "alter table slots add "+ date3 +" int default 0 not null;";
            queryS1 = "alter table slots add "+ date3S1 +" int default 0 not null;";
            queryS2 = "alter table slots add "+ date3S2 +" int default 0 not null;";
            queryS3 = "alter table slots add "+ date3S3 +" int default 0 not null;";
            queryS4 = "alter table slots add "+ date3S4 +" int default 0 not null;";

            conn = dbHandler.getConnection();
            conn.createStatement().executeUpdate(query);
            conn.createStatement().executeUpdate(queryS1);
            conn.createStatement().executeUpdate(queryS2);
            conn.createStatement().executeUpdate(queryS3);
            conn.createStatement().executeUpdate(queryS4);
        }
        if(day4 == false){
            query = "alter table slots add "+ date4 +" int default 0 not null;";
            queryS1 = "alter table slots add "+ date4S1 +" int default 0 not null;";
            queryS2 = "alter table slots add "+ date4S2 +" int default 0 not null;";
            queryS3 = "alter table slots add "+ date4S3 +" int default 0 not null;";
            queryS4 = "alter table slots add "+ date4S4 +" int default 0 not null;";

            conn = dbHandler.getConnection();
            conn.createStatement().executeUpdate(query);
            conn.createStatement().executeUpdate(queryS1);
            conn.createStatement().executeUpdate(queryS2);
            conn.createStatement().executeUpdate(queryS3);
            conn.createStatement().executeUpdate(queryS4);
        }

    }

}
