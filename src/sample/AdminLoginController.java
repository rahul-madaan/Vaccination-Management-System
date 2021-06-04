package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {

    @FXML
    private TextField userIDTextField;

    @FXML
    private TextField passwordTextField;

    private Connection conn;

    private DbHandler dbHandler;
    
    @FXML
    private Label errorLabel;

    public static int adminCentreID;
    public static String adminPosition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
    }


    public void loginButtonClick(ActionEvent event) throws SQLException, IOException {
        String enteredPassword = passwordTextField.getText();
        String enteredUserID = userIDTextField.getText();

        String queryBoth = "SELECT * from login_admin where userID ='" + enteredUserID + "' and Password = '" + enteredPassword + "' ;";
        conn = dbHandler.getConnection();
        ResultSet bothSet = conn.createStatement().executeQuery(queryBoth);

        String queryUserID = "SELECT * from login_admin where userID ='" + enteredUserID + "';";
        ResultSet userIDSet = conn.createStatement().executeQuery(queryUserID);
        String position = null;
        if (userIDSet.next() == false) {
            errorLabel.setVisible(true);
            errorLabel.setText("You are not registered!");
            errorLabel.setStyle(" -fx-background-color: #ff5c5c; -fx-text-fill: black");
            return;
        } else if (bothSet.next() == false) {
            errorLabel.setVisible(true);
            errorLabel.setText("Enter correct password!");
            errorLabel.setStyle(" -fx-background-color: #ff5c5c; -fx-text-fill: black");
            return;
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText("Login Successful!");
            errorLabel.setStyle(" -fx-background-color: green; -fx-text-fill: white");

            position = bothSet.getString("position");
            AdminLoginController.adminPosition = position;
            AdminLoginController.adminCentreID = bothSet.getInt("centreID");
            loginSignupButtonClickSuccess(event);
        }
    
    }

    public void loginSignupButtonClickSuccess(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AdminActions.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void userLoginButtonClick(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

}


