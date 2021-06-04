package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarkAttendanceController implements Initializable {

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
    private ToggleGroup timeSlotToggleGroup;

    @FXML
    private ImageView refreshButtonImageView;

    @FXML
    private Label selectedDateLabel;

    @FXML
    private ComboBox selectDateComboBox;

    private Connection conn;
    private DbHandler dbHandler;

    private ObservableList<Member> memberList;

    @FXML
    private TableColumn<Member,String> colStatus;

    @FXML
    private TableColumn<?,?> colRefID;

    @FXML
    private TableColumn<?,?> colName;

    @FXML
    private TableColumn<?,?> colSlot;

    @FXML
    private TableColumn<Member,String> colColour;

    @FXML
    private TableView<Member> attendanceTable;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        try {
            populateCentreDetails();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateSelectDateComboBox();
    }

    public void populateCentreDetails() throws SQLException {
        int centreID = AdminLoginController.adminCentreID;
        String query = "SELECT * FROM vaccinecentres where CentreID ='" + centreID + "';";
        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(query);

        while (set.next()){
            centreIDLabel.setText(set.getString("centreID"));
            centreNameLabel.setText(set.getString("Hospital_name"));
            centreAddressLabel.setText(set.getString("address"));
            centreStateLabel.setText(set.getString("state"));
            centrePinCodeLabel.setText(set.getString("pin_code"));
        }

    }

    public void populateSelectDateComboBox() {

        //previous 4 dates
        java.util.Date date=new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String date1 = c.getTime().toString();
        String date1temp = date1.substring(4,7);
        date1 = date1.substring(8,10);
        date1 = date1temp + date1;
        String date1S1 = date1 + "s1";
        String date1S2 = date1 + "s2";
        String date1S3 = date1 + "s3";
        String date1S4 = date1 + "s4";


        c.add(Calendar.DATE, -1);
        String date2 = c.getTime().toString();
        String date2temp = date2.substring(4,7);
        date2 = date2.substring(8,10);
        date2 = date2temp + date2;
        String date2S1 = date2 + "s1";
        String date2S2 = date2 + "s2";
        String date2S3 = date2 + "s3";
        String date2S4 = date2 + "s4";

        c.add(Calendar.DATE, -1);
        String date3 = c.getTime().toString();
        String date3temp = date3.substring(4,7);
        date3 = date3.substring(8,10);
        date3 = date3temp + date3;
        String date3S1 = date3 + "s1";
        String date3S2 = date3 + "s2";
        String date3S3 = date3 + "s3";
        String date3S4 = date3 + "s4";

        c.add(Calendar.DATE, -1);
        String date4 = c.getTime().toString();
        String date4temp = date4.substring(4,7);
        date4 = date4.substring(8,10);
        date4 = date4temp + date4;
        String date4S1 = date4 + "s1";
        String date4S2 = date4 + "s2";
        String date4S3 = date4 + "s3";
        String date4S4 = date4 + "s4";

        String comboBoxDate1 = date1.substring(3,5) + " " + date1.substring(0,3) + " 2021";
        String comboBoxDate2 = date2.substring(3,5) + " " + date2.substring(0,3) + " 2021";
        String comboBoxDate3 = date3.substring(3,5) + " " + date3.substring(0,3) + " 2021";
        String comboBoxDate4 = date4.substring(3,5) + " " + date4.substring(0,3) + " 2021";


        selectDateComboBox.getItems().addAll(comboBoxDate1,comboBoxDate2,comboBoxDate3,comboBoxDate4);
    }

    @FXML
    private void backToMainMenuButtonClick(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AdminActions.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    private void adminLogoutButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    @FXML
    public void populateAttendanceTable() throws SQLException {
        try {
            Label label = new Label("No Vaccinations done on " + selectDateComboBox.getValue().toString());

            label.setFont(new Font("Arial", 24));
            attendanceTable.setPlaceholder(label);
            memberList = FXCollections.observableArrayList();
            //gets in format 20 jun 2021
            //gives in format jun20
            String selectedDate = selectDateComboBox.getValue().toString().substring(0, 6).replace(" ", "");
            selectedDate = selectedDate.substring(2,5) + selectedDate.substring(0,2);
            String query = "SELECT * FROM members where dose1date = '" + selectedDate + "' OR dose2date = '" + selectedDate + "' ;";
            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);
            while (set.next()) {
                Member member = new Member();
                member.setRefID(set.getInt("RefID"));
                member.setName(set.getString("name"));
                member.setAadhaarNumber(set.getString("AadhaarNumber"));
                member.setAge(set.getInt("Age"));
                member.setDose1Status(set.getString("Dose1Status"));
                member.setDose2Status(set.getString("Dose2Status"));
                member.setDose1CentreID(set.getInt("dose1centreID"));
                member.setDose2CentreID(set.getInt("dose2centreID"));
                member.setDose1date(set.getString("dose1date"));
                member.setDose2date(set.getString("dose2date"));
                member.setDose1Slot(set.getInt("dose1slot"));
                member.setDose2Slot(set.getInt("dose2slot"));
                member.setDose1Name(set.getString("dose1vaccineName"));
                member.setDose2Name(set.getString("dose2vaccineName"));
                memberList.add(member);
            }

            colRefID.setCellValueFactory(new PropertyValueFactory<>("RefID"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colSlot.setCellValueFactory(new PropertyValueFactory<>("Dose1Slot"));

            Callback<TableColumn<Member, String>, TableCell<Member, String>> cellFactory = (param) -> {
                final TableCell<Member, String> cell = new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button editButton = new Button("Edit");
                            editButton.setOnAction(event -> {
                                Member p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked\n" + p.getName() +
                                        " with Aadhaar Number \n" + p.getAadhaarNumber());
                                alert.show();
                            });
                            setGraphic(editButton);
                        }
                        setText(null);
                    }

                    ;
                };

                return cell;
            };

            colStatus.setCellFactory(cellFactory);
            attendanceTable.setItems(memberList);

        }catch (SQLException throwable) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null,  throwable);
            throwable.printStackTrace();
        }
    }
}


