package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FindCentreController implements Initializable {

    private Connection conn;

    @FXML
    private TextField pinCodeTextField;

    @FXML
    private Label pinCodeLabel;

    private DbHandler dbHandler;

    @FXML
    private ComboBox statesComboBox;

    @FXML
    private ComboBox districtsComboBox;

    @FXML
    private Label selectStateLabel;

    @FXML
    private Label selectDistrictLabel;

    private ToggleGroup districtPinCodeToggleGroup;

    @FXML
    private RadioButton searchByDistrictRadioButton;

    @FXML
    private RadioButton searchByPinCodeRadioButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        //toggle group
        populateComboBoxStates();
        districtPinCodeToggleGroup = new ToggleGroup();
        searchByPinCodeRadioButton.setToggleGroup(districtPinCodeToggleGroup);
        searchByDistrictRadioButton.setToggleGroup(districtPinCodeToggleGroup);
        searchByDistrictRadioButton.setSelected(true);
    }

    @FXML
    private void clearDistrictSelection(){
        districtsComboBox.setValue(null);//to remove any selection if user changes state after selecting district
        districtsComboBox.setValue("Select District");    }

    private void populateComboBoxStates() {


        try {
            String query = "SELECT DISTINCT State from vaccineCentres order by State asc ;";

            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                statesComboBox.getItems().add(set.getString("state"));
            }
            districtsComboBox.setValue("Select District");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void populateComboBoxDistricts(){
        int rows =0;
        try {
            String stateSelection = new String();
            if(statesComboBox.getValue()!=null) {
                stateSelection = statesComboBox.getValue().toString();
            }
            String query = "SELECT DISTINCT District from vaccineCentres where state = '"+ stateSelection +"'order by district asc ;";
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);
            districtsComboBox.getItems().removeAll(districtsComboBox.getItems().toArray());
            while (set.next()) {
                districtsComboBox.getItems().add(set.getString("district"));
                if (rows<9)
                    rows = rows + 1;
            }
            districtsComboBox.hide(); //before you set new visibleRowCount value
            districtsComboBox.setVisibleRowCount(rows); // set new visibleRowCount value
            districtsComboBox.show(); //after you set new visibleRowCount value

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    @FXML
    private void districtPinCodeToggleclicked(){
        if(searchByDistrictRadioButton.isSelected()){
            pinCodeLabel.setVisible(false);
            pinCodeTextField.setVisible(false);
            statesComboBox.setVisible(true);
            selectStateLabel.setVisible(true);
            districtsComboBox.setVisible(true);
            selectDistrictLabel.setVisible(true);
        }
        else if(searchByPinCodeRadioButton.isSelected()){
            pinCodeLabel.setVisible(true);
            pinCodeTextField.setVisible(true);
            statesComboBox.setVisible(false);
            selectStateLabel.setVisible(false);
            districtsComboBox.setVisible(false);
            selectDistrictLabel.setVisible(false);
        }
    }
}
