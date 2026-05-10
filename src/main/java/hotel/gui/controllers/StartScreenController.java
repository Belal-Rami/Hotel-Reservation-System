package hotel.gui.controllers;

import hotel.enums.Role;
import hotel.gui.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class StartScreenController {


    @FXML
    private Button adminBtn;
    @FXML
    private Button guestBtn;
    @FXML
    private Button staffBtn;

    private void switchScene(ActionEvent event, String fxmlFileName, String title) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/hotel/gui/scenes/" + fxmlFileName));
        Scene scene = new Scene(parent);


        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setTitle(title);
        window.setScene(scene);
        window.show();
    }

    public void handleAdminAction(ActionEvent event) throws IOException {
        UserSession.currentRole = "ADMIN";
        switchScene(event, "login.fxml", "Admin Login - Lokanda System");
    }


    public void handleReceptionistAction(ActionEvent event) throws IOException {
        UserSession.currentRole = "RECEPTIONIST";
        switchScene(event, "login.fxml", "Receptionist Login - Lokanda System");
    
    }
    
    @FXML
    public void handleGuestAction(ActionEvent event) throws IOException {
        UserSession.currentRole = "GUEST";
        switchScene(event,"GuestOptions.fxml", "Guest Options - Lokanda System");
    }


}