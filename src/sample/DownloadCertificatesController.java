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
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

    private static boolean downloadSuccess = false;

    @FXML
    private ImageView infoIconImageView;

    @FXML
    private Text notificationText;

    @FXML
    private Rectangle notificationRectangle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkDownloadableStatus();
        setBeneficiaryDetails();
        if(DownloadCertificatesController.downloadSuccess==true){
            notificationRectangle.setVisible(true);
            notificationText.setVisible(true);
            infoIconImageView.setVisible(true);
            DownloadCertificatesController.downloadSuccess=false;
        }else if(DownloadCertificatesController.downloadSuccess==false){
            //hide
            notificationRectangle.setVisible(false);
            notificationText.setVisible(false);
            infoIconImageView.setVisible(false);
        }
    }

    public void setBeneficiaryDetails(){
        Member selectedMember = allMembersController.selectedmemberForCertificate;
        beneficiaryNameLabel.setText(selectedMember.getName());
        beneficiaryAadhaarNumberLabel.setText(selectedMember.getAadhaarNumber().substring(0,4) + "-" +selectedMember.getAadhaarNumber().substring(4,8) + "-" +selectedMember.getAadhaarNumber().substring(8,12));
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
    public void downloadDose1CertificateButtonClick(ActionEvent event) throws Exception {
        Member selectedMember = allMembersController.selectedmemberForCertificate;
        PdfMaker.makeCertificate(event, selectedMember,1);
        DownloadCertificatesController.downloadSuccess=true;
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("DownloadCertificates.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }
    @FXML
    public void downloadDose2CertificateButtonClick(ActionEvent event) throws Exception {
        Member selectedMember = allMembersController.selectedmemberForCertificate;
        PdfMaker.makeCertificate(event, selectedMember,2);
        DownloadCertificatesController.downloadSuccess=true;
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("DownloadCertificates.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void faqButtonClicked(ActionEvent event) throws IOException {
        faqSceneController.returnToPage = "DownloadCertificates.fxml";
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("faqScene.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
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
