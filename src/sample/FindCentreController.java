package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private ObservableList<VaccineCentre> centreList;

    @FXML
    private RadioButton searchByDistrictRadioButton;

    @FXML
    private RadioButton searchByPinCodeRadioButton;

    @FXML
    private TableView<VaccineCentre> vaccineCentreTableView;

    @FXML
    private TableColumn<?,?> colCentreID;

    @FXML
    private TableColumn<?,?> colHospitalName;

    @FXML
    private TableColumn<?,?> colCentreAddress;

    @FXML
    private TableColumn<?,?> colCentrePinCode;

    @FXML
    private TableColumn<?,?> colVaccineName;

    @FXML
    private TableColumn<?,?> colCost;

    @FXML
    private TableColumn<VaccineCentre,String> colDate1;

    @FXML
    private TableColumn<VaccineCentre,String> colDate2;

    @FXML
    private TableColumn<VaccineCentre,String> colDate3;

    @FXML
    private TableColumn<VaccineCentre,String> colDate4;

    public static VaccineCentre selectedCentre;
    public static int selectedCentreID;
    public static String selectedDate;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        //toggle group
        populateComboBoxStates();
        districtPinCodeToggleGroup = new ToggleGroup();
        searchByPinCodeRadioButton.setToggleGroup(districtPinCodeToggleGroup);
        searchByDistrictRadioButton.setToggleGroup(districtPinCodeToggleGroup);
        searchByDistrictRadioButton.setSelected(true);
        java.util.Date date=new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        String date1 = c.getTime().toString();
        date1 = date1.substring(4,10);
        colDate1.setText(date1);

        c.add(Calendar.DATE, 1);
        String date2 = c.getTime().toString();
        date2 = date2.substring(4,10);
        colDate2.setText(date2);

        c.add(Calendar.DATE, 1);
        String date3 = c.getTime().toString();
        date3 = date3.substring(4,10);
        colDate3.setText(date3);

        c.add(Calendar.DATE, 1);
        String date4 = c.getTime().toString();
        date4 = date4.substring(4,10);
        colDate4.setText(date4);

    }

    @FXML
    private void clearDistrictSelection(){
        districtsComboBox.setValue("");//to remove any selection if user changes state after selecting district
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
    private void districtPinCodeToggleClicked(){
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

    @FXML
    public void handleEnter(KeyEvent k) {
        if (k.getCode().equals(KeyCode.ENTER)) {
            if(pinCodeTextField.getText().length()!=6) {
                //put pincode check for characters also
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please enter a valid Pin Code");
                alert.show();
                pinCodeTextField.setText("");
                

            }
            populateVaccineCentresTable();
        }
    }

    @FXML
    public void populateVaccineCentresTable(){
        String selectedDistrict = districtsComboBox.getValue().toString();
        String selectedPinCode = pinCodeTextField.getText();
        String query = new String();
        try {
            centreList = FXCollections.observableArrayList();
            if(searchByDistrictRadioButton.isSelected()) {
                query = "SELECT * FROM vaccinecentres where District ='" + selectedDistrict + "';";
            }
            if(searchByPinCodeRadioButton.isSelected()) {
                query = "SELECT * FROM vaccinecentres where Pin_Code ='" + selectedPinCode + "';";
            }
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                VaccineCentre centre = new VaccineCentre();
                centre.setCentreID(set.getInt("CentreID"));
                centre.setHospitalName(set.getString("Hospital_Name"));
                centre.setAddress(set.getString("Address"));
                centre.setDistrict(set.getString("District"));
                centre.setState(set.getString("State"));
                centre.setPinCode(set.getString("Pin_code"));
                centre.setVaccineName(set.getString("vaccineName"));
                centre.setVaccineCost(set.getInt("vaccineCost"));
                centreList.add(centre);
            }

//we are now setting the name of columns for java to understand
            colCentreID.setCellValueFactory(new PropertyValueFactory<>("CentreID"));
            colHospitalName.setCellValueFactory(new PropertyValueFactory<>("HospitalName"));
            colCentreAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            colCentrePinCode.setCellValueFactory(new PropertyValueFactory<>("PinCode"));
            colVaccineName.setCellValueFactory(new PropertyValueFactory<>("VaccineName"));
            colCost.setCellValueFactory(new PropertyValueFactory<>("vaccineCost"));

            Callback<TableColumn<VaccineCentre, String>, TableCell<VaccineCentre, String>> cellFactoryDate1 = (param) -> {
                final TableCell<VaccineCentre, String> cell = new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button editButton = new Button("Book");
                            editButton.setOnAction(event -> {
                                VaccineCentre p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked Centre ID\n" + p.getCentreID() +
                                        " with Pin code \n" + p.getPinCode() + "\nand date " + colDate1.getText());
                                alert.show();
                                FindCentreController.selectedCentre = p;
                                FindCentreController.selectedDate = colDate1.getText().replace(" ","");
                                Parent scene2Parent = null;
                                try {
                                    scene2Parent = FXMLLoader.load(getClass().getResource("BookingConfirmation.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Scene findCentreScene = new Scene(scene2Parent);
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(findCentreScene);
                                window.show();
                            });
                            setGraphic(editButton);
                        }
                        setText(null);
                    }
                    ;
                };

                return cell;
            };

            colDate1.setCellFactory(cellFactoryDate1);

            Callback<TableColumn<VaccineCentre, String>, TableCell<VaccineCentre, String>> cellFactoryDate2 = (param) -> {
                final TableCell<VaccineCentre, String> cell = new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button editButton = new Button("Book");
                            editButton.setOnAction(event -> {
                                VaccineCentre p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked Centre ID\n" + p.getCentreID() +
                                        " with Pin code \n" + p.getPinCode() + "\nand date " + colDate2.getText());
                                alert.show();
                                FindCentreController.selectedCentre = p;
                                FindCentreController.selectedDate = colDate2.getText().replace(" ","");
                                Parent scene2Parent = null;
                                try {
                                    scene2Parent = FXMLLoader.load(getClass().getResource("BookingConfirmation.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Scene findCentreScene = new Scene(scene2Parent);
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(findCentreScene);
                                window.show();
                            });
                            setGraphic(editButton);
                        }
                        setText(null);
                    }
                    ;
                };

                return cell;
            };

            colDate2.setCellFactory(cellFactoryDate2);

            Callback<TableColumn<VaccineCentre, String>, TableCell<VaccineCentre, String>> cellFactoryDate3 = (param) -> {
                final TableCell<VaccineCentre, String> cell = new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button editButton = new Button("Book");
                            editButton.setOnAction(event -> {
                                VaccineCentre p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked Centre ID\n" + p.getCentreID() +
                                        " with Pin code \n" + p.getPinCode() + "\nand date " + colDate3.getText());
                                alert.show();
                                FindCentreController.selectedCentre = p;
                                FindCentreController.selectedDate = colDate3.getText().replace(" ","");
                                Parent scene2Parent = null;
                                try {
                                    scene2Parent = FXMLLoader.load(getClass().getResource("BookingConfirmation.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Scene findCentreScene = new Scene(scene2Parent);
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(findCentreScene);
                                window.show();
                            });
                            setGraphic(editButton);
                        }
                        setText(null);
                    }
                    ;
                };

                return cell;
            };

            colDate3.setCellFactory(cellFactoryDate3);

            Callback<TableColumn<VaccineCentre, String>, TableCell<VaccineCentre, String>> cellFactoryDate4 = (param) -> {
                final TableCell<VaccineCentre, String> cell = new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button editButton = new Button("Book");
                            editButton.setOnAction(event -> {
                                VaccineCentre p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked Centre ID\n" + p.getCentreID() +
                                        " with Pin code \n" + p.getPinCode() + "\nand date " + colDate4.getText());
                                alert.show();
                                FindCentreController.selectedCentre = p;
                                FindCentreController.selectedDate = colDate4.getText().replace(" ","");
                                Parent scene2Parent = null;
                                try {
                                    scene2Parent = FXMLLoader.load(getClass().getResource("BookingConfirmation.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Scene findCentreScene = new Scene(scene2Parent);
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(findCentreScene);
                                window.show();
                            });
                            setGraphic(editButton);
                        }
                        setText(null);
                    }
                    ;
                };

                return cell;
            };

            colDate4.setCellFactory(cellFactoryDate4);

            vaccineCentreTableView.setItems(centreList);

        } catch (SQLException throwable) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null,  throwable);
            throwable.printStackTrace();
        }

    }
}
