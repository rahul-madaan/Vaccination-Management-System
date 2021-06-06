package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DownloadCertificatesController implements Initializable {

    @FXML
    private Button dose1Button;

    @FXML
    private Button dose2Button;

    @FXML
    private Label beneficiaryNameLabel;

    @FXML
    private Label beneficiaryAadhaarNumberLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkDownloadableStatus();
        setBeneficiaryDetails();
    }

    public void setBeneficiaryDetails(){
        Member selectedMember = allMembersController.selectedmemberForCertificate;

    }

    public void checkDownloadableStatus(){
        Member selectedMember = allMembersController.selectedmemberForCertificate;
        if(selectedMember.getDose2Status().equalsIgnoreCase("vaccinated")){
            dose2Button.setDisable(false);
        }else{
            dose2Button.setDisable(true);
        }
    }

    @FXML
    public void logoutButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void backToMembersButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("allMembersScene.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void faqButtonClick(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("faqScene.fxml"));
        Scene faqScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(faqScene);
        window.show();
    }

}
