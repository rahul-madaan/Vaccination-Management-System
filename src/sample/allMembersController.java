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

    public static String selectedMemberName;
    public static String selectedMemberAadhaarNumber;


    @FXML
    public void secondWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addNewMemberScene.fxml"));
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(root));
        secondStage.show();
        secondStage.setTitle("part2");
    }

    public void populateMembersTable(){
        String activeUserPhoneNumber = mainPageController.activeUserPhoneNumber;
        try {
            memberList = FXCollections.observableArrayList();
            String query = "SELECT * FROM members WHERE phonenumer = '"+ activeUserPhoneNumber +"';";

            conn = dbHandler.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                Member member = new Member();
                member.setRefID(set.getInt("RefID"));
                member.setName(set.getString("name"));
                member.setAadhaarNumber(set.getString("AadhaarNumber"));
                member.setAge(set.getInt("Age"));
                member.setDose1Status(set.getBoolean("Dose1Status"));
                member.setDose2Status(set.getBoolean("Dose2Status"));
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
                            scheduleButton.setOnAction(event -> {
                                Member p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have Clicked\n" + p.getName() +
                                        " with Aadhaar Number \n" + p.getAadhaarNumber());
                                alert.show();
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

            memberTable.setItems(memberList);

        } catch (SQLException throwable) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null,  throwable);
            throwable.printStackTrace();
        }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        populateMembersTable();
        phoneNumberLabel.setText(mainPageController.activeUserPhoneNumber);
    }
}
