package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminActionsController implements Initializable {

    @FXML
    private Button markAttendanceButton;

    @FXML
    private Button addSlotsButton;

    @FXML
    private Button addCentreButton;

    private Connection conn;

    private DbHandler dbHandler;
    @FXML
    public Rectangle infoRectangle;

    @FXML
    public ImageView infoIconImageView;

    @FXML
    public Text infoText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        if(AdminLoginController.adminPosition.equalsIgnoreCase("local")){
            addCentreButton.setDisable(true);
        }else if(AdminLoginController.adminPosition.equalsIgnoreCase("global")){
            addCentreButton.setDisable(false);
        }

        if(EditVaccineSlotsController.updated==1){
            infoRectangle.setVisible(true);
            infoIconImageView.setVisible(true);
            infoText.setVisible(true);
            EditVaccineSlotsController.updated=0;
        }else{
            infoRectangle.setVisible(false);
            infoIconImageView.setVisible(false);
            infoText.setVisible(false);
        }
    }

    @FXML
    public void addVaccinationCentreButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AddVaccinationCentre.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void markAttendanceButtonClick(ActionEvent event) throws IOException, SQLException {
        if(AdminLoginController.adminPosition.equalsIgnoreCase("global")) {
            Parent scene2Parent = FXMLLoader.load(getClass().getResource("MarkAttendanceSelectCentre.fxml"));
            Scene addMembersScene = new Scene(scene2Parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addMembersScene);
            window.show();
        }
        else if(AdminLoginController.adminPosition.equalsIgnoreCase("local")){
            String queryBoth = "SELECT * from vaccinecentres where CentreID = " + AdminLoginController.adminCentreID +" ;";
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(queryBoth);
            VaccineCentre centre = new VaccineCentre();
            while (set.next()){
                centre.setCentreID(set.getInt("CentreID"));
                centre.setHospitalName(set.getString("Hospital_Name"));
                centre.setAddress(set.getString("Address"));
                centre.setDistrict(set.getString("District"));
                centre.setState(set.getString("State"));
                centre.setPinCode(set.getString("Pin_code"));
                centre.setVaccineName(set.getString("vaccineName"));
                centre.setVaccineCost(set.getInt("vaccineCost"));
            }

            ChooseCentreSlotAddController.selectedCentre = centre;
            Parent scene2Parent = FXMLLoader.load(getClass().getResource("MarkAttendance.fxml"));
            Scene addMembersScene = new Scene(scene2Parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addMembersScene);
            window.show();
        }
    }

    @FXML
    public void addVaccineSlotsButtonClick(ActionEvent event) throws IOException, SQLException {
        if(AdminLoginController.adminPosition.equalsIgnoreCase("global")) {
            Parent scene2Parent = FXMLLoader.load(getClass().getResource("ChooseCentreSlotAdd.fxml"));
            Scene addMembersScene = new Scene(scene2Parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addMembersScene);
            window.show();
        }
        else if(AdminLoginController.adminPosition.equalsIgnoreCase("local")){
            String queryBoth = "SELECT * from vaccinecentres where CentreID = " + AdminLoginController.adminCentreID +" ;";
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(queryBoth);
            VaccineCentre centre = new VaccineCentre();
            while (set.next()){
                centre.setCentreID(set.getInt("CentreID"));
                centre.setHospitalName(set.getString("Hospital_Name"));
                centre.setAddress(set.getString("Address"));
                centre.setDistrict(set.getString("District"));
                centre.setState(set.getString("State"));
                centre.setPinCode(set.getString("Pin_code"));
                centre.setVaccineName(set.getString("vaccineName"));
                centre.setVaccineCost(set.getInt("vaccineCost"));
            }

            ChooseCentreSlotAddController.selectedCentre = centre;
            Parent scene2Parent = FXMLLoader.load(getClass().getResource("EditVaccineSlots.fxml"));
            Scene addMembersScene = new Scene(scene2Parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addMembersScene);
            window.show();
        }
    }

    @FXML
    public void faqButtonClicked(ActionEvent event) throws IOException {
        faqSceneController.returnToPage = "AdminActions.fxml";
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("faqScene.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void logoutButtonClicked(ActionEvent event)throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();

    }


}
