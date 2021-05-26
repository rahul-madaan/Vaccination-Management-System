package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class faqSceneController {

    public void goToMainScene(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        Scene scene2 = new Scene(scene2Parent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
}
