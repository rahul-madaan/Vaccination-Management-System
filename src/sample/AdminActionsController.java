package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminActionsController implements Initializable {

    @FXML
    private Button markAttendanceButton;

    @FXML
    private Button addSlotsButton;

    @FXML
    private Button addCentreButton;

    @FXML
    private Button deleteCentreButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(AdminLoginController.adminPosition.equalsIgnoreCase("local")){
            addCentreButton.setDisable(true);
            deleteCentreButton.setDisable(true);
        }else if(AdminLoginController.adminPosition.equalsIgnoreCase("global")){
            addCentreButton.setDisable(false);
            deleteCentreButton.setDisable(false);
        }
    }

    @FXML
    public void addVaccineSlotsButtonClick(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("ChooseCentreSlotAdd.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }


}
