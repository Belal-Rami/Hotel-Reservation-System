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
        // Role logic: For Admin and Staff, go immediately to Login screen
        System.out.println("Admin clicked! Person 1 will load Login screen for Role: ADMIN.");
        // The implementation here might pass the "ADMIN" role info to the login controller
        switchScene(event, "login.fxml", "Admin Login - Lokanda System");
    }


    public void handleStaffAction(ActionEvent event) throws IOException {
        System.out.println("Staff clicked! Person 1 will load Login screen for Role: STAFF.");
        switchScene(event, "login.fxml", "Staff Login - Lokanda System");
    }


        private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/hotel/gui/scenes/" + fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }

    @FXML
    void onGuestClicked(ActionEvent event) throws IOException {
        UserSession.currentRole = Role.GUEST;
        switchScene(event,"GuestOptions.fxml");
    }


    @FXML
        void onStaffClicked(ActionEvent event) throws IOException {
            UserSession.currentRole = Role.STAFF; // Or however your enum is named
            switchScene(event, "Login.fxml");
        }

        @FXML
        void onAdminClicked(ActionEvent event) throws IOException {
            UserSession.currentRole = Role.ADMIN;
            switchScene(event, "Login.fxml");
        }
    }