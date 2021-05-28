package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class allMembersController {

    @FXML
    public void secondWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addNewMemberScene.fxml"));
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(root));
        secondStage.show();
        secondStage.setTitle("part2");
    }

    public void populateMembersTable(){

    }

    public void faqButtonClick(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("faqScene.fxml"));
        Scene faqScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(faqScene);
        window.show();
    }
}
