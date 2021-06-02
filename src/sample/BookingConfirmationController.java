package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookingConfirmationController implements Initializable {
    @FXML
    private Label personalRefIDLabel;

    @FXML
    private Label personalNameLabel;

    @FXML
    private Label personalAadhaarNumberLabel;

    @FXML
    private Label personalAgeLabel;

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
    private RadioButton slot1RadioButton;

    @FXML
    private RadioButton slot2RadioButton;

    @FXML
    private RadioButton slot3RadioButton;

    @FXML
    private RadioButton slot4RadioButton;

    @FXML
    private ImageView captchaImageView;

    @FXML
    private TextField captchaTextField;

    @FXML
    private CheckBox agreeCheckBox;

    @FXML
    private ToggleGroup timeSlotToggleGroup;

    @FXML
    private ImageView refreshButtonImageView;

    private Connection conn;
    private DbHandler dbHandler;
    private static String captchaCode;

    public static Boolean bookingStatus = false;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        loadCaptcha();
        loadDetailsOnLabels();
        try {
            checkTimeSlotAvailability();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void refreshButtonClicked(){
        int min =1;
        int max = 1070;
        int randomNumber = (int) ((Math.random() * (max - min)) + min);

        String query = "SELECT images FROM captchaimages WHERE serialNumber = "+ randomNumber +";";

        conn = dbHandler.getConnection();
        ResultSet set = null;
        try {
            set = conn.createStatement().executeQuery(query);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        String imageName = null;
        while (true) {
            try {
                if (!set.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                imageName = set.getString("images");
                BookingConfirmationController.captchaCode = imageName;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        captchaImageView.setImage(new Image("file:C:\\Users\\dell\\IdeaProjects\\Vaccine_CodeForCovid\\src\\images\\samples\\" +imageName+ ".png"));


    }

    public void loadCaptcha(){
        int min =1;
        int max = 1070;
        int randomNumber = (int) ((Math.random() * (max - min)) + min);

        String query = "SELECT images FROM captchaimages WHERE serialNumber = "+ randomNumber +";";

        conn = dbHandler.getConnection();
        ResultSet set = null;
        try {
            set = conn.createStatement().executeQuery(query);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        String imageName = null;
        while (true) {
            try {
                if (!set.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                imageName = set.getString("images");
                BookingConfirmationController.captchaCode = imageName;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        captchaImageView.setImage(new Image("file:C:\\Users\\dell\\IdeaProjects\\Vaccine_CodeForCovid\\src\\images\\samples\\" +imageName+ ".png"));

    }

    public void loadDetailsOnLabels(){
        VaccineCentre selectedCentre = FindCentreController.selectedCentre;
        centreAddressLabel.setText(selectedCentre.getAddress());
        centreIDLabel.setText(Integer.toString(selectedCentre.getCentreID()));
        centreNameLabel.setText(selectedCentre.getHospitalName());
        centreStateLabel.setText(selectedCentre.getState());
        centrePinCodeLabel.setText(selectedCentre.getPinCode());
        Member selectedMember = allMembersController.selectedMember;
        personalNameLabel.setText(selectedMember.getName());
        personalRefIDLabel.setText(Integer.toString(selectedMember.getRefID()));
        personalAadhaarNumberLabel.setText(selectedMember.getAadhaarNumber());
        personalAgeLabel.setText(Integer.toString(selectedMember.getAge()));
    }

    public void confirmBookingButtonClick(ActionEvent event) throws SQLException, IOException {
        if(!slot1RadioButton.isSelected()&&!slot2RadioButton.isSelected()&&
        !slot3RadioButton.isSelected()&&!slot4RadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a time slot");
            alert.show();
            return;
        }
        if(!captchaTextField.getText().equals(captchaCode)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter correct CAPTCHA!");
            alert.show();
            return;
        }
        if(!agreeCheckBox.isSelected()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please click on the check box to proceed!");
            alert.show();
            return;
        }

        /*
        check is checkbox ticked====>DONE
        check time selected or not====>DONE
        check captcha====>DONE
        update total slots (-1) ====> DONE
        update affected timeslot (-1) ====>DONE
        update dose1booking status or dose2booking status depending upon which dose number
        update dose1 centreID
        update dose 1 date
        update dose1 slot
        show date in confirm slot window
         */
        String selectedDate = FindCentreController.selectedDate;
        String selectedCentreID = Integer.toString(FindCentreController.selectedCentre.getCentreID());
        String selectedSlot=null;
        if(slot1RadioButton.isSelected()){
            selectedSlot = "S1";
        }
        else if(slot2RadioButton.isSelected()){
            selectedSlot = "S2";
        }
        else if(slot3RadioButton.isSelected()){
            selectedSlot = "S3";
        }
        else if(slot4RadioButton.isSelected()){
            selectedSlot = "S4";
        }
        String slot = selectedDate +selectedSlot;
        conn = dbHandler.getConnection();
        String query = "SELECT * FROM slots where centreID = "+selectedCentreID+";";
        ResultSet set = conn.createStatement().executeQuery(query);

        int totalSlots = 0;
        int specificSlots = 0;

        while(set.next()){
            totalSlots = set.getInt(selectedDate);
            specificSlots = set.getInt(slot);
        }

        conn = dbHandler.getConnection();
        query = "UPDATE  slots SET "+ selectedDate +" = "+ (totalSlots - 1) +" , "+ slot +" = "+ (specificSlots-1) +" where centreID = "+selectedCentreID+";";
        conn.createStatement().executeUpdate(query);

        Member selectedMember = allMembersController.selectedMember;
        String selectedMemberRefID = selectedMember.getRefID().toString();

        VaccineCentre selectedCentre = FindCentreController.selectedCentre;

        query = "UPDATE  members SET dose1status = 'Booked', " +
                "dose1centreID = "+ selectedCentreID +" , dose1slot = "+ Integer.parseInt(Character.toString(selectedSlot.charAt(1))) +" , dose1date = '"+ selectedDate +"' , dose1vaccineName = '"+ selectedCentre.getVaccineName() +"' where refID = "+selectedMemberRefID+";";
        conn.createStatement().executeUpdate(query);
        //System.out.println(query);

        BookingConfirmationController.bookingStatus = true;

        Parent scene2Parent = FXMLLoader.load(getClass().getResource("allMembersScene.fxml"));
        Scene addMembersScene = new Scene(scene2Parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addMembersScene);
        window.show();


    }

    public void checkTimeSlotAvailability() throws SQLException {
        conn = dbHandler.getConnection();
        String selectedDate = FindCentreController.selectedDate;
        String selectedCentreID = Integer.toString(FindCentreController.selectedCentre.getCentreID());

        String query = "SELECT * FROM slots where centreID = "+selectedCentreID+";";
        ResultSet set = conn.createStatement().executeQuery(query);

        String selectedDateS1 = selectedDate +"S1";
        String selectedDateS2 = selectedDate +"S2";
        String selectedDateS3 = selectedDate +"S3";
        String selectedDateS4 = selectedDate +"S4";
        int totalSlots = 0;
        int timeSlot1 = 0;
        int timeSlot2 = 0;
        int timeSlot3 = 0;
        int timeSlot4 = 0;


        while(set.next()){
            totalSlots = set.getInt(selectedDate);
            timeSlot1 = set.getInt(selectedDateS1);
            timeSlot2 = set.getInt(selectedDateS2);
            timeSlot3 = set.getInt(selectedDateS3);
            timeSlot4 = set.getInt(selectedDateS4);
        }
        if(timeSlot1 == 0){
            slot1RadioButton.setDisable(true);
        }
        if(timeSlot2 == 0){
            slot2RadioButton.setDisable(true);
        }
        if(timeSlot3 == 0){
            slot3RadioButton.setDisable(true);
        }
        if(timeSlot4 == 0){
            slot4RadioButton.setDisable(true);
        }
    }

}
