package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        vaccineNameToggleGroup = new ToggleGroup();
        covaxinRadioButton.setToggleGroup(vaccineNameToggleGroup);
        covishieldRadioButton.setToggleGroup(vaccineNameToggleGroup);
        sputnikvRadioButton.setToggleGroup(vaccineNameToggleGroup);
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


}
