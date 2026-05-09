package hotel.gui.controllers;

import hotel.enums.Role;
import hotel.gui.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuestOptionsController {

    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            // Updated path to match your project resource structure
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Could not load " + fxmlFile);
        }
    }

    @FXML
    void onLoginChoice(ActionEvent event) throws IOException {
        // We set the role to GUEST here so the LoginController
        // knows to use the GuestManager later.
        UserSession.currentRole = Role.GUEST;
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/login.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onRegisterChoice(ActionEvent event) throws IOException {
        // Set the role and move to the registration screen.
        UserSession.currentRole = Role.GUEST;
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/Register.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onBackClicked(ActionEvent event) {
        // Returns the user to the start screen.
        switchScene("start-screen.fxml",event);
    }
}

