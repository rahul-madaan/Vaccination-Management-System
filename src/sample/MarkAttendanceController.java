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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    private Connection conn;
    private DbHandler dbHandler;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DbHandler();
        try {
            populateCentreDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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



}
