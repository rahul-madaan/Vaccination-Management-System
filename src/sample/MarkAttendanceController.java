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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
    private TableView<Member> attendanceTable;

    private Set<String> presentees = new HashSet<>();

    private Set<String> absentees = new HashSet<>();

    public static boolean updated;

    @FXML
    private Text infoText;

    @FXML
    private ImageView infoIconImageView;

    @FXML
    private Rectangle notificationRectangle;







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        try {
            populateCentreDetails();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateSelectDateComboBox();
        notification();
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


        c.add(Calendar.DATE, 1);//make -1
        String date2 = c.getTime().toString();
        String date2temp = date2.substring(4,7);
        date2 = date2.substring(8,10);
        date2 = date2temp + date2;
        String date2S1 = date2 + "s1";
        String date2S2 = date2 + "s2";
        String date2S3 = date2 + "s3";
        String date2S4 = date2 + "s4";

        c.add(Calendar.DATE, 1);//make -1
        String date3 = c.getTime().toString();
        String date3temp = date3.substring(4,7);
        date3 = date3.substring(8,10);
        date3 = date3temp + date3;
        String date3S1 = date3 + "s1";
        String date3S2 = date3 + "s2";
        String date3S3 = date3 + "s3";
        String date3S4 = date3 + "s4";

        c.add(Calendar.DATE, 1);//make -1
        String date4 = c.getTime().toString();
        String date4temp = date4.substring(4,7);
        date4 = date4.substring(8,10);
        date4 = date4temp + date4;
        String date4S1 = date4 + "s1";
        String date4S2 = date4 + "s2";
        String date4S3 = date4 + "s3";
        String date4S4 = date4 + "s4";

        c.add(Calendar.DATE, 1);//make -1
        String date5 = c.getTime().toString();
        String date5temp = date5.substring(4,7);
        date5 = date5.substring(8,10);
        date5 = date5temp + date5;
        String date5S1 = date5 + "s1";
        String date5S2 = date5 + "s2";
        String date5S3 = date5 + "s3";
        String date5S4 = date5 + "s4";

        String comboBoxDate1 = date1.substring(3,5) + " " + date1.substring(0,3) + " 2021";
        String comboBoxDate2 = date2.substring(3,5) + " " + date2.substring(0,3) + " 2021";
        String comboBoxDate3 = date3.substring(3,5) + " " + date3.substring(0,3) + " 2021";
        String comboBoxDate4 = date4.substring(3,5) + " " + date4.substring(0,3) + " 2021";
        String comboBoxDate5 = date5.substring(3,5) + " " + date5.substring(0,3) + " 2021";

        selectDateComboBox.getItems().addAll(comboBoxDate1,comboBoxDate2,comboBoxDate3,comboBoxDate4,comboBoxDate5);
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
            presentees.clear();
            Label label = new Label("No Attendance marking left for " + selectDateComboBox.getValue().toString());

            label.setFont(new Font("Arial", 24));
            attendanceTable.setPlaceholder(label);
            memberList = FXCollections.observableArrayList();
            //gets in format 20 jun 2021
            //gives in format jun20
            String selectedDate = selectDateComboBox.getValue().toString().substring(0, 6).replace(" ", "");
            selectedDate = selectedDate.substring(2,5) + selectedDate.substring(0,2);
            String query = "SELECT * FROM members where ( dose1date = '" + selectedDate + "' OR dose2date = '" + selectedDate + "' ) AND " +
                    "(dose1centreID = " + AdminLoginController.adminCentreID +" OR dose2centreID = " + AdminLoginController.adminCentreID + " ) AND " +
                    "(dose1status = 'Booked' OR dose2status = 'Booked' ) ;";
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
                presentees.add(set.getString("RefID"));
                memberList.add(member);
            }

            colRefID.setCellValueFactory(new PropertyValueFactory<>("RefID"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));

            Callback<TableColumn<Member, String>, TableCell<Member, String>> cellFactory = (param) -> {
                final TableCell<Member, String> cell = new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button editButton = new Button("Present");
                            editButton.setStyle("-fx-background-color: lawngreen");
                            editButton.setOnAction(event -> {
                                Member p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked\n" + p.getName() +
                                        " with Aadhaar Number \n" + p.getAadhaarNumber());
                                alert.show();
                                if(editButton.getStyle().equalsIgnoreCase("-fx-background-color: lawngreen")){
                                    editButton.setStyle("-fx-background-color: red; -fx-text-fill: white");
                                    editButton.setText("Absent");
                                    presentees.remove(p.getRefID().toString());
                                    absentees.add(p.getRefID().toString());
                                }else{
                                    editButton.setStyle("-fx-background-color: lawngreen");
                                    editButton.setText("Present");
                                    presentees.add(p.getRefID().toString());
                                    absentees.remove(p.getRefID().toString());
                                }
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

//            Callback<TableColumn<Member, String>, TableCell<Member, String>> cellFactoryColour = (param) -> {
//                final TableCell<Member, String> cell = new TableCell<>() {
//
//                    @Override
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            Rectangle statusRectangle = new Rectangle();
//                            Button editButton = new Button("Absent");
//                            editButton.setOnAction(event -> {
//                                Member p = getTableView().getItems().get(getIndex());
//                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                                alert.setContentText("You have Clicked\n" + p.getName() +
//                                        " with Aadhaar Number \n" + p.getAadhaarNumber());
//                                alert.show();
//                            });
//                            setGraphic(editButton);
//                        }
//                        setText(null);
//                    }
//
//                    ;
//                };
//
//                return cell;
//            };
//
//            colColour.setCellFactory(cellFactoryColour);

            attendanceTable.setItems(memberList);

        }catch (SQLException throwable) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null,  throwable);
            throwable.printStackTrace();
        }
    }

    public void notification(){
        if (MarkAttendanceController.updated==true){
            //show
            infoIconImageView.setVisible(true);
            notificationRectangle.setVisible(true);
            infoText.setVisible(true);
            MarkAttendanceController.updated=false;
        }else if(MarkAttendanceController.updated==false){
            //hide
            infoIconImageView.setVisible(false);
            notificationRectangle.setVisible(false);
            infoText.setVisible(false);
        }
    }

    public void submitAttendanceButtonClicked(ActionEvent event) throws SQLException, IOException {
        String[] array = presentees.toArray( new String[presentees.size()] );
        String[] arrayAbsentees = absentees.toArray( new String[absentees.size()] );
        String query =null;
        for(int i=0;i<presentees.size();i++){
            query = "UPDATE members SET dose1status = 'Vaccinated' WHERE RefID = '"+ array[i] +"' AND dose1status = 'Booked'";
            conn = dbHandler.getConnection();
            conn.createStatement().executeUpdate(query);
        }
        for(int i=0;i<presentees.size();i++){
            query = "UPDATE members SET dose2status = 'Vaccinated' WHERE RefID = '"+ array[i] +"' AND dose2status = 'Booked'";
            conn = dbHandler.getConnection();
            conn.createStatement().executeUpdate(query);
        }
        for(int i=0;i<absentees.size();i++){
            query = "UPDATE members SET dose1status = 'Not vaccinated' WHERE RefID = '"+ arrayAbsentees[i] +"' AND dose1status = 'Booked'";
            conn = dbHandler.getConnection();
            conn.createStatement().executeUpdate(query);
        }
        for(int i=0;i<absentees.size();i++){
            query = "UPDATE members SET dose2status = 'Not vaccinated' WHERE RefID = '"+ arrayAbsentees[i] +"' AND dose2status = 'Booked'";
            conn = dbHandler.getConnection();
            conn.createStatement().executeUpdate(query);
        }
        MarkAttendanceController.updated =true;

        if(AdminLoginController.adminPosition.equalsIgnoreCase("local")){
            Parent scene2Parent = FXMLLoader.load(getClass().getResource("MarkAttendance.fxml"));
            Scene addMembersScene = new Scene(scene2Parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addMembersScene);
            window.show();
        }else if(AdminLoginController.adminPosition.equalsIgnoreCase("global")){
            Parent scene2Parent = FXMLLoader.load(getClass().getResource("MarkAttendance.fxml"));
            Scene addMembersScene = new Scene(scene2Parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addMembersScene);
            window.show();
        }

    }

    public void faqButtonClicked(ActionEvent event) throws IOException {
        faqSceneController.returnToPage = "MarkAttendance.fxml";
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("faqScene.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }
}


