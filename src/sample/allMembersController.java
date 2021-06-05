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
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class allMembersController implements Initializable {


    private ObservableList<Member> memberList;
    private Connection conn;

    private DbHandler dbHandler;
    @FXML
    private TableView<Member> memberTable;

    @FXML
    private TableColumn<?,?> colRefID;

    @FXML
    private TableColumn<?,?> colName;

    @FXML
    private TableColumn<?,?> colAadhaarNumber;

    @FXML
    private TableColumn<?,?> colAge;

    @FXML
    private TableColumn<?,?> colDose1Status;

    @FXML
    private TableColumn<?,?> colDose2Status;

    @FXML
    private TableColumn<Member,String> colDelete;

    @FXML
    private TableColumn<Member,String> colUpdate;

    @FXML
    private TableColumn<Member,String> colSchedule;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private ImageView infoIconImageView;

    @FXML
    private Rectangle notificationRectangle;

    @FXML
    private Text notificationText;

    @FXML
    private Button addNewMemberButton;

    public static String selectedMemberName;
    public static String selectedMemberAadhaarNumber;
    public static String selectedPhoneNumber;
    public static ActionEvent event;
    public static Member selectedMember;
    public static Member selectedMemberForEdit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        populateMembersTable();
        phoneNumberLabel.setText(mainPageController.activeUserPhoneNumber);
        if(BookingConfirmationController.bookingStatus==true){
            notificationRectangle.setVisible(true);
            notificationText.setVisible(true);
            infoIconImageView.setVisible(true);
            BookingConfirmationController.bookingStatus=false;
        }else{
            notificationRectangle.setVisible(false);
            notificationText.setVisible(false);
            infoIconImageView.setVisible(false);
        }
        try {
            checkMemberCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void addNewMemberButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addNewMemberScene.fxml"));
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(root));
        allMembersController.selectedPhoneNumber = mainPageController.activeUserPhoneNumber;
        allMembersController.event = event;
        secondStage.show();
        secondStage.setTitle("Add New Member");
    }

    @FXML
    public void checkMemberCount() throws SQLException {
        String activeUserPhoneNumber = mainPageController.activeUserPhoneNumber;
        memberList = FXCollections.observableArrayList();
        String query = "SELECT * FROM members WHERE phonenumber = '"+ activeUserPhoneNumber +"';";
        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(query);
        int counter =0;
        while (set.next()){
            counter = counter+1;
        }
        if(counter>3){
            addNewMemberButton.setDisable(true);
        }else{
            addNewMemberButton.setDisable(false);
        }
    }

    @FXML
    public void editMemberButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditMember.fxml"));
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(root));
        allMembersController.selectedPhoneNumber = mainPageController.activeUserPhoneNumber;
        allMembersController.event = event;
        secondStage.show();
        secondStage.setTitle("Edit Member");
    }

    @FXML
    public void logoutButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();
    }

    public void populateMembersTable(){
        String activeUserPhoneNumber = mainPageController.activeUserPhoneNumber;
        try {
            memberList = FXCollections.observableArrayList();
            String query = "SELECT * FROM members WHERE phonenumber = '"+ activeUserPhoneNumber +"';";

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

//we are now setting the name of columns for java to understand
            colRefID.setCellValueFactory(new PropertyValueFactory<>("RefID"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colAadhaarNumber.setCellValueFactory(new PropertyValueFactory<>("AadhaarNumber"));
            colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
            colDose1Status.setCellValueFactory(new PropertyValueFactory<>("Dose1Status"));
            colDose2Status.setCellValueFactory(new PropertyValueFactory<>("Dose2Status"));

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
                                try {
                                    allMembersController.selectedMemberForEdit = p;
                                    editMemberButtonClicked(event);
                                } catch (IOException e) {
                                    e.printStackTrace();
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

            colUpdate.setCellFactory(cellFactory);

            Callback<TableColumn<Member, String>, TableCell<Member, String>> cellFactorySchedule = (param) -> {
                final TableCell<Member, String> cell = new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button scheduleButton = new Button("Schedule");
                            Member member = getTableView().getItems().get(getIndex());
                            try {
                                int disableButtonOrChangeText = disableScheduleButtonOrChangeText(member);
                                if(disableButtonOrChangeText == 0){
                                    scheduleButton.setDisable(false);
                                    scheduleButton.setText("Book Dose 1");
                                }else if(disableButtonOrChangeText == 1){
                                    scheduleButton.setText("Booked Dose 1");
                                    scheduleButton.setDisable(true);
                                }else if(disableButtonOrChangeText == 2){
                                    scheduleButton.setDisable(false);
                                    scheduleButton.setText("Book Dose 2");
                                }else if(disableButtonOrChangeText == 3){
                                    scheduleButton.setText("Booked Dose 2");
                                    scheduleButton.setDisable(true);
                                }else if(disableButtonOrChangeText == 4){
                                    scheduleButton.setDisable(true);
                                    scheduleButton.setText("Completed");
                                }
                                else {
                                    scheduleButton.setDisable(true);
                                    scheduleButton.setText("problem in code");
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            //add function to disable button if both scheduled
                            scheduleButton.setOnAction(event -> {
                                Member p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked\n" + p.getName() +
                                        " with Aadhaar Number \n" + p.getAadhaarNumber());
                                alert.show();
                                allMembersController.selectedMember = p;
                                try {
                                    allMembersController.selectedMemberName = p.getName();
                                    allMembersController.selectedMemberAadhaarNumber = p.getAadhaarNumber();
                                    scheduleButtonClicked(event);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            setGraphic(scheduleButton);
                        }
                        setText(null);
                    }
                    ;
                };

                return cell;
            };

            colSchedule.setCellFactory(cellFactorySchedule);

            Callback<TableColumn<Member, String>, TableCell<Member, String>> cellFactoryDelete = (param) -> {
                final TableCell<Member, String> cell = new TableCell<>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button deleteButton = new Button("Delete");
                            deleteButton.setOnAction(event -> {
                                Member p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked\n" + p.getName() +
                                        " with Aadhaar Number \n" + p.getAadhaarNumber());
                                alert.show();
                                allMembersController.selectedMember = p;
                                allMembersController.selectedMemberName = p.getName();
                                allMembersController.selectedMemberAadhaarNumber = p.getAadhaarNumber();
                                deleteButtonClicked(event,p);
                            });
                            setGraphic(deleteButton);
                        }
                        setText(null);
                    }
                    ;
                };

                return cell;
            };

            colDelete.setCellFactory(cellFactoryDelete);

            memberTable.setItems(memberList);

        } catch (SQLException throwable) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null,  throwable);
            throwable.printStackTrace();
        }

    }

    public void deleteButtonClicked(ActionEvent event,Member member){
        //if booked, booking cancel both
        //if

    }

    public int disableScheduleButtonOrChangeText(Member member) throws SQLException {
        String query = "SELECT * FROM members WHERE refID = "+ member.getRefID() +";";

        conn = dbHandler.getConnection();
        ResultSet set = conn.createStatement().executeQuery(query);

        String dose1Status=null;
        String dose2Status=null;

        while(set.next()){
            dose1Status = set.getString("Dose1Status");
            dose2Status = set.getString("Dose2Status");
        }

        if(dose1Status.equalsIgnoreCase("not vaccinated")&& dose2Status.equalsIgnoreCase("not vaccinated")){
            return 0;//both non vaccinated
        }else if(dose1Status.equalsIgnoreCase("booked") && dose2Status.equalsIgnoreCase("not vaccinated")){
            return 1;//booked, not vaccinated
        }else if(dose1Status.equalsIgnoreCase("vaccinated") && dose2Status.equalsIgnoreCase("not vaccinated")){
            return 2;//1 done, 2nd left
        }else if(dose1Status.equalsIgnoreCase("vaccinated") && dose2Status.equalsIgnoreCase("booked")){
            return 3;//1 done, 2nd left
        }else if(dose1Status.equalsIgnoreCase("vaccinated") && dose2Status.equalsIgnoreCase("vaccinated")){
            return 4;//1 done, 2nd left
        }
        else return 5;


    }


    public void scheduleButtonClicked(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("FindCentre.fxml"));
        Scene findCentreScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(findCentreScene);
        window.show();
    }

    public void faqButtonClick(ActionEvent event) throws IOException {
        Parent scene2Parent = FXMLLoader.load(getClass().getResource("faqScene.fxml"));
        Scene faqScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(faqScene);
        window.show();
    }


}
