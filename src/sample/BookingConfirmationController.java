package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        loadCaptcha();
        loadDetailsOnLabels();
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

    public void confirmBookingButtonClick(){
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
        check is checkbox ticked
        check time selected or not
        check captcha
        update total slots (-1)
        update affected timeslot (-1)
        update dose1booking status or dose2booking status depending upon which dose number
        update dose1 centreID
        update dose 1 date
        update dose1 slot
        show date in confirm slot window


         */
    }
}
