package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddVaccinationCentreController implements Initializable {
    private Connection conn;

    private DbHandler dbHandler;

    @FXML
    private TextField centreNameTextField;

    @FXML
    private TextField centreAddressTextField;

    @FXML
    private TextField centreDistrictTextField;

    @FXML
    private TextField centreStateTextField;

    @FXML
    private TextField centrePinCodeTextField;

    @FXML
    private TextField centreAdminUsernameTextField;

    @FXML
    private TextField centreAdminPasswordTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
    }

    public void addCentreButtonClicked(ActionEvent event) throws SQLException {
        //check all text fields match constraints.
        String centreName = centreNameTextField.getText();
        String centreAddress = centreAddressTextField.getText();
        String centreState = centreAddressTextField.getText();
        String centreDistrict = centreDistrictTextField.getText();
        String centrePinCode = centrePinCodeTextField.getText();
        String centreAdminUsername = centreAdminUsernameTextField.getText();
        String centreAdminPassword = centreAdminPasswordTextField.getText();

        String queryBoth = String.format("INSERT INTO vaccineCentres (Hospital_name,Address,District,State,Pin_code) Values(%s,%s,%s,%s,%s)",
                centreName,centreAddress,centreDistrict,centreState,centrePinCode);
        //check this query, not tested yet
        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(queryBoth);
    }

    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AdminActions.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }
}
