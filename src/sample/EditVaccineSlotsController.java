package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EditVaccineSlotsController implements Initializable {
    @FXML
    private Label centreIDLabel;

    @FXML
    private Label centreNameLabel;

    @FXML
    private Label centreAddressLabel;

    @FXML
    private Label centreStateLabel;

    @FXML
    private Label centrePinCodeLabel;

    @FXML
    private ComboBox selectDateComboBox;

    @FXML
    private TextField totalSlotsTextField;

    @FXML
    private TextField slot1TextField;

    @FXML
    private TextField slot2TextField;

    @FXML
    private TextField slot3TextField;

    @FXML
    private TextField slot4TextField;

    @FXML
    private RadioButton covishieldRadioButton;

    @FXML
    private RadioButton covaxinRadioButton;

    @FXML
    private RadioButton sputnikvRadioButton;

    @FXML
    private TextField updateCostTextField;

    private ToggleGroup vaccineNameToggleGroup;

    private Connection conn;

    private DbHandler dbHandler;

    public static int updated;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        vaccineNameToggleGroup = new ToggleGroup();
        covaxinRadioButton.setToggleGroup(vaccineNameToggleGroup);
        covishieldRadioButton.setToggleGroup(vaccineNameToggleGroup);
        sputnikvRadioButton.setToggleGroup(vaccineNameToggleGroup);
        covaxinRadioButton.setUserData("COVAXIN");
        covishieldRadioButton.setUserData("COVISHIELD");
        sputnikvRadioButton.setUserData("SPUTNIK V");
        VaccineCentre selectedVaccineCentre = ChooseCentreSlotAddController.selectedCentre;
        centreIDLabel.setText(Integer.toString(selectedVaccineCentre.getCentreID()));
        centreNameLabel.setText(selectedVaccineCentre.getHospitalName());
        centreAddressLabel.setText(selectedVaccineCentre.getAddress());
        centreStateLabel.setText(selectedVaccineCentre.getState());
        centrePinCodeLabel.setText(selectedVaccineCentre.getPinCode());
        java.util.Date date=new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        String date1 = c.getTime().toString();
        String date1temp = date1.substring(4,7);
        date1 = date1.substring(8,10);
        date1 = date1temp + date1;
        String date1S1 = date1 + "s1";
        String date1S2 = date1 + "s2";
        String date1S3 = date1 + "s3";
        String date1S4 = date1 + "s4";


        c.add(Calendar.DATE, 1);
        String date2 = c.getTime().toString();
        String date2temp = date2.substring(4,7);
        date2 = date2.substring(8,10);
        date2 = date2temp + date2;
        String date2S1 = date2 + "s1";
        String date2S2 = date2 + "s2";
        String date2S3 = date2 + "s3";
        String date2S4 = date2 + "s4";

        c.add(Calendar.DATE, 1);
        String date3 = c.getTime().toString();
        String date3temp = date3.substring(4,7);
        date3 = date3.substring(8,10);
        date3 = date3temp + date3;
        String date3S1 = date3 + "s1";
        String date3S2 = date3 + "s2";
        String date3S3 = date3 + "s3";
        String date3S4 = date3 + "s4";

        c.add(Calendar.DATE, 1);
        String date4 = c.getTime().toString();
        String date4temp = date4.substring(4,7);
        date4 = date4.substring(8,10);
        date4 = date4temp + date4;
        String date4S1 = date4 + "s1";
        String date4S2 = date4 + "s2";
        String date4S3 = date4 + "s3";
        String date4S4 = date4 + "s4";

        selectDateComboBox.getItems().addAll(date1,date2,date3,date4);
        updateCostTextField.setText(Integer.toString(selectedVaccineCentre.getVaccineCost()));

        if(selectedVaccineCentre.getVaccineName().equalsIgnoreCase("covishield")){
            covishieldRadioButton.setSelected(true);
        }
        else if(selectedVaccineCentre.getVaccineName().equalsIgnoreCase("covaxin")){
            covaxinRadioButton.setSelected(true);
        }
        else if(selectedVaccineCentre.getVaccineName().equalsIgnoreCase("Sputnik V")){
            sputnikvRadioButton.setSelected(true);
        }
    }

    @FXML
    public void mainMenuButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AdminActions.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();

    }

    @FXML
    public void dateSelected() throws SQLException {
        java.util.Date date=new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        String date1 = c.getTime().toString();
        String date1temp = date1.substring(4,7);
        date1 = date1.substring(8,10);
        date1 = date1temp + date1;
        String date1S1 = date1 + "s1";
        String date1S2 = date1 + "s2";
        String date1S3 = date1 + "s3";
        String date1S4 = date1 + "s4";


        c.add(Calendar.DATE, 1);
        String date2 = c.getTime().toString();
        String date2temp = date2.substring(4,7);
        date2 = date2.substring(8,10);
        date2 = date2temp + date2;
        String date2S1 = date2 + "s1";
        String date2S2 = date2 + "s2";
        String date2S3 = date2 + "s3";
        String date2S4 = date2 + "s4";

        c.add(Calendar.DATE, 1);
        String date3 = c.getTime().toString();
        String date3temp = date3.substring(4,7);
        date3 = date3.substring(8,10);
        date3 = date3temp + date3;
        String date3S1 = date3 + "s1";
        String date3S2 = date3 + "s2";
        String date3S3 = date3 + "s3";
        String date3S4 = date3 + "s4";

        c.add(Calendar.DATE, 1);
        String date4 = c.getTime().toString();
        String date4temp = date4.substring(4,7);
        date4 = date4.substring(8,10);
        date4 = date4temp + date4;
        String date4S1 = date4 + "s1";
        String date4S2 = date4 + "s2";
        String date4S3 = date4 + "s3";
        String date4S4 = date4 + "s4";

        if(selectDateComboBox.getValue().toString().equalsIgnoreCase(date1)) {
            VaccineCentre selectedVaccineCentre = ChooseCentreSlotAddController.selectedCentre;
            String query = "SELECT * from slots where centreID = '" + selectedVaccineCentre.getCentreID() + "';";
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                totalSlotsTextField.setText(set.getString(date1));
                slot1TextField.setText(set.getString(date1S1));
                slot2TextField.setText(set.getString(date1S2));
                slot3TextField.setText(set.getString(date1S3));
                slot4TextField.setText(set.getString(date1S4));
            }
        }

        if(selectDateComboBox.getValue().toString().equalsIgnoreCase(date2)){
            VaccineCentre selectedVaccineCentre = ChooseCentreSlotAddController.selectedCentre;
            String query = "SELECT * from slots where centreID = '" + selectedVaccineCentre.getCentreID() + "';";
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                totalSlotsTextField.setText(set.getString(date2));
                slot1TextField.setText(set.getString(date2S1));
                slot2TextField.setText(set.getString(date2S2));
                slot3TextField.setText(set.getString(date2S3));
                slot4TextField.setText(set.getString(date2S4));
            }
        }

        if(selectDateComboBox.getValue().toString().equalsIgnoreCase(date3)){
            VaccineCentre selectedVaccineCentre = ChooseCentreSlotAddController.selectedCentre;
            String query = "SELECT * from slots where centreID = '" + selectedVaccineCentre.getCentreID() + "';";
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                totalSlotsTextField.setText(set.getString(date3));
                slot1TextField.setText(set.getString(date3S1));
                slot2TextField.setText(set.getString(date3S2));
                slot3TextField.setText(set.getString(date3S3));
                slot4TextField.setText(set.getString(date3S4));
            }
        }

        if(selectDateComboBox.getValue().toString().equalsIgnoreCase(date4)){
            VaccineCentre selectedVaccineCentre = ChooseCentreSlotAddController.selectedCentre;
            String query = "SELECT * from slots where centreID = '" + selectedVaccineCentre.getCentreID() + "';";
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                totalSlotsTextField.setText(set.getString(date4));
                slot1TextField.setText(set.getString(date4S1));
                slot2TextField.setText(set.getString(date4S2));
                slot3TextField.setText(set.getString(date4S3));
                slot4TextField.setText(set.getString(date4S4));
            }
        }

    }

    @FXML
    private void divideEquallyButtonCLicked(){
        if(totalSlotsTextField.getText().matches("[A-z]")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter a valid number!");
            alert.show();

        }
        else {
            int totalSlots = Integer.parseInt(totalSlotsTextField.getText());
            if (totalSlots % 4 == 0) {
                slot1TextField.setText(Integer.toString(totalSlots / 4));
                slot2TextField.setText(Integer.toString(totalSlots / 4));
                slot3TextField.setText(Integer.toString(totalSlots / 4));
                slot4TextField.setText(Integer.toString(totalSlots / 4));
            } else if (totalSlots % 4 == 1) {
                slot1TextField.setText(Integer.toString((totalSlots / 4) + 1));
                slot2TextField.setText(Integer.toString(totalSlots / 4));
                slot3TextField.setText(Integer.toString(totalSlots / 4));
                slot4TextField.setText(Integer.toString(totalSlots / 4));
            } else if (totalSlots % 4 == 2) {
                slot1TextField.setText(Integer.toString((totalSlots / 4) + 1));
                slot2TextField.setText(Integer.toString((totalSlots / 4) + 1));
                slot3TextField.setText(Integer.toString(totalSlots / 4));
                slot4TextField.setText(Integer.toString(totalSlots / 4));
            } else if (totalSlots % 4 == 3) {
                slot1TextField.setText(Integer.toString((totalSlots / 4) + 1));
                slot2TextField.setText(Integer.toString((totalSlots / 4) + 1));
                slot3TextField.setText(Integer.toString((totalSlots / 4) + 1));
                slot4TextField.setText(Integer.toString(totalSlots / 4));
            }
        }
    }

    public void submitButtonCLicked(ActionEvent event) throws SQLException, IOException {
        VaccineCentre selectedVaccineCentre = ChooseCentreSlotAddController.selectedCentre;
        int totalSlots = Integer.parseInt(totalSlotsTextField.getText());
        int slot1 = Integer.parseInt(slot1TextField.getText());
        int slot2 = Integer.parseInt(slot2TextField.getText());
        int slot3 = Integer.parseInt(slot3TextField.getText());
        int slot4 = Integer.parseInt(slot4TextField.getText());
        int cost = Integer.parseInt(updateCostTextField.getText());
        String vaccineName = vaccineNameToggleGroup.getSelectedToggle().getUserData().toString();
        String date = selectDateComboBox.getValue().toString();
        String dateS1 = date + "s1";
        String dateS2 = date + "s2";
        String dateS3 = date + "s3";
        String dateS4 = date + "s4";
        String query = "UPDATE slots SET " +
                date +" = "+ totalSlots +", "+
                dateS1 +" = "+ slot1 +", "+
                dateS2 +" = "+ slot2 +", "+
                dateS3 +" = "+ slot3 +", "+
                dateS4 +" = "+ slot4 +" WHERE centreID = " + selectedVaccineCentre.getCentreID() + ";";

        conn = dbHandler.getConnection();
        conn.createStatement().executeUpdate(query);

        query = "UPDATE vaccineCentres " +
                "SET vaccineName = '" + vaccineName +
                "' , vaccineCost = " + cost +
                " WHERE centreID = " + selectedVaccineCentre.getCentreID() + ";";

        conn = dbHandler.getConnection();
        conn.createStatement().executeUpdate(query);

        updated=1;

        Parent scene2Parent = FXMLLoader.load(getClass().getResource("ChooseCentreSlotAdd.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();


    }

}
