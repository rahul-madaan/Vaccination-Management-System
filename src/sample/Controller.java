package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.naming.spi.InitialContextFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private RadioButton loginRadioButton;
    @FXML private RadioButton signupRadioButton;
    @FXML private Label loginSignupLabel;
    private ToggleGroup loginSignupToggleGroup;
    @FXML private Button loginSignupButton;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label passwordLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginSignupToggleGroup = new ToggleGroup();
        this.loginRadioButton.setToggleGroup(loginSignupToggleGroup);
        this.signupRadioButton.setToggleGroup(loginSignupToggleGroup);

    }

    public void loginSignupToggle(){
        if(signupRadioButton.isSelected()){
            passwordLabel.setText("");
            passwordTextField.setVisible(false);
            loginSignupLabel.setText("USER SIGN-UP");
            loginSignupButton.setText("SIGN-UP");

        }
        else if(loginRadioButton.isSelected()){
            passwordLabel.setText("Password");
            passwordTextField.setPrefHeight(40);
            passwordTextField.setPrefWidth(200);
            passwordTextField.setVisible(true);
            loginSignupLabel.setText("USER LOGIN");
            loginSignupButton.setText("LOGIN");
        }
    }


}
