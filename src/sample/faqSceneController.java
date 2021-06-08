package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class faqSceneController implements Initializable {
    public static String returnToPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToMainScene(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource(faqSceneController.returnToPage));
        Scene scene2 = new Scene(scene2Parent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }


}
